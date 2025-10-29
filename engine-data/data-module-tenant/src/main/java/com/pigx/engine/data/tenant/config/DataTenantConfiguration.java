package com.pigx.engine.data.tenant.config;

import com.pigx.engine.data.tenant.properties.MultiTenantProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MultiTenantProperties.class)
@Import({
        DiscriminatorApproachConfiguration.class,
        SchemaApproachConfiguration.class,
        DatabaseApproachConfiguration.class,
})
public class DataTenantConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DataTenantConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Tenant] Configure.");
    }
}
