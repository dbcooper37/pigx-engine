package com.pigx.engine.core.foundation.feature;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * Auto-configuration for the feature flag system.
 *
 * <p>This configuration is enabled by default and provides:</p>
 * <ul>
 *   <li>Feature flag service for programmatic checks</li>
 *   <li>AOP aspect for annotation-based feature flags</li>
 *   <li>Configuration properties binding</li>
 * </ul>
 *
 * <p><b>To disable:</b></p>
 * <pre>{@code
 * herodotus:
 *   features:
 *     enabled: false
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableConfigurationProperties(FeatureFlagProperties.class)
public class FeatureFlagConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FeatureFlagConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Module [Feature Flags] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public FeatureFlagService featureFlagService(FeatureFlagProperties properties) {
        FeatureFlagService service = new FeatureFlagService(properties);
        log.trace("[PIGXD] |- Bean [Feature Flag Service] Configure.");
        return service;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "herodotus.features.enabled", havingValue = "true", matchIfMissing = true)
    public FeatureFlagAspect featureFlagAspect(FeatureFlagService featureFlagService) {
        FeatureFlagAspect aspect = new FeatureFlagAspect(featureFlagService);
        log.trace("[PIGXD] |- Bean [Feature Flag Aspect] Configure.");
        return aspect;
    }
}
