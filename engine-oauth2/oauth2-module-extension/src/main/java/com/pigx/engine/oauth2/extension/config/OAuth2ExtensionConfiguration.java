package com.pigx.engine.oauth2.extension.config;

import com.pigx.engine.oauth2.extension.listener.AuthenticationFailureListener;
import com.pigx.engine.oauth2.extension.listener.AuthenticationSuccessListener;
import com.pigx.engine.oauth2.extension.manager.OAuth2ComplianceManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = {"com.pigx.engine.oauth2.extension.repository"})
@EntityScan(basePackages = {"com.pigx.engine.oauth2.extension.entity"})
@ComponentScan(basePackages = {"com.pigx.engine.oauth2.extension.service"})
@Import({OAuth2ComplianceConfiguration.class})
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/config/OAuth2ExtensionConfiguration.class */
public class OAuth2ExtensionConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2ExtensionConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Management] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public AuthenticationFailureListener authenticationFailureListener(OAuth2ComplianceManager oauth2ComplianceManager) {
        AuthenticationFailureListener listener = new AuthenticationFailureListener(oauth2ComplianceManager);
        log.trace("[Herodotus] |- Bean [OAuth2 Authentication Failure Listener] Configure.");
        return listener;
    }

    @ConditionalOnMissingBean
    @Bean
    public AuthenticationSuccessListener authenticationSuccessListener(OAuth2ComplianceManager oauth2ComplianceManager) {
        AuthenticationSuccessListener listener = new AuthenticationSuccessListener(oauth2ComplianceManager);
        log.trace("[Herodotus] |- Bean [OAuth2 Authentication Success Listener] Configure.");
        return listener;
    }
}
