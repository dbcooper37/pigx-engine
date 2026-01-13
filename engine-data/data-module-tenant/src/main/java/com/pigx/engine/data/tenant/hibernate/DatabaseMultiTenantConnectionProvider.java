package com.pigx.engine.data.tenant.hibernate;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.data.tenant.service.MultiTenantDataSourceFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Multi-tenant connection provider for database-per-tenant architecture.
 * <p>
 * This provider manages separate datasources for each tenant, loading them
 * lazily from the database on first access.
 * </p>
 *
 * <p><b>Thread Safety:</b> This class is thread-safe. Initialization is
 * performed exactly once using double-checked locking with AtomicBoolean.</p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class DatabaseMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> implements HibernatePropertiesCustomizer {

    private static final Logger log = LoggerFactory.getLogger(DatabaseMultiTenantConnectionProvider.class);
    
    private final ConcurrentMap<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final DataSource defaultDataSource;
    private final AtomicBoolean isDataSourceInit = new AtomicBoolean(false);
    private final ReentrantLock initLock = new ReentrantLock();

    public DatabaseMultiTenantConnectionProvider(DataSource dataSource) {
        this.defaultDataSource = dataSource;
        dataSources.put(SystemConstants.TENANT_ID, dataSource);
    }

    private void initialize() {
        MultiTenantDataSourceFactory factory = SpringUtil.getBean(MultiTenantDataSourceFactory.class);
        dataSources.putAll(factory.getAll(defaultDataSource));
        log.info("[PIGXD] |- Multi-tenant datasources initialized. Total tenants: {}", dataSources.size());
    }

    /**
     * 在没有指定 tenantId 的情况下选择的数据源（例如启动处理）
     *
     * @return {@link DataSource}
     */
    @Override
    protected DataSource selectAnyDataSource() {
        log.debug("[PIGXD] |- Select any dataSource: " + defaultDataSource);
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        // Thread-safe lazy initialization with double-checked locking
        if (!isDataSourceInit.get()) {
            initLock.lock();
            try {
                if (!isDataSourceInit.get()) {
                    initialize();
                    isDataSourceInit.set(true);
                }
            } finally {
                initLock.unlock();
            }
        }

        DataSource currentDataSource = dataSources.get(tenantIdentifier);
        if (ObjectUtils.isNotEmpty(currentDataSource)) {
            log.debug("[PIGXD] |- Found the multi tenant dataSource for id : [{}]", tenantIdentifier);
            return currentDataSource;
        } else {
            log.warn("[PIGXD] |- Cannot find the dataSource for tenant [{}], using default.", tenantIdentifier);
            return defaultDataSource;
        }
    }
    
    /**
     * Adds a new tenant datasource at runtime.
     *
     * @param tenantId   the tenant identifier
     * @param dataSource the datasource for this tenant
     */
    public void addTenantDataSource(String tenantId, DataSource dataSource) {
        dataSources.put(tenantId, dataSource);
        log.info("[PIGXD] |- Added datasource for tenant [{}]", tenantId);
    }
    
    /**
     * Removes a tenant datasource.
     *
     * @param tenantId the tenant identifier to remove
     * @return the removed datasource, or null if not found
     */
    public DataSource removeTenantDataSource(String tenantId) {
        DataSource removed = dataSources.remove(tenantId);
        if (removed != null) {
            log.info("[PIGXD] |- Removed datasource for tenant [{}]", tenantId);
        }
        return removed;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
