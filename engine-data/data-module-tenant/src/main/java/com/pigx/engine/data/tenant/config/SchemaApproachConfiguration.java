package com.pigx.engine.data.tenant.config;

import com.pigx.engine.data.hibernate.tenant.SchemaMultiTenantConnectionProvider;
import com.pigx.engine.data.tenant.condition.ConditionalOnMultiTenant;
import com.pigx.engine.data.tenant.enums.MultiTenant;
import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnMultiTenant(MultiTenant.SCHEMA)
/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/config/SchemaApproachConfiguration.class */
class SchemaApproachConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SchemaApproachConfiguration.class);

    SchemaApproachConfiguration() {
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Schema Approach] Configure.");
    }

    @Bean
    public HibernatePropertiesCustomizer schemaMultiTenantConnectionProvider(DataSource dataSource) {
        SchemaMultiTenantConnectionProvider provider = new SchemaMultiTenantConnectionProvider(dataSource);
        log.trace("[Herodotus] |- Bean [Multi Tenant Connection Provider] Configure.");
        return provider;
    }
}
