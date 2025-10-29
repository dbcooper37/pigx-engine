package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.constant.TokenFormat;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;


public class OAuth2ResourceServerConfigurerCustomer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

    private final JwtDecoder jwtDecoder;
    private final OAuth2AuthorizationProperties authorizationProperties;
    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    public OAuth2ResourceServerConfigurerCustomer(OAuth2AuthorizationProperties authorizationProperties, JwtDecoder jwtDecoder, OpaqueTokenIntrospector opaqueTokenIntrospector) {
        this.jwtDecoder = jwtDecoder;
        this.authorizationProperties = authorizationProperties;
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
    }

    private boolean isRemoteValidate() {
        return this.authorizationProperties.getTokenFormat() == TokenFormat.OPAQUE;
    }

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        if (isRemoteValidate()) {
            configurer
                    .opaqueToken(opaque -> opaque.introspector(opaqueTokenIntrospector));
        } else {
            configurer
                    .jwt(jwt -> jwt.decoder(this.jwtDecoder).jwtAuthenticationConverter(new HerodotusJwtAuthenticationConverter()))
                    .bearerTokenResolver(new DefaultBearerTokenResolver());
        }
    }
}
