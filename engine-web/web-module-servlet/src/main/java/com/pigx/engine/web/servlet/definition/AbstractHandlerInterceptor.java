package com.pigx.engine.web.servlet.definition;

import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import cn.hutool.v7.crypto.SecureUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/definition/AbstractHandlerInterceptor.class */
public abstract class AbstractHandlerInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AbstractHandlerInterceptor.class);

    protected String generateRequestKey(HttpServletRequest request) {
        String sessionId = SessionUtils.analyseSessionId(request);
        String url = request.getRequestURI();
        String method = request.getMethod();
        if (StringUtils.isNotBlank(sessionId)) {
            String key = SecureUtil.md5(sessionId + ":" + url + ":" + method);
            log.debug("[Herodotus] |- IdempotentInterceptor key is [{}].", key);
            return key;
        }
        log.warn("[Herodotus] |- IdempotentInterceptor cannot create key, because sessionId is null.");
        return null;
    }
}
