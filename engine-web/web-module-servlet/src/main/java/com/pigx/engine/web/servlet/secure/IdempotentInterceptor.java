package com.pigx.engine.web.servlet.secure;

import com.pigx.engine.web.core.annotation.Idempotent;
import com.pigx.engine.web.service.secure.IdempotentHandler;
import com.pigx.engine.web.service.stamp.IdempotentStampManager;
import com.pigx.engine.web.servlet.definition.AbstractHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/secure/IdempotentInterceptor.class */
public class IdempotentInterceptor extends AbstractHandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(IdempotentInterceptor.class);
    private final IdempotentStampManager idempotentStampManager;

    public IdempotentInterceptor(IdempotentStampManager idempotentStampManager) {
        this.idempotentStampManager = idempotentStampManager;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Idempotent idempotent = (Idempotent) method.getAnnotation(Idempotent.class);
        if (ObjectUtils.isNotEmpty(idempotent)) {
            String key = generateRequestKey(request);
            return IdempotentHandler.handle(key, idempotent, request.getRequestURI(), this.idempotentStampManager);
        }
        return true;
    }
}
