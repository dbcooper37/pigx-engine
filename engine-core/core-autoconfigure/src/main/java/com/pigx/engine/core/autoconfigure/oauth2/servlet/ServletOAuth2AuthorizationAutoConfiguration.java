package com.pigx.engine.core.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationAutoConfiguration;
import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.constant.ConditionalOnTokenFormat;
import com.pigx.engine.core.autoconfigure.oauth2.constant.TokenFormat;
import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import com.pigx.engine.core.identity.oauth2.BearerTokenResolver;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@AutoConfiguration(after = OAuth2AuthorizationAutoConfiguration.class)
@ConditionalOnServletApplication
@ConditionalOnClass(BearerTokenAuthenticationToken.class)
@Import({
        ServletRestControllerAdvice.class
})
public class ServletOAuth2AuthorizationAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ServletOAuth2AuthorizationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Servlet OAuth2 Authorization] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletOAuth2ResourceMatcherConfigurer servletSecurityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider) {
        ServletOAuth2ResourceMatcherConfigurer configurer = new ServletOAuth2ResourceMatcherConfigurer(authorizationProperties, resourceUrlProvider);
        log.trace("[PIGXD] |- Bean [Servlet Security Matcher Configurer] Configure.");
        return configurer;
    }

    @Bean
    @ConditionalOnClass(AuditorAware.class)
    @ConditionalOnMissingBean
    public AuditorAware<String> auditorAware() {
        ServletSecurityAuditorAware aware = new ServletSecurityAuditorAware();
        log.trace("[PIGXD] |- Bean [Servlet Security Auditor Aware] Configure.");
        return aware;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(OpaqueTokenIntrospector.class)
    @ConditionalOnTokenFormat(TokenFormat.OPAQUE)
    static class OAuth2OpaqueTokenConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public OpaqueTokenIntrospector herodotusServletOpaqueTokenIntrospector(OAuth2ResourceServerProperties resourceServerProperties) {
            HerodotusServletOpaqueTokenIntrospector introspector = new HerodotusServletOpaqueTokenIntrospector(resourceServerProperties);
            log.trace("[PIGXD] |- Bean [Herodotus Servlet Opaque Token Introspector] Configure.");
            return introspector;
        }

        @Bean
        @ConditionalOnMissingBean
        public BearerTokenResolver opaqueBearerTokenResolver(OpaqueTokenIntrospector OpaqueTokenIntrospector) {
            HerodotusServletOpaqueTokenResolver resolver = new HerodotusServletOpaqueTokenResolver(OpaqueTokenIntrospector);
            log.trace("[PIGXD] |- Bean [Herodotus Servlet Opaque Token Resolver] Configure.");
            return resolver;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(JwtDecoder.class)
    @ConditionalOnTokenFormat(TokenFormat.JWT)
    static class OAuth2JwtTokenConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public BearerTokenResolver jwtBearerTokenResolver(JwtDecoder jwtDecoder) {
            HerodotusServletJwtTokenResolver resolver = new HerodotusServletJwtTokenResolver(jwtDecoder);
            log.trace("[PIGXD] |- Bean [Herodotus Servlet JWT Token Resolver] Configure.");
            return resolver;
        }
    }
}
