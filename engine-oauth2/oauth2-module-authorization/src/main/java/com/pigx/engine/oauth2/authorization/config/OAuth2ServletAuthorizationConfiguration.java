package com.pigx.engine.oauth2.authorization.config;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.servlet.ServletOAuth2ResourceMatcherConfigurer;
import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeStorage;
import com.pigx.engine.oauth2.authorization.properties.FeignSecurityProperties;
import com.pigx.engine.oauth2.authorization.servlet.OAuth2SessionManagementConfigurerCustomer;
import com.pigx.engine.oauth2.authorization.servlet.ServletOAuth2AuthorizationConfigurerManager;
import com.pigx.engine.oauth2.authorization.servlet.ServletSecurityAuthorizationManager;
import com.pigx.engine.oauth2.authorization.validator.FeignTokenValidator;
import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;


@Configuration(proxyBeanMethods = false)
@ConditionalOnServletApplication
@EnableMethodSecurity(proxyTargetClass = true, securedEnabled = true, jsr250Enabled = true)
@EnableConfigurationProperties(FeignSecurityProperties.class)
@Import({
        OAuth2ServletSessionConfiguration.class,
})
public class OAuth2ServletAuthorizationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ServletAuthorizationConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [OAuth2 Servlet Authorization] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityAttributeStorage securityMetadataSourceStorage() {
        return new SecurityAttributeStorage();
    }

    @Bean
    @ConditionalOnMissingBean
    public FeignTokenValidator feignTokenValidator(FeignSecurityProperties feignSecurityProperties) {
        FeignTokenValidator validator = new FeignTokenValidator(
                feignSecurityProperties.getSecretKey(),
                feignSecurityProperties.isEnabled(),
                feignSecurityProperties.getTokenValiditySeconds());
        log.trace("[PIGXD] |- Bean [Feign Token Validator] Configure. Enabled: {}", feignSecurityProperties.isEnabled());
        return validator;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletSecurityAuthorizationManager servletSecurityAuthorizationManager(
            SecurityAttributeStorage securityAttributeStorage,
            ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer,
            FeignTokenValidator feignTokenValidator) {
        ServletSecurityAuthorizationManager manager = new ServletSecurityAuthorizationManager(
                securityAttributeStorage,
                servletOAuth2ResourceMatcherConfigurer,
                feignTokenValidator);
        log.trace("[PIGXD] |- Bean [Servlet Security Authorization Manager] Configure.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityAttributeAnalyzer securityAttributeAnalyzer(SecurityAttributeStorage securityAttributeStorage, ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer) {
        SecurityAttributeAnalyzer analyzer = new SecurityAttributeAnalyzer(securityAttributeStorage, servletOAuth2ResourceMatcherConfigurer);
        log.trace("[PIGXD] |- Bean [Security Attribute Analyzer] Configure.");
        return analyzer;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletOAuth2AuthorizationConfigurerManager servletOAuth2AuthorizationFacadeConfigurer(
            ThymeleafTemplateHandler thymeleafTemplateHandler,
            OAuth2AuthorizationProperties oauth2AuthorizationProperties,
            JwtDecoder jwtDecoder,
            OpaqueTokenIntrospector opaqueTokenIntrospector,
            OAuth2SessionManagementConfigurerCustomer sessionManagementConfigurerCustomer,
            ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer,
            ServletSecurityAuthorizationManager servletSecurityAuthorizationManager) {
        ServletOAuth2AuthorizationConfigurerManager configurer = new ServletOAuth2AuthorizationConfigurerManager(
                thymeleafTemplateHandler,
                oauth2AuthorizationProperties,
                jwtDecoder,
                opaqueTokenIntrospector,
                sessionManagementConfigurerCustomer,
                servletOAuth2ResourceMatcherConfigurer,
                servletSecurityAuthorizationManager);
        log.trace("[PIGXD] |- Bean [Servlet OAuth2 Resource Server Configurer] Configure.");
        return configurer;
    }
}
