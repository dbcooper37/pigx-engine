package com.pigx.engine.data.tenant.service;

import com.pigx.engine.data.tenant.entity.SysTenantDataSource;
import com.pigx.engine.data.tenant.properties.HikariPoolProperties;
import com.pigx.engine.data.tenant.repository.SysTenantDataSourceRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * Factory for creating and managing multi-tenant datasources.
 * <p>
 * This factory creates HikariCP datasources for each tenant and manages their lifecycle,
 * ensuring proper cleanup when tenants are removed or the application shuts down.
 * </p>
 *
 * <p><b>Thread Safety:</b> This class is thread-safe. All datasource operations
 * are performed on a ConcurrentHashMap.</p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@Component
public class MultiTenantDataSourceFactory implements DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(MultiTenantDataSourceFactory.class);

    /**
     * Map of tenant ID to managed datasource for lifecycle tracking.
     */
    private final ConcurrentMap<String, HikariDataSource> managedDataSources = new ConcurrentHashMap<>();

    @Autowired
    private SysTenantDataSourceRepository sysTenantDataSourceRepository;

    @Autowired(required = false)
    private HikariPoolProperties hikariPoolProperties;

    /**
     * Creates a new datasource for the given tenant configuration.
     *
     * @param defaultDataSource   the default datasource to copy settings from
     * @param sysTenantDataSource the tenant datasource configuration
     * @return the created datasource
     */
    private HikariDataSource createDataSource(DataSource defaultDataSource, SysTenantDataSource sysTenantDataSource) {
        HikariDataSource dataSource;
        
        if (defaultDataSource instanceof HikariDataSource defaultHikariDataSource) {
            Properties defaultDataSourceProperties = defaultHikariDataSource.getDataSourceProperties();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(sysTenantDataSource.getDriverClassName());
            hikariConfig.setJdbcUrl(sysTenantDataSource.getUrl());
            hikariConfig.setUsername(sysTenantDataSource.getUsername());
            hikariConfig.setPassword(sysTenantDataSource.getPassword());
            
            // Apply configurable pool settings
            applyPoolConfiguration(hikariConfig, sysTenantDataSource.getTenantId());

            if (ObjectUtils.isNotEmpty(defaultDataSource)) {
                defaultDataSourceProperties.forEach((key, value) -> 
                        hikariConfig.addDataSourceProperty(String.valueOf(key), value));
            }

            dataSource = new HikariDataSource(hikariConfig);
        } else {
            dataSource = DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .url(sysTenantDataSource.getUrl())
                    .driverClassName(sysTenantDataSource.getDriverClassName())
                    .username(sysTenantDataSource.getUsername())
                    .password(sysTenantDataSource.getPassword())
                    .build();
        }
        
        // Track datasource for lifecycle management
        managedDataSources.put(sysTenantDataSource.getTenantId(), dataSource);
        log.info("[PIGXD] |- Created datasource for tenant [{}]", sysTenantDataSource.getTenantId());
        
        return dataSource;
    }

    /**
     * Applies configurable pool settings to the HikariConfig.
     * Uses properties if available, otherwise uses sensible defaults.
     *
     * @param config   the HikariConfig to configure
     * @param tenantId the tenant identifier for pool naming
     */
    private void applyPoolConfiguration(HikariConfig config, String tenantId) {
        if (hikariPoolProperties != null) {
            config.setPoolName(hikariPoolProperties.getPoolNamePrefix() + "-" + tenantId);
            config.setMaximumPoolSize(hikariPoolProperties.getMaximumPoolSize());
            config.setMinimumIdle(hikariPoolProperties.getMinimumIdle());
            config.setConnectionTimeout(hikariPoolProperties.getConnectionTimeout().toMillis());
            config.setIdleTimeout(hikariPoolProperties.getIdleTimeout().toMillis());
            config.setMaxLifetime(hikariPoolProperties.getMaxLifetime().toMillis());
            config.setLeakDetectionThreshold(hikariPoolProperties.getLeakDetectionThreshold().toMillis());
            config.setRegisterMbeans(hikariPoolProperties.isRegisterMbeans());
            config.setAutoCommit(hikariPoolProperties.isAutoCommit());
            config.setConnectionTestQuery(hikariPoolProperties.getConnectionTestQuery());
            config.setValidationTimeout(hikariPoolProperties.getValidationTimeout().toMillis());
            
            log.debug("[PIGXD] |- Applied custom HikariCP config for tenant [{}]: maxPoolSize={}, minIdle={}", 
                    tenantId, hikariPoolProperties.getMaximumPoolSize(), hikariPoolProperties.getMinimumIdle());
        } else {
            // Sensible defaults when properties are not configured
            config.setPoolName("HikariPool-" + tenantId);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            config.setLeakDetectionThreshold(60000);
            config.setRegisterMbeans(true);
            config.setAutoCommit(false);
            config.setConnectionTestQuery("SELECT 1");
            
            log.debug("[PIGXD] |- Applied default HikariCP config for tenant [{}]", tenantId);
        }
    }

    /**
     * Gets all managed datasources.
     *
     * @return read-only view of managed datasources
     */
    public Map<String, HikariDataSource> getManagedDataSources() {
        return Map.copyOf(managedDataSources);
    }

    /**
     * Gets all tenant datasources, creating them if necessary.
     *
     * @param defaultDataSource the default datasource to copy settings from
     * @return map of tenant ID to datasource
     */
    public Map<String, DataSource> getAll(DataSource defaultDataSource) {
        List<SysTenantDataSource> sysTenantDataSources = sysTenantDataSourceRepository.findAll();
        if (CollectionUtils.isNotEmpty(sysTenantDataSources)) {
            return sysTenantDataSources.stream()
                    .collect(Collectors.toMap(
                            SysTenantDataSource::getTenantId,
                            value -> createDataSource(defaultDataSource, value)
                    ));
        } else {
            return new HashMap<>();
        }
    }

    /**
     * Removes and closes the datasource for the specified tenant.
     *
     * @param tenantId the tenant identifier
     */
    public void removeTenant(String tenantId) {
        HikariDataSource dataSource = managedDataSources.remove(tenantId);
        if (dataSource != null) {
            closeDataSource(dataSource, tenantId);
        } else {
            log.warn("[PIGXD] |- No datasource found for tenant [{}] to remove", tenantId);
        }
    }

    /**
     * Gets the datasource for a specific tenant.
     *
     * @param tenantId the tenant identifier
     * @return the datasource, or null if not found
     */
    public HikariDataSource getDataSource(String tenantId) {
        return managedDataSources.get(tenantId);
    }

    /**
     * Checks if a datasource exists for the specified tenant.
     *
     * @param tenantId the tenant identifier
     * @return true if datasource exists
     */
    public boolean hasDataSource(String tenantId) {
        return managedDataSources.containsKey(tenantId);
    }

    /**
     * Gets the number of managed datasources.
     *
     * @return the count of managed datasources
     */
    public int getDataSourceCount() {
        return managedDataSources.size();
    }

    /**
     * Safely closes a datasource with error handling.
     */
    private void closeDataSource(HikariDataSource dataSource, String tenantId) {
        try {
            if (!dataSource.isClosed()) {
                dataSource.close();
                log.info("[PIGXD] |- Closed datasource for tenant [{}]", tenantId);
            }
        } catch (Exception e) {
            log.error("[PIGXD] |- Error closing datasource for tenant [{}]: {}", tenantId, e.getMessage());
        }
    }

    /**
     * Cleanup all managed datasources on application shutdown.
     */
    @Override
    @PreDestroy
    public void destroy() {
        log.info("[PIGXD] |- Closing all managed datasources. Total: {}", managedDataSources.size());
        
        managedDataSources.forEach((tenantId, dataSource) -> closeDataSource(dataSource, tenantId));
        managedDataSources.clear();
        
        log.info("[PIGXD] |- All managed datasources closed successfully");
    }
}
