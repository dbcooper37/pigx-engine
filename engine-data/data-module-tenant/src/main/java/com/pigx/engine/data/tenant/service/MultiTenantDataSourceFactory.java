package com.pigx.engine.data.tenant.service;

import com.pigx.engine.data.tenant.entity.SysTenantDataSource;
import com.pigx.engine.data.tenant.repository.SysTenantDataSourceRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/service/MultiTenantDataSourceFactory.class */
public class MultiTenantDataSourceFactory {

    @Autowired
    private SysTenantDataSourceRepository sysTenantDataSourceRepository;

    private DataSource createDataSource(DataSource defaultDataSource, SysTenantDataSource sysTenantDataSource) {
        if (defaultDataSource instanceof HikariDataSource) {
            HikariDataSource defaultHikariDataSource = (HikariDataSource) defaultDataSource;
            Properties defaultDataSourceProperties = defaultHikariDataSource.getDataSourceProperties();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(sysTenantDataSource.getDriverClassName());
            hikariConfig.setJdbcUrl(sysTenantDataSource.getUrl());
            hikariConfig.setUsername(sysTenantDataSource.getUsername());
            hikariConfig.setPassword(sysTenantDataSource.getPassword());
            if (ObjectUtils.isNotEmpty(defaultDataSource)) {
                defaultDataSourceProperties.forEach((key, value) -> {
                    hikariConfig.addDataSourceProperty(String.valueOf(key), value);
                });
            }
            return new HikariDataSource(hikariConfig);
        }
        return DataSourceBuilder.create().type(HikariDataSource.class).url(sysTenantDataSource.getUrl()).driverClassName(sysTenantDataSource.getDriverClassName()).username(sysTenantDataSource.getUsername()).password(sysTenantDataSource.getPassword()).build();
    }

    public Map<String, DataSource> getAll(DataSource defaultDataSource) {
        List<SysTenantDataSource> sysTenantDataSources = this.sysTenantDataSourceRepository.m105findAll();
        if (CollectionUtils.isNotEmpty(sysTenantDataSources)) {
            return (Map) sysTenantDataSources.stream().collect(Collectors.toMap((v0) -> {
                return v0.getTenantId();
            }, value -> {
                return createDataSource(defaultDataSource, value);
            }));
        }
        return new HashMap();
    }
}
