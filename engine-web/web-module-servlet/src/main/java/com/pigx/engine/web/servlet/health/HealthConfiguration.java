package com.pigx.engine.web.servlet.health;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Configuration for health check endpoints.
 *
 * <p>This configuration sets up custom health indicators for:</p>
 * <ul>
 *   <li>Database connection pool monitoring</li>
 *   <li>Feature flag service status</li>
 *   <li>Kubernetes readiness/liveness probes</li>
 * </ul>
 *
 * <p><b>Application Properties:</b></p>
 * <pre>{@code
 * # Enable all health details
 * management.endpoint.health.show-details=always
 *
 * # Enable health groups for Kubernetes
 * management.endpoint.health.group.liveness.include=livenessState
 * management.endpoint.health.group.readiness.include=readinessState,db
 *
 * # Expose health endpoints
 * management.endpoints.web.exposure.include=health,info,metrics,prometheus
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HealthEndpointGroups.class)
@Import({
        CustomHealthIndicators.DatabasePoolHealthIndicator.class,
        CustomHealthIndicators.FeatureFlagHealthIndicator.class,
        ReadinessHealthIndicator.class
})
public class HealthConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HealthConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Module [Health Indicators] Configure.");
    }
}
