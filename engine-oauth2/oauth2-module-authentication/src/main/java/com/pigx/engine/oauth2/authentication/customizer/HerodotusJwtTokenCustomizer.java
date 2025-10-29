package com.pigx.engine.oauth2.authentication.customizer;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.HashMap;
import java.util.Map;


public class HerodotusJwtTokenCustomizer extends AbstractTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {

        AbstractAuthenticationToken token = null;
        Authentication clientAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (clientAuthentication instanceof OAuth2ClientAuthenticationToken) {
            token = (OAuth2ClientAuthenticationToken) clientAuthentication;
        }

        if (ObjectUtils.isNotEmpty(token) && token.isAuthenticated()) {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                Authentication authentication = context.getPrincipal();
                if (ObjectUtils.isNotEmpty(authentication)) {
                    Map<String, Object> attributes = new HashMap<>();
                    appendAll(attributes, authentication, context.getAuthorizedScopes());
                    JwtClaimsSet.Builder jwtClaimSetBuilder = context.getClaims();
                    jwtClaimSetBuilder.claims(claims -> claims.putAll(attributes));
                }
            }

            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                Authentication authentication = context.getPrincipal();
                if (ObjectUtils.isNotEmpty(authentication)) {
                    Map<String, Object> attributes = new HashMap<>();
                    appendCommons(attributes, authentication, context.getAuthorizedScopes());
                    JwtClaimsSet.Builder jwtClaimSetBuilder = context.getClaims();
                    jwtClaimSetBuilder.claims(claims -> claims.putAll(attributes));
                }
            }
        }
    }
}
