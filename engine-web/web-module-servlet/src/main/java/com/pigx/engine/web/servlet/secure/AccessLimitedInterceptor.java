package com.pigx.engine.web.servlet.secure;

import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.service.secure.AccessLimitedHandler;
import com.pigx.engine.web.service.stamp.AccessLimitedStampManager;
import com.pigx.engine.web.servlet.definition.AbstractHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/secure/AccessLimitedInterceptor.class */
public class AccessLimitedInterceptor extends AbstractHandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AccessLimitedInterceptor.class);
    private final AccessLimitedStampManager accessLimitedStampManager;

    public AccessLimitedInterceptor(AccessLimitedStampManager accessLimitedStampManager) {
        this.accessLimitedStampManager = accessLimitedStampManager;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.trace("[Herodotus] |- AccessLimitedInterceptor preHandle postProcess.");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AccessLimited accessLimited = (AccessLimited) method.getAnnotation(AccessLimited.class);
        if (ObjectUtils.isNotEmpty(accessLimited)) {
            String key = generateRequestKey(request);
            return AccessLimitedHandler.handle(key, accessLimited, request.getRequestURI(), this.accessLimitedStampManager);
        }
        return true;
    }
}
