package com.pigx.engine.oauth2.authentication.utils;

import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.util.CollectionUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/utils/OAuth2AuthenticationProviderUtils.class */
public class OAuth2AuthenticationProviderUtils {
    private OAuth2AuthenticationProviderUtils() {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    public static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) throws OAuth2AuthenticationException {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_CLIENT);
    }

    public static <T extends OAuth2Token> OAuth2AccessToken accessToken(OAuth2Authorization.Builder builder, T token, OAuth2TokenContext accessTokenContext) {
        OAuth2AccessToken.TokenType tokenType = OAuth2AccessToken.TokenType.BEARER;
        if (token instanceof ClaimAccessor) {
            ClaimAccessor claimAccessor = (ClaimAccessor) token;
            Map<String, Object> cnfClaims = claimAccessor.getClaimAsMap("cnf");
            if (!CollectionUtils.isEmpty(cnfClaims) && cnfClaims.containsKey("jkt")) {
                tokenType = OAuth2AccessToken.TokenType.DPOP;
            }
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, token.getTokenValue(), token.getIssuedAt(), token.getExpiresAt(), accessTokenContext.getAuthorizedScopes());
        OAuth2TokenFormat accessTokenFormat = accessTokenContext.getRegisteredClient().getTokenSettings().getAccessTokenFormat();
        builder.token(accessToken, metadata -> {
            if (token instanceof ClaimAccessor) {
                ClaimAccessor claimAccessor2 = (ClaimAccessor) token;
                metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, claimAccessor2.getClaims());
            }
            metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, false);
            metadata.put(OAuth2TokenFormat.class.getName(), accessTokenFormat.getValue());
        });
        return accessToken;
    }
}
