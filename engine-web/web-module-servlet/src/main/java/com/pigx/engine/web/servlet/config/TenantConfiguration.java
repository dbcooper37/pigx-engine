package com.pigx.engine.web.servlet.config;

import com.pigx.engine.web.servlet.tenant.MultiTenantInterceptor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class TenantConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TenantConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Protect Tenant] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public MultiTenantInterceptor tenantInterceptor() {
        MultiTenantInterceptor multiTenantInterceptor = new MultiTenantInterceptor();
        log.trace("[PIGXD] |- Bean [Idempotent Interceptor] Configure.");
        return multiTenantInterceptor;
    }
}
