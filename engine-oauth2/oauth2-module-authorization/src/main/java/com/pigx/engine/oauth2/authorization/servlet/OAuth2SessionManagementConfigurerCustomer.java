package com.pigx.engine.oauth2.authorization.servlet;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.session.SessionRegistry;


public class OAuth2SessionManagementConfigurerCustomer implements Customizer<SessionManagementConfigurer<HttpSecurity>> {

    private final SessionRegistry sessionRegistry;

    public OAuth2SessionManagementConfigurerCustomer(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }


    @Override
    public void customize(SessionManagementConfigurer<HttpSecurity> configurer) {
        configurer.sessionAuthenticationStrategy(new HerodotusSessionAuthenticationStrategy(sessionRegistry));
    }
}
