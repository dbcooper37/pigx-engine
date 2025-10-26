package com.pigx.engine.oauth2.persistence.sas.jpa.config;

import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusAuthorizationConsentService;
import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusAuthorizationService;
import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusRegisteredClientService;
import com.pigx.engine.oauth2.persistence.sas.jpa.storage.JpaOAuth2AuthorizationConsentService;
import com.pigx.engine.oauth2.persistence.sas.jpa.storage.JpaOAuth2AuthorizationService;
import com.pigx.engine.oauth2.persistence.sas.jpa.storage.JpaRegisteredClientRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = {"com.pigx.engine.oauth2.persistence.sas.jpa.repository"})
@EntityScan(basePackages = {"com.pigx.engine.oauth2.persistence.sas.jpa.entity"})
@ComponentScan(basePackages = {"com.pigx.engine.oauth2.persistence.sas.jpa.service"})
/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/config/OAuth2PersistenceSasJpaConfiguration.class */
public class OAuth2PersistenceSasJpaConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2PersistenceSasJpaConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Data JPA] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public RegisteredClientRepository registeredClientRepository(HerodotusRegisteredClientService herodotusRegisteredClientService, PasswordEncoder passwordEncoder) {
        JpaRegisteredClientRepository jpaRegisteredClientRepository = new JpaRegisteredClientRepository(herodotusRegisteredClientService, passwordEncoder);
        log.trace("[Herodotus] |- Bean [Jpa Registered Client Repository] Configure.");
        return jpaRegisteredClientRepository;
    }

    @ConditionalOnMissingBean
    @Bean
    public OAuth2AuthorizationService authorizationService(HerodotusAuthorizationService herodotusAuthorizationService, RegisteredClientRepository registeredClientRepository) {
        JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService = new JpaOAuth2AuthorizationService(herodotusAuthorizationService, registeredClientRepository);
        log.trace("[Herodotus] |- Bean [Jpa OAuth2 Authorization Service] Configure.");
        return jpaOAuth2AuthorizationService;
    }

    @ConditionalOnMissingBean
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(HerodotusAuthorizationConsentService herodotusAuthorizationConsentService, RegisteredClientRepository registeredClientRepository) {
        JpaOAuth2AuthorizationConsentService jpaOAuth2AuthorizationConsentService = new JpaOAuth2AuthorizationConsentService(herodotusAuthorizationConsentService, registeredClientRepository);
        log.trace("[Herodotus] |- Bean [Jpa OAuth2 Authorization Consent Service] Configure.");
        return jpaOAuth2AuthorizationConsentService;
    }
}
