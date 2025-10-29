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
import java.util.HashMap;
import java.util.Map;


public class DatabaseMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> implements HibernatePropertiesCustomizer {

    private static final Logger log = LoggerFactory.getLogger(DatabaseMultiTenantConnectionProvider.class);
    private final Map<String, DataSource> dataSources = new HashMap<>();
    private final DataSource defaultDataSource;
    private boolean isDataSourceInit = false;

    public DatabaseMultiTenantConnectionProvider(DataSource dataSource) {
        this.defaultDataSource = dataSource;
        dataSources.put(SystemConstants.TENANT_ID, dataSource);
    }

    private void initialize() {
        isDataSourceInit = true;
        MultiTenantDataSourceFactory factory = SpringUtil.getBean(MultiTenantDataSourceFactory.class);
        dataSources.putAll(factory.getAll(defaultDataSource));
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
        if (!isDataSourceInit) {
            initialize();
        }

        DataSource currentDataSource = dataSources.get(tenantIdentifier);
        if (ObjectUtils.isNotEmpty(currentDataSource)) {
            log.debug("[PIGXD] |- Found the multi tenant dataSource for id : [{}]", tenantIdentifier);
            return currentDataSource;
        } else {
            log.warn("[PIGXD] |- Cannot found the dataSource for tenant [{}], change to use default.", tenantIdentifier);
            return defaultDataSource;
        }
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
