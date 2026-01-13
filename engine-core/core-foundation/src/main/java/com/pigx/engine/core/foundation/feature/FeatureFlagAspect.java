package com.pigx.engine.core.foundation.feature;

import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * AOP Aspect for processing {@link FeatureFlag} annotations.
 *
 * <p>This aspect intercepts method calls annotated with {@code @FeatureFlag}
 * and checks if the feature is enabled before allowing execution.</p>
 *
 * <p><b>Behavior:</b></p>
 * <ul>
 *   <li>If enabled: proceeds with the original method</li>
 *   <li>If disabled with fallback: calls the fallback method</li>
 *   <li>If disabled without fallback: throws exception or returns null</li>
 * </ul>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@Aspect
@Component
@Order(1) // Run before transaction aspects
public class FeatureFlagAspect {

    private static final Logger log = LoggerFactory.getLogger(FeatureFlagAspect.class);

    private final FeatureFlagService featureFlagService;

    public FeatureFlagAspect(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @Around("@annotation(featureFlag)")
    public Object checkFeatureFlag(ProceedingJoinPoint joinPoint, FeatureFlag featureFlag) throws Throwable {
        String featureName = featureFlag.value();
        
        log.debug("[PIGXD] |- Checking feature flag [{}] for method [{}]",
                featureName, joinPoint.getSignature().getName());

        boolean isEnabled = featureFlagService.isEnabled(featureName);

        if (isEnabled) {
            log.debug("[PIGXD] |- Feature [{}] is enabled, proceeding with original method", featureName);
            return joinPoint.proceed();
        }

        log.info("[PIGXD] |- Feature [{}] is disabled", featureName);

        // Try to call fallback method
        String fallbackMethodName = featureFlag.fallbackMethod();
        if (!fallbackMethodName.isEmpty()) {
            return invokeFallback(joinPoint, fallbackMethodName);
        }

        // No fallback - throw or return null
        if (featureFlag.throwOnDisabled()) {
            throw new FeatureDisabledException(
                    "Feature '" + featureName + "' is disabled and no fallback method is provided");
        }

        return null;
    }

    /**
     * Invokes the fallback method when feature is disabled.
     */
    private Object invokeFallback(ProceedingJoinPoint joinPoint, String fallbackMethodName) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        
        // Find the fallback method with the same parameter types
        Method fallbackMethod = findFallbackMethod(targetClass, fallbackMethodName, signature.getParameterTypes());
        
        if (fallbackMethod == null) {
            throw new FeatureDisabledException(
                    "Fallback method '" + fallbackMethodName + "' not found in " + targetClass.getName());
        }

        log.debug("[PIGXD] |- Invoking fallback method [{}]", fallbackMethodName);
        fallbackMethod.setAccessible(true);
        return fallbackMethod.invoke(joinPoint.getTarget(), joinPoint.getArgs());
    }

    /**
     * Finds the fallback method in the target class.
     */
    private Method findFallbackMethod(Class<?> targetClass, String methodName, Class<?>[] parameterTypes) {
        try {
            return targetClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            // Try in superclass
            Class<?> superClass = targetClass.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                return findFallbackMethod(superClass, methodName, parameterTypes);
            }
            return null;
        }
    }

    /**
     * Exception thrown when a feature is disabled.
     */
    public static class FeatureDisabledException extends PlatformRuntimeException {
        public FeatureDisabledException(String message) {
            super(message);
        }
    }
}
