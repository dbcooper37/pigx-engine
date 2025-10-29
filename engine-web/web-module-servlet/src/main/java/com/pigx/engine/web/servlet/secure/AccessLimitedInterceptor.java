package com.pigx.engine.web.servlet.secure;

import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.service.secure.AccessLimitedHandler;
import com.pigx.engine.web.service.stamp.AccessLimitedStampManager;
import com.pigx.engine.web.servlet.definition.AbstractHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;


public class AccessLimitedInterceptor extends AbstractHandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AccessLimitedInterceptor.class);

    private final AccessLimitedStampManager accessLimitedStampManager;

    public AccessLimitedInterceptor(AccessLimitedStampManager accessLimitedStampManager) {
        this.accessLimitedStampManager = accessLimitedStampManager;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.trace("[PIGXD] |- AccessLimitedInterceptor preHandle postProcess.");

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();

        AccessLimited accessLimited = method.getAnnotation(AccessLimited.class);
        if (ObjectUtils.isNotEmpty(accessLimited)) {
            String key = generateRequestKey(request);
            return AccessLimitedHandler.handle(key, accessLimited, request.getRequestURI(), accessLimitedStampManager);
        }

        return true;
    }
}
