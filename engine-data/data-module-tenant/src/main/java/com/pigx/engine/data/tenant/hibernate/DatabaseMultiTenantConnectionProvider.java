package com.pigx.engine.data.tenant.hibernate;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.data.tenant.service.MultiTenantDataSourceFactory;
import cn.hutool.v7.extra.spring.SpringUtil;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/hibernate/DatabaseMultiTenantConnectionProvider.class */
public class DatabaseMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> implements HibernatePropertiesCustomizer {
    private static final Logger log = LoggerFactory.getLogger(DatabaseMultiTenantConnectionProvider.class);
    private final DataSource defaultDataSource;
    private final Map<String, DataSource> dataSources = new HashMap();
    private boolean isDataSourceInit = false;

    public DatabaseMultiTenantConnectionProvider(DataSource dataSource) {
        this.defaultDataSource = dataSource;
        this.dataSources.put(SystemConstants.TENANT_ID, dataSource);
    }

    private void initialize() {
        this.isDataSourceInit = true;
        MultiTenantDataSourceFactory factory = (MultiTenantDataSourceFactory) SpringUtil.getBean(MultiTenantDataSourceFactory.class, new Object[0]);
        this.dataSources.putAll(factory.getAll(this.defaultDataSource));
    }

    protected DataSource selectAnyDataSource() {
        log.debug("[Herodotus] |- Select any dataSource: " + String.valueOf(this.defaultDataSource));
        return this.defaultDataSource;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DataSource selectDataSource(String tenantIdentifier) {
        if (!this.isDataSourceInit) {
            initialize();
        }
        DataSource currentDataSource = this.dataSources.get(tenantIdentifier);
        if (ObjectUtils.isNotEmpty(currentDataSource)) {
            log.debug("[Herodotus] |- Found the multi tenant dataSource for id : [{}]", tenantIdentifier);
            return currentDataSource;
        }
        log.warn("[Herodotus] |- Cannot found the dataSource for tenant [{}], change to use default.", tenantIdentifier);
        return this.defaultDataSource;
    }

    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.multi_tenant_connection_provider", this);
    }
}
