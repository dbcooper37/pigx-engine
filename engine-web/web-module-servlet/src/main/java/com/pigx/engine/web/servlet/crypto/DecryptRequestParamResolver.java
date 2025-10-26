package com.pigx.engine.web.servlet.crypto;

import com.pigx.engine.web.core.annotation.Crypto;
import com.pigx.engine.web.core.exception.SessionInvalidException;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartRequest;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/crypto/DecryptRequestParamResolver.class */
public class DecryptRequestParamResolver implements HandlerMethodArgumentResolver {
    private static final Logger log = LoggerFactory.getLogger(DecryptRequestParamResolver.class);
    private HttpCryptoProcessor httpCryptoProcessor;
    private RequestParamMethodArgumentResolver requestParamMethodArgumentResolver;

    public void setRequestParamMethodArgumentResolver(RequestParamMethodArgumentResolver requestParamMethodArgumentResolver) {
        this.requestParamMethodArgumentResolver = requestParamMethodArgumentResolver;
    }

    public void setInterfaceCryptoProcessor(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    public boolean supportsParameter(MethodParameter methodParameter) {
        String methodName = methodParameter.getMethod().getName();
        boolean isSupports = isConfigCrypto(methodParameter) && this.requestParamMethodArgumentResolver.supportsParameter(methodParameter);
        log.trace("[Herodotus] |- Is DecryptRequestParamResolver supports method [{}] ? Status is [{}].", methodName, Boolean.valueOf(isSupports));
        return isSupports;
    }

    private boolean isConfigCrypto(MethodParameter methodParameter) {
        Crypto crypto = (Crypto) methodParameter.getMethodAnnotation(Crypto.class);
        return ObjectUtils.isNotEmpty(crypto) && crypto.requestDecrypt();
    }

    private boolean isRegularRequest(NativeWebRequest webRequest) {
        MultipartRequest multipartRequest = (MultipartRequest) webRequest.getNativeRequest(MultipartRequest.class);
        return ObjectUtils.isEmpty(multipartRequest);
    }

    private String[] decrypt(String sessionId, String[] paramValues) throws SessionInvalidException {
        List<String> values = new ArrayList<>();
        for (String paramValue : paramValues) {
            String value = this.httpCryptoProcessor.decrypt(sessionId, paramValue);
            if (StringUtils.isNotBlank(value)) {
                values.add(value);
            }
        }
        String[] result = new String[values.size()];
        return (String[]) values.toArray(result);
    }

    @Nullable
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (isRegularRequest(webRequest)) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(HttpServletRequest.class);
            String sessionId = SessionUtils.analyseSessionId(request);
            if (SessionUtils.isCryptoEnabled(request, sessionId)) {
                String[] paramValues = request.getParameterValues(methodParameter.getParameterName());
                if (ArrayUtils.isNotEmpty(paramValues)) {
                    String[] values = decrypt(sessionId, paramValues);
                    return values.length == 1 ? values[0] : values;
                }
            } else {
                log.warn("[Herodotus] |- Cannot find Herodotus Cloud custom session header. Use interface crypto founction need add X_HERODOTUS_SESSION_ID to request header.");
            }
        }
        log.debug("[Herodotus] |- The decryption conditions are not met DecryptRequestParamResolver, skip! to next!");
        return this.requestParamMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }
}
