package com.pigx.engine.web.servlet.crypto;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.web.core.annotation.Crypto;
import com.pigx.engine.web.core.exception.SessionInvalidException;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/crypto/EncryptResponseBodyAdvice.class */
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);
    private HttpCryptoProcessor httpCryptoProcessor;

    public void setInterfaceCryptoProcessor(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        String methodName = methodParameter.getMethod().getName();
        Crypto crypto = (Crypto) methodParameter.getMethodAnnotation(Crypto.class);
        boolean isSupports = ObjectUtils.isNotEmpty(crypto) && crypto.responseEncrypt();
        log.trace("[Herodotus] |- Is EncryptResponseBodyAdvice supports method [{}] ? Status is [{}].", methodName, Boolean.valueOf(isSupports));
        return isSupports;
    }

    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String sessionId = SessionUtils.analyseSessionId(request);
        if (SessionUtils.isCryptoEnabled((HttpInputMessage) request, sessionId)) {
            log.info("[Herodotus] |- EncryptResponseBodyAdvice begin encrypt data.");
            String methodName = methodParameter.getMethod().getName();
            String className = methodParameter.getDeclaringClass().getName();
            try {
                String bodyString = Jackson2Utils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(body);
                String result = this.httpCryptoProcessor.encrypt(sessionId, bodyString);
                if (StringUtils.isNotBlank(result)) {
                    log.debug("[Herodotus] |- Encrypt response body for rest method [{}] in [{}] finished.", methodName, className);
                    return result;
                }
                return body;
            } catch (SessionInvalidException e) {
                log.error("[Herodotus] |- Session is expired for encrypt response body for rest method [{}] in [{}], skip encrypt operation.", new Object[]{methodName, className, e});
                return body;
            } catch (JsonProcessingException e2) {
                log.debug("[Herodotus] |- Encrypt response body for rest method [{}] in [{}] catch error, skip encrypt operation.", new Object[]{methodName, className, e2});
                return body;
            }
        }
        log.warn("[Herodotus] |- Cannot find Herodotus Cloud custom session header. Use interface crypto function need add X_HERODOTUS_SESSION_ID to request header.");
        return body;
    }
}
