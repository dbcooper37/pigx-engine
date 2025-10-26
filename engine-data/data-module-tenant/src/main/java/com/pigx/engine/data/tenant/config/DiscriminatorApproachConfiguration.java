package com.pigx.engine.data.tenant.config;

import com.pigx.engine.data.hibernate.tenant.HerodotusTenantIdentifierResolver;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/config/DiscriminatorApproachConfiguration.class */
class DiscriminatorApproachConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DiscriminatorApproachConfiguration.class);

    DiscriminatorApproachConfiguration() {
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Discriminator Approach] Configure.");
    }

    @Bean
    public HibernatePropertiesCustomizer herodotusTenantIdentifierResolver() {
        HerodotusTenantIdentifierResolver resolver = new HerodotusTenantIdentifierResolver();
        log.trace("[Herodotus] |- Bean [Current Tenant Identifier Resolver] Configure.");
        return resolver;
    }
}
