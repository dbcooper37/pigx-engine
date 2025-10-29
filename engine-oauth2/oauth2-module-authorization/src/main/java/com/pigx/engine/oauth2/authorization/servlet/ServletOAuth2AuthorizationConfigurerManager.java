package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.servlet.ServletOAuth2ResourceMatcherConfigurer;
import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;


public class ServletOAuth2AuthorizationConfigurerManager {

    private final OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer;
    private final OAuth2SessionManagementConfigurerCustomer oauth2SessionManagementConfigurerCustomer;
    private final OAuth2AuthorizeHttpRequestsConfigurerCustomer oauth2AuthorizeHttpRequestsConfigurerCustomer;
    private final OAuth2ExceptionHandlingConfigurerCustomizer oauth2ExceptionHandlingConfigurerCustomizer;

    public ServletOAuth2AuthorizationConfigurerManager(
            ThymeleafTemplateHandler thymeleafTemplateHandler,
            OAuth2AuthorizationProperties oauth2AuthorizationProperties,
            JwtDecoder jwtDecoder,
            OpaqueTokenIntrospector opaqueTokenIntrospector,
            OAuth2SessionManagementConfigurerCustomer oauth2SessionManagementConfigurerCustomer,
            ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer,
            ServletSecurityAuthorizationManager servletSecurityAuthorizationManager) {
        this.oauth2ResourceServerConfigurerCustomer = new OAuth2ResourceServerConfigurerCustomer(oauth2AuthorizationProperties, jwtDecoder, opaqueTokenIntrospector);
        this.oauth2SessionManagementConfigurerCustomer = oauth2SessionManagementConfigurerCustomer;
        this.oauth2AuthorizeHttpRequestsConfigurerCustomer = new OAuth2AuthorizeHttpRequestsConfigurerCustomer(servletOAuth2ResourceMatcherConfigurer, servletSecurityAuthorizationManager);
        this.oauth2ExceptionHandlingConfigurerCustomizer = new OAuth2ExceptionHandlingConfigurerCustomizer(thymeleafTemplateHandler);
    }

    public OAuth2ResourceServerConfigurerCustomer getOAuth2ResourceServerConfigurerCustomer() {
        return oauth2ResourceServerConfigurerCustomer;
    }

    public OAuth2SessionManagementConfigurerCustomer getOAuth2SessionManagementConfigurerCustomer() {
        return oauth2SessionManagementConfigurerCustomer;
    }

    public OAuth2AuthorizeHttpRequestsConfigurerCustomer getOAuth2AuthorizeHttpRequestsConfigurerCustomer() {
        return oauth2AuthorizeHttpRequestsConfigurerCustomer;
    }

    public OAuth2ExceptionHandlingConfigurerCustomizer getOAuth2ExceptionHandlingConfigurerCustomizer() {
        return oauth2ExceptionHandlingConfigurerCustomizer;
    }
}
