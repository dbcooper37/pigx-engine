package com.pigx.engine.web.servlet.crypto;

import cn.hutool.v7.core.io.IoUtil;
import cn.hutool.v7.core.util.ByteUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.web.core.annotation.Crypto;
import com.pigx.engine.web.core.exception.SessionInvalidException;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;


@RestControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger log = LoggerFactory.getLogger(DecryptRequestBodyAdvice.class);

    /**
     * Maximum allowed JSON nesting depth to prevent stack overflow attacks.
     * Deeply nested JSON payloads can cause stack overflow during recursive processing.
     */
    private static final int MAX_JSON_DEPTH = 32;

    private HttpCryptoProcessor httpCryptoProcessor;

    public void setInterfaceCryptoProcessor(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        String methodName = methodParameter.getMethod().getName();
        Crypto crypto = methodParameter.getMethodAnnotation(Crypto.class);

        boolean isSupports = ObjectUtils.isNotEmpty(crypto) && crypto.requestDecrypt();

        log.trace("[PIGXD] |- Is DecryptRequestBodyAdvice supports method [{}] ? Status is [{}].", methodName, isSupports);
        return isSupports;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        String sessionId = SessionUtils.analyseSessionId(httpInputMessage);

        if (SessionUtils.isCryptoEnabled(httpInputMessage, sessionId)) {

            log.info("[PIGXD] |- DecryptRequestBodyAdvice begin decrypt data.");

            String methodName = methodParameter.getMethod().getName();
            String className = methodParameter.getDeclaringClass().getName();

            String content = IoUtil.read(httpInputMessage.getBody()).toString();

            if (StringUtils.isNotBlank(content)) {
                String data = httpCryptoProcessor.tryDecrypt(sessionId, content);
                if (Strings.CS.equals(data, content)) {
                    data = decrypt(sessionId, content);
                }
                log.debug("[PIGXD] |- Decrypt request body for rest method [{}] in [{}] finished.", methodName, className);
                return new DecryptHttpInputMessage(httpInputMessage, ByteUtil.toUtf8Bytes(data));
            } else {
                return httpInputMessage;
            }
        } else {
            log.warn("[PIGXD] |- Cannot find Herodotus Cloud custom session header. Use interface crypto founction need add X_HERODOTUS_SESSION_ID to request header.");
            return httpInputMessage;
        }
    }

    private String decrypt(String sessionKey, String content) throws SessionInvalidException {
        JsonNode jsonNode = Jackson2Utils.toNode(content);
        if (ObjectUtils.isNotEmpty(jsonNode)) {
            decrypt(sessionKey, jsonNode, 0);
            return Jackson2Utils.toJson(jsonNode);
        }

        return content;
    }

    /**
     * Recursively decrypts JSON node values with depth limiting to prevent stack overflow.
     *
     * @param sessionKey   the session key for decryption
     * @param jsonNode     the JSON node to process
     * @param currentDepth the current recursion depth
     * @throws SessionInvalidException if the session is invalid
     * @throws CryptoProcessingException if the JSON nesting exceeds maximum allowed depth
     */
    private void decrypt(String sessionKey, JsonNode jsonNode, int currentDepth) throws SessionInvalidException {
        // Prevent stack overflow from deeply nested JSON
        if (currentDepth >= MAX_JSON_DEPTH) {
            log.error("[PIGXD] |- JSON nesting depth {} exceeds maximum allowed depth {}. " +
                    "Possible attack attempt.", currentDepth, MAX_JSON_DEPTH);
            throw new CryptoProcessingException(
                    "JSON nesting depth exceeds maximum allowed: " + MAX_JSON_DEPTH,
                    com.pigx.engine.web.core.constant.WebErrorCodes.CRYPTO_JSON_DEPTH_EXCEEDED);
        }
        
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                if (entry.getValue() instanceof TextNode t && entry.getValue().isValueNode()) {
                    // Use tryDecrypt for graceful handling of non-encrypted values
                    String value = httpCryptoProcessor.tryDecrypt(sessionKey, t.asText());
                    entry.setValue(new TextNode(value));
                }
                decrypt(sessionKey, entry.getValue(), currentDepth + 1);
            }
        }

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                decrypt(sessionKey, node, currentDepth + 1);
            }
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    public static class DecryptHttpInputMessage implements HttpInputMessage {

        private final HttpInputMessage httpInputMessage;
        private final byte[] data;

        public DecryptHttpInputMessage(HttpInputMessage httpInputMessage, byte[] data) {
            this.httpInputMessage = httpInputMessage;
            this.data = data;
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(this.data);
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpInputMessage.getHeaders();
        }
    }
}
