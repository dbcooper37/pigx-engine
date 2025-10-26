package com.pigx.engine.oauth2.authentication.customizer;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/customizer/HerodotusJwtTokenCustomizer.class */
public class HerodotusJwtTokenCustomizer extends AbstractTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    public void customize(JwtEncodingContext context) {
        OAuth2ClientAuthenticationToken oAuth2ClientAuthenticationToken = null;
        OAuth2ClientAuthenticationToken authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2ClientAuthenticationToken) {
            oAuth2ClientAuthenticationToken = authentication;
        }
        if (ObjectUtils.isNotEmpty(oAuth2ClientAuthenticationToken) && oAuth2ClientAuthenticationToken.isAuthenticated()) {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                Authentication authentication2 = context.getPrincipal();
                if (ObjectUtils.isNotEmpty(authentication2)) {
                    Map<String, Object> attributes = new HashMap<>();
                    appendAll(attributes, authentication2, context.getAuthorizedScopes());
                    JwtClaimsSet.Builder jwtClaimSetBuilder = context.getClaims();
                    jwtClaimSetBuilder.claims(claims -> {
                        claims.putAll(attributes);
                    });
                }
            }
            if ("id_token".equals(context.getTokenType().getValue())) {
                Authentication authentication3 = context.getPrincipal();
                if (ObjectUtils.isNotEmpty(authentication3)) {
                    Map<String, Object> attributes2 = new HashMap<>();
                    appendCommons(attributes2, authentication3, context.getAuthorizedScopes());
                    JwtClaimsSet.Builder jwtClaimSetBuilder2 = context.getClaims();
                    jwtClaimSetBuilder2.claims(claims2 -> {
                        claims2.putAll(attributes2);
                    });
                }
            }
        }
    }
}
