package com.pigx.engine.facility.alibaba.autoconfigure.sentinel.enhance;

import com.google.common.base.Objects;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.exception.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


public class HerodotusFeignFallback<T> implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HerodotusFeignFallback.class);

    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;

    public HerodotusFeignFallback(Class<T> targetType, String targetName, Throwable cause) {
        this.targetType = targetType;
        this.targetName = targetName;
        this.cause = cause;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        String errorMessage = cause.getMessage();
        String path = targetType.getName() + SymbolConstants.FORWARD_SLASH + method.getName();

        Result<String> result = GlobalExceptionHandler.resolveException((Exception) cause, path);
        log.error("[PIGXD] |- Feign remote call fallback : [{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusFeignFallback<?> that = (HerodotusFeignFallback<?>) o;
        return Objects.equal(targetType, that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(targetType);
    }
}
