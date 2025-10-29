package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.servlet.ServletOAuth2ResourceMatcherConfigurer;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;


public class OAuth2AuthorizeHttpRequestsConfigurerCustomer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    private final ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer;
    private final ServletSecurityAuthorizationManager servletSecurityAuthorizationManager;

    public OAuth2AuthorizeHttpRequestsConfigurerCustomer(ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer, ServletSecurityAuthorizationManager servletSecurityAuthorizationManager) {
        this.servletOAuth2ResourceMatcherConfigurer = servletOAuth2ResourceMatcherConfigurer;
        this.servletSecurityAuthorizationManager = servletSecurityAuthorizationManager;
    }

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurer) {
        configurer
                .requestMatchers(servletOAuth2ResourceMatcherConfigurer.getStaticRequestMatchers()).permitAll()
                .requestMatchers(servletOAuth2ResourceMatcherConfigurer.getPermitAllRequestMatchers()).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().access(servletSecurityAuthorizationManager);
    }
}
