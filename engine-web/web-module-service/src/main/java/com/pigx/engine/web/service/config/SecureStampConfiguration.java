package com.pigx.engine.web.service.config;

import com.pigx.engine.web.service.properties.SecureProperties;
import com.pigx.engine.web.service.stamp.AccessLimitedStampManager;
import com.pigx.engine.web.service.stamp.IdempotentStampManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SecureProperties.class})
public class SecureStampConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecureStampConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Protect Secure] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public IdempotentStampManager idempotentStampManager(SecureProperties secureProperties) {
        IdempotentStampManager idempotentStampManager = new IdempotentStampManager(secureProperties);
        log.trace("[PIGXD] |- Bean [Idempotent Stamp Manager] Configure.");
        return idempotentStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessLimitedStampManager accessLimitedStampManager(SecureProperties secureProperties) {
        AccessLimitedStampManager accessLimitedStampManager = new AccessLimitedStampManager(secureProperties);
        log.trace("[PIGXD] |- Bean [Access Limited Stamp Manager] Configure.");
        return accessLimitedStampManager;
    }
}
