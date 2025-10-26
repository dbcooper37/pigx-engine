package com.pigx.engine.oauth2.authentication.utils;

import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenExchangeActor;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenExchangeCompositeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/utils/DefaultOAuth2TokenCustomizers.class */
final class DefaultOAuth2TokenCustomizers {
    private DefaultOAuth2TokenCustomizers() {
    }

    static OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            context.getClaims().claims(claims -> {
                customize(context, claims);
            });
        };
    }

    static OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer() {
        return context -> {
            context.getClaims().claims(claims -> {
                customize(context, claims);
            });
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    public static void customize(OAuth2TokenContext tokenContext, Map<String, Object> claims) throws OAuth2AuthenticationException {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenContext.getTokenType()) && tokenContext.getAuthorizationGrant() != null) {
            Object principal = tokenContext.getAuthorizationGrant().getPrincipal();
            if (principal instanceof OAuth2ClientAuthenticationToken) {
                OAuth2ClientAuthenticationToken clientAuthentication = (OAuth2ClientAuthenticationToken) principal;
                if ((ClientAuthenticationMethod.TLS_CLIENT_AUTH.equals(clientAuthentication.getClientAuthenticationMethod()) || ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH.equals(clientAuthentication.getClientAuthenticationMethod())) && tokenContext.getRegisteredClient().getTokenSettings().isX509CertificateBoundAccessTokens()) {
                    X509Certificate[] clientCertificateChain = (X509Certificate[]) clientAuthentication.getCredentials();
                    try {
                        Object sha256Thumbprint = computeSHA256Thumbprint(clientCertificateChain[0]);
                        Map<String, Object> x5tClaim = new HashMap<>();
                        x5tClaim.put("x5t#S256", sha256Thumbprint);
                        claims.put("cnf", x5tClaim);
                    } catch (Exception ex) {
                        OAuth2Error error = new OAuth2Error(OAuth2ErrorKeys.SERVER_ERROR, "Failed to compute SHA-256 Thumbprint for client X509Certificate.", (String) null);
                        throw new OAuth2AuthenticationException(error, ex);
                    }
                }
            }
        }
        OAuth2TokenExchangeCompositeAuthenticationToken principal2 = tokenContext.getPrincipal();
        if (principal2 instanceof OAuth2TokenExchangeCompositeAuthenticationToken) {
            OAuth2TokenExchangeCompositeAuthenticationToken compositeAuthenticationToken = principal2;
            Map<String, Object> currentClaims = claims;
            for (OAuth2TokenExchangeActor actor : compositeAuthenticationToken.getActors()) {
                Map<String, Object> actorClaims = actor.getClaims();
                Map<String, Object> actClaim = new LinkedHashMap<>();
                actClaim.put("iss", actorClaims.get("iss"));
                actClaim.put("sub", actorClaims.get("sub"));
                currentClaims.put("act", Collections.unmodifiableMap(actClaim));
                currentClaims = actClaim;
            }
        }
    }

    private static String computeSHA256Thumbprint(X509Certificate x509Certificate) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(x509Certificate.getEncoded());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
