package com.pigx.engine.web.servlet.crypto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.web.core.annotation.Crypto;
import com.pigx.engine.web.core.exception.SessionInvalidException;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    private HttpCryptoProcessor httpCryptoProcessor;

    public void setInterfaceCryptoProcessor(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {

        String methodName = methodParameter.getMethod().getName();
        Crypto crypto = methodParameter.getMethodAnnotation(Crypto.class);

        boolean isSupports = ObjectUtils.isNotEmpty(crypto) && crypto.responseEncrypt();

        log.trace("[PIGXD] |- Is EncryptResponseBodyAdvice supports method [{}] ? Status is [{}].", methodName, isSupports);
        return isSupports;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        String sessionId = SessionUtils.analyseSessionId(request);
        if (SessionUtils.isCryptoEnabled(request, sessionId)) {

            log.info("[PIGXD] |- EncryptResponseBodyAdvice begin encrypt data.");

            String methodName = methodParameter.getMethod().getName();
            String className = methodParameter.getDeclaringClass().getName();

            try {
                String bodyString = Jackson2Utils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(body);
                String result = httpCryptoProcessor.encrypt(sessionId, bodyString);
                if (StringUtils.isNotBlank(result)) {
                    log.debug("[PIGXD] |- Encrypt response body for rest method [{}] in [{}] finished.", methodName, className);
                    return result;
                } else {
                    return body;
                }
            } catch (JsonProcessingException e) {
                log.debug("[PIGXD] |- Encrypt response body for rest method [{}] in [{}] catch error, skip encrypt operation.", methodName, className, e);
                return body;
            } catch (SessionInvalidException e) {
                log.error("[PIGXD] |- Session is expired for encrypt response body for rest method [{}] in [{}], skip encrypt operation.", methodName, className, e);
                return body;
            }
        } else {
            log.warn("[PIGXD] |- Cannot find Herodotus Cloud custom session header. Use interface crypto function need add X_HERODOTUS_SESSION_ID to request header.");
            return body;
        }
    }
}
