package com.pigx.engine.core.foundation.feature;

import java.lang.annotation.*;


/**
 * Annotation to mark methods or classes that are controlled by feature flags.
 *
 * <p>When a method is annotated with this annotation, the feature flag system
 * will check if the feature is enabled before allowing execution.</p>
 *
 * <p><b>Usage on methods:</b></p>
 * <pre>{@code
 * @FeatureFlag("new-payment-flow")
 * public PaymentResult processPayment(PaymentRequest request) {
 *     // New payment flow implementation
 * }
 * }</pre>
 *
 * <p><b>Usage with fallback:</b></p>
 * <pre>{@code
 * @FeatureFlag(value = "beta-search", fallbackMethod = "legacySearch")
 * public SearchResult search(SearchQuery query) {
 *     // New search implementation
 * }
 *
 * public SearchResult legacySearch(SearchQuery query) {
 *     // Fallback implementation
 * }
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 * @see FeatureFlagService
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeatureFlag {

    /**
     * The name of the feature flag.
     * Must match a configured feature in the feature flag service.
     */
    String value();

    /**
     * The fallback method to call when the feature is disabled.
     * Must have the same signature as the annotated method.
     * If empty, an exception is thrown when feature is disabled.
     */
    String fallbackMethod() default "";

    /**
     * Whether to throw an exception when the feature is disabled
     * and no fallback is provided.
     * If false, returns null instead.
     */
    boolean throwOnDisabled() default true;

    /**
     * The percentage of requests to enable this feature for (0-100).
     * Only used when the feature flag is in "gradual rollout" mode.
     * Default: -1 (use server configuration)
     */
    int rolloutPercentage() default -1;
}
