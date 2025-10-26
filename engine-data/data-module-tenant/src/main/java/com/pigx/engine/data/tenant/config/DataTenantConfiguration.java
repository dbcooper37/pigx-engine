package com.pigx.engine.data.tenant.config;

import com.pigx.engine.data.tenant.properties.MultiTenantProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties({MultiTenantProperties.class})
@Configuration(proxyBeanMethods = false)
@Import({DiscriminatorApproachConfiguration.class, SchemaApproachConfiguration.class, DatabaseApproachConfiguration.class})
/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/config/DataTenantConfiguration.class */
public class DataTenantConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataTenantConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Tenant] Configure.");
    }
}
