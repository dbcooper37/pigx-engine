package com.pigx.engine.data.tenant.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;


/**
 * Configuration properties for HikariCP connection pool tuning.
 * <p>
 * These properties allow fine-tuning of the HikariCP connection pool
 * for multi-tenant datasources.
 * </p>
 *
 * <p><b>Recommended settings:</b></p>
 * <ul>
 *   <li>For high-traffic tenants: Increase maximumPoolSize and minimumIdle</li>
 *   <li>For low-traffic tenants: Use lower values to conserve resources</li>
 *   <li>Enable leak detection in development environments</li>
 * </ul>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "herodotus.data.hikari")
public class HikariPoolProperties {

    /**
     * Maximum number of connections in the pool.
     * Default: 10
     */
    private int maximumPoolSize = 10;

    /**
     * Minimum number of idle connections maintained in the pool.
     * Default: 5
     */
    private int minimumIdle = 5;

    /**
     * Maximum time a client will wait for a connection from the pool.
     * Default: 30 seconds
     */
    private Duration connectionTimeout = Duration.ofSeconds(30);

    /**
     * Maximum time a connection can sit idle in the pool.
     * Default: 10 minutes
     */
    private Duration idleTimeout = Duration.ofMinutes(10);

    /**
     * Maximum lifetime of a connection in the pool.
     * Default: 30 minutes
     */
    private Duration maxLifetime = Duration.ofMinutes(30);

    /**
     * Threshold for connection leak detection.
     * Set to 0 to disable leak detection.
     * Default: 60 seconds (enabled in development)
     */
    private Duration leakDetectionThreshold = Duration.ofSeconds(60);

    /**
     * Whether to register JMX MBeans for pool monitoring.
     * Default: true
     */
    private boolean registerMbeans = true;

    /**
     * SQL query to validate connections.
     * Default: SELECT 1
     */
    private String connectionTestQuery = "SELECT 1";

    /**
     * Auto-commit mode for connections.
     * Default: false (for transaction safety)
     */
    private boolean autoCommit = false;

    /**
     * Pool name prefix for tenant pools.
     * Default: HikariPool-Tenant
     */
    private String poolNamePrefix = "HikariPool-Tenant";

    /**
     * Validation timeout for testing connections.
     * Default: 5 seconds
     */
    private Duration validationTimeout = Duration.ofSeconds(5);

    // Getters and Setters

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public Duration getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Duration getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Duration idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Duration getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Duration maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public Duration getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(Duration leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }

    public boolean isRegisterMbeans() {
        return registerMbeans;
    }

    public void setRegisterMbeans(boolean registerMbeans) {
        this.registerMbeans = registerMbeans;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public String getPoolNamePrefix() {
        return poolNamePrefix;
    }

    public void setPoolNamePrefix(String poolNamePrefix) {
        this.poolNamePrefix = poolNamePrefix;
    }

    public Duration getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(Duration validationTimeout) {
        this.validationTimeout = validationTimeout;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maximumPoolSize", maximumPoolSize)
                .add("minimumIdle", minimumIdle)
                .add("connectionTimeout", connectionTimeout)
                .add("idleTimeout", idleTimeout)
                .add("maxLifetime", maxLifetime)
                .add("leakDetectionThreshold", leakDetectionThreshold)
                .add("registerMbeans", registerMbeans)
                .add("autoCommit", autoCommit)
                .toString();
    }
}
