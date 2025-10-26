package com.pigx.engine.logic.identity.config;

import com.pigx.engine.core.identity.service.ClientDetailsService;
import com.pigx.engine.logic.identity.definition.HerodotusClientDetailsService;
import com.pigx.engine.logic.identity.service.OAuth2ApplicationService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = {"com.pigx.engine.logic.identity.repository"})
@EntityScan(basePackages = {"com.pigx.engine.logic.identity.entity"})
@ComponentScan(basePackages = {"com.pigx.engine.logic.identity.service"})
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/config/LogicIdentityConfiguration.class */
public class LogicIdentityConfiguration {
    private static final Logger log = LoggerFactory.getLogger(LogicIdentityConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Logic Identity] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public ClientDetailsService clientDetailsService(OAuth2ApplicationService applicationService) {
        HerodotusClientDetailsService herodotusClientDetailsService = new HerodotusClientDetailsService(applicationService);
        log.trace("[Herodotus] |- Bean [Herodotus Client Details Service] Configure.");
        return herodotusClientDetailsService;
    }
}
