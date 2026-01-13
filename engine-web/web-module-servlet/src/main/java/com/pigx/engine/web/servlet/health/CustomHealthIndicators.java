package com.pigx.engine.web.servlet.health;

import com.pigx.engine.core.foundation.feature.FeatureFlagService;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


/**
 * Custom health indicators for application monitoring.
 *
 * <p>These indicators provide detailed health information for:</p>
 * <ul>
 *   <li>Database connection pools</li>
 *   <li>Feature flag service</li>
 *   <li>External service dependencies</li>
 * </ul>
 *
 * <p><b>Endpoints:</b></p>
 * <ul>
 *   <li>{@code /actuator/health} - Overall health status</li>
 *   <li>{@code /actuator/health/dbPool} - Database pool health</li>
 *   <li>{@code /actuator/health/featureFlags} - Feature flags health</li>
 * </ul>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class CustomHealthIndicators {

    /**
     * Health indicator for HikariCP database connection pool.
     */
    @Component("dbPoolHealth")
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnBean(DataSource.class)
    public static class DatabasePoolHealthIndicator implements HealthIndicator {

        private static final Logger log = LoggerFactory.getLogger(DatabasePoolHealthIndicator.class);

        private final DataSource dataSource;

        @Autowired
        public DatabasePoolHealthIndicator(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public Health health() {
            try {
                if (dataSource instanceof HikariDataSource hikari) {
                    int activeConnections = hikari.getHikariPoolMXBean().getActiveConnections();
                    int idleConnections = hikari.getHikariPoolMXBean().getIdleConnections();
                    int totalConnections = hikari.getHikariPoolMXBean().getTotalConnections();
                    int maxPoolSize = hikari.getMaximumPoolSize();
                    int waitingThreads = hikari.getHikariPoolMXBean().getThreadsAwaitingConnection();

                    // Calculate pool utilization
                    double utilization = (double) activeConnections / maxPoolSize * 100;

                    Health.Builder builder = Health.up()
                            .withDetail("poolName", hikari.getPoolName())
                            .withDetail("activeConnections", activeConnections)
                            .withDetail("idleConnections", idleConnections)
                            .withDetail("totalConnections", totalConnections)
                            .withDetail("maxPoolSize", maxPoolSize)
                            .withDetail("waitingThreads", waitingThreads)
                            .withDetail("utilizationPercent", String.format("%.1f%%", utilization));

                    // Warn if pool is getting exhausted
                    if (utilization > 80) {
                        builder.status("DEGRADED")
                                .withDetail("warning", "Connection pool utilization is high");
                    }

                    // Alert if threads are waiting
                    if (waitingThreads > 0) {
                        builder.withDetail("alert", "Threads waiting for connections: " + waitingThreads);
                    }

                    return builder.build();
                }

                // Non-Hikari datasource
                return Health.up()
                        .withDetail("type", dataSource.getClass().getSimpleName())
                        .build();

            } catch (Exception e) {
                log.error("[PIGXD] |- Database pool health check failed", e);
                return Health.down()
                        .withException(e)
                        .build();
            }
        }
    }

    /**
     * Health indicator for feature flag service.
     */
    @Component("featureFlagsHealth")
    @ConditionalOnBean(FeatureFlagService.class)
    public static class FeatureFlagHealthIndicator implements HealthIndicator {

        private static final Logger log = LoggerFactory.getLogger(FeatureFlagHealthIndicator.class);

        private final FeatureFlagService featureFlagService;

        @Autowired
        public FeatureFlagHealthIndicator(FeatureFlagService featureFlagService) {
            this.featureFlagService = featureFlagService;
        }

        @Override
        public Health health() {
            try {
                var features = featureFlagService.getAllFeatures();
                
                long enabledCount = features.values().stream()
                        .filter(config -> config.isEnabled())
                        .count();
                
                long disabledCount = features.size() - enabledCount;

                return Health.up()
                        .withDetail("totalFlags", features.size())
                        .withDetail("enabledFlags", enabledCount)
                        .withDetail("disabledFlags", disabledCount)
                        .withDetail("status", "Feature flag service is operational")
                        .build();

            } catch (Exception e) {
                log.error("[PIGXD] |- Feature flag health check failed", e);
                return Health.down()
                        .withException(e)
                        .build();
            }
        }
    }
}
