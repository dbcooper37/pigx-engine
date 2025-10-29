package com.pigx.engine.data.tenant.config;

import com.pigx.engine.data.hibernate.tenant.HerodotusTenantIdentifierResolver;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
class DiscriminatorApproachConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DiscriminatorApproachConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Discriminator Approach] Configure.");
    }

    @Bean
    public HibernatePropertiesCustomizer herodotusTenantIdentifierResolver() {
        HerodotusTenantIdentifierResolver resolver = new HerodotusTenantIdentifierResolver();
        log.trace("[PIGXD] |- Bean [Current Tenant Identifier Resolver] Configure.");
        return resolver;
    }
}
