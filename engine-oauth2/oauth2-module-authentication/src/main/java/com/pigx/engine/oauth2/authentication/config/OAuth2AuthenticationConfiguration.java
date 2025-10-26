package com.pigx.engine.oauth2.authentication.config;

import com.pigx.engine.oauth2.authentication.configurer.OAuth2AuthenticationConfigurerManager;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusJwtTokenCustomizer;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusOpaqueTokenCustomizer;
import com.pigx.engine.oauth2.authentication.response.DefaultOAuth2AuthenticationEventPublisher;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration(proxyBeanMethods = false)
/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/config/OAuth2AuthenticationConfiguration.class */
public class OAuth2AuthenticationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Authentication] Configure.");
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationContext applicationContext) {
        DefaultOAuth2AuthenticationEventPublisher publisher = new DefaultOAuth2AuthenticationEventPublisher(applicationContext);
        publisher.setDefaultAuthenticationFailureEvent(AuthenticationFailureBadCredentialsEvent.class);
        log.trace("[Herodotus] |- Bean [Authentication Event Publisher] Configure.");
        return publisher;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        HerodotusJwtTokenCustomizer customizer = new HerodotusJwtTokenCustomizer();
        log.trace("[Herodotus] |- Bean [OAuth2 Jwt Token Customizer] Configure.");
        return customizer;
    }

    @Bean
    public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> opaqueTokenCustomizer() {
        HerodotusOpaqueTokenCustomizer customizer = new HerodotusOpaqueTokenCustomizer();
        log.trace("[Herodotus] |- Bean [OAuth2 Opaque Token Customizer] Configure.");
        return customizer;
    }

    @ConditionalOnMissingBean
    @Bean
    public OAuth2AuthenticationConfigurerManager oauth2AuthenticationConfigurerManager(ThymeleafTemplateHandler thymeleafTemplateHandler, HttpCryptoProcessor httpCryptoProcessor, OAuth2AuthenticationProperties authenticationProperties) {
        OAuth2AuthenticationConfigurerManager configurer = new OAuth2AuthenticationConfigurerManager(thymeleafTemplateHandler, httpCryptoProcessor, authenticationProperties);
        log.trace("[Herodotus] |- Bean [Servlet OAuth2 Authorization Server Configurer] Configure.");
        return configurer;
    }
}
