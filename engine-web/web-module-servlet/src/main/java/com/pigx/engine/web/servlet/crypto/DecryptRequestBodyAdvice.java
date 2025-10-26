package com.pigx.engine.web.servlet.crypto;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.web.core.annotation.Crypto;
import com.pigx.engine.web.core.exception.SessionInvalidException;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import cn.hutool.v7.core.io.IoUtil;
import cn.hutool.v7.core.util.ByteUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
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

@RestControllerAdvice
/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/crypto/DecryptRequestBodyAdvice.class */
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {
    private static final Logger log = LoggerFactory.getLogger(DecryptRequestBodyAdvice.class);
    private HttpCryptoProcessor httpCryptoProcessor;

    public void setInterfaceCryptoProcessor(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        String methodName = methodParameter.getMethod().getName();
        Crypto crypto = (Crypto) methodParameter.getMethodAnnotation(Crypto.class);
        boolean isSupports = ObjectUtils.isNotEmpty(crypto) && crypto.requestDecrypt();
        log.trace("[Herodotus] |- Is DecryptRequestBodyAdvice supports method [{}] ? Status is [{}].", methodName, Boolean.valueOf(isSupports));
        return isSupports;
    }

    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException, SessionInvalidException {
        String sessionId = SessionUtils.analyseSessionId(httpInputMessage);
        if (SessionUtils.isCryptoEnabled(httpInputMessage, sessionId)) {
            log.info("[Herodotus] |- DecryptRequestBodyAdvice begin decrypt data.");
            String methodName = methodParameter.getMethod().getName();
            String className = methodParameter.getDeclaringClass().getName();
            String content = IoUtil.read(httpInputMessage.getBody()).toString();
            if (StringUtils.isNotBlank(content)) {
                String data = this.httpCryptoProcessor.decrypt(sessionId, content);
                if (Strings.CS.equals(data, content)) {
                    data = decrypt(sessionId, content);
                }
                log.debug("[Herodotus] |- Decrypt request body for rest method [{}] in [{}] finished.", methodName, className);
                return new DecryptHttpInputMessage(httpInputMessage, ByteUtil.toUtf8Bytes(data));
            }
            return httpInputMessage;
        }
        log.warn("[Herodotus] |- Cannot find Herodotus Cloud custom session header. Use interface crypto founction need add X_HERODOTUS_SESSION_ID to request header.");
        return httpInputMessage;
    }

    private String decrypt(String sessionKey, String content) throws SessionInvalidException {
        JsonNode jsonNode = Jackson2Utils.toNode(content);
        if (ObjectUtils.isNotEmpty(jsonNode)) {
            decrypt(sessionKey, jsonNode);
            return Jackson2Utils.toJson(jsonNode);
        }
        return content;
    }

    private void decrypt(String sessionKey, JsonNode jsonNode) throws SessionInvalidException {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                JsonNode value = entry.getValue();
                if (value instanceof TextNode) {
                    TextNode t = (TextNode) value;
                    if (entry.getValue().isValueNode()) {
                        String value2 = this.httpCryptoProcessor.decrypt(sessionKey, t.asText());
                        entry.setValue(new TextNode(value2));
                    }
                }
                decrypt(sessionKey, entry.getValue());
            }
        }
        if (jsonNode.isArray()) {
            Iterator it2 = jsonNode.iterator();
            while (it2.hasNext()) {
                JsonNode node = (JsonNode) it2.next();
                decrypt(sessionKey, node);
            }
        }
    }

    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/crypto/DecryptRequestBodyAdvice$DecryptHttpInputMessage.class */
    public static class DecryptHttpInputMessage implements HttpInputMessage {
        private final HttpInputMessage httpInputMessage;
        private final byte[] data;

        public DecryptHttpInputMessage(HttpInputMessage httpInputMessage, byte[] data) {
            this.httpInputMessage = httpInputMessage;
            this.data = data;
        }

        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(this.data);
        }

        public HttpHeaders getHeaders() {
            return this.httpInputMessage.getHeaders();
        }
    }
}
