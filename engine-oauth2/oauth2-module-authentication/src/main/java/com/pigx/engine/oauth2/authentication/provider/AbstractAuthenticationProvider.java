package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.oauth2.core.utils.PrincipalUtils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/AbstractAuthenticationProvider.class */
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {
    private final Log logger = LogFactory.getLog(getClass());
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType("id_token");

    private static String createHash(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(value.getBytes(StandardCharsets.US_ASCII));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    protected OAuth2AccessToken createOAuth2AccessToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, String errorUri) throws OAuth2AuthenticationException {
        DefaultOAuth2TokenContext defaultOAuth2TokenContextBuild = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(defaultOAuth2TokenContextBuild);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorKeys.SERVER_ERROR, "The token generator failed to generate the access token.", errorUri);
            throw new OAuth2AuthenticationException(error);
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Generated access token");
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(), generatedAccessToken.getExpiresAt(), defaultOAuth2TokenContextBuild.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, metadata -> {
                metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims());
            });
        } else {
            authorizationBuilder.accessToken(accessToken);
        }
        return accessToken;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    protected OAuth2RefreshToken creatOAuth2RefreshToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, String errorUri, OAuth2ClientAuthenticationToken clientPrincipal, RegisteredClient registeredClient) throws OAuth2AuthenticationException {
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            OAuth2Token generatedRefreshToken = tokenGenerator.generate(tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build());
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorKeys.SERVER_ERROR, "The token generator failed to generate the refresh token.", errorUri);
                throw new OAuth2AuthenticationException(error);
            }
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated refresh token");
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }
        return refreshToken;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    protected OidcIdToken createOidcIdToken(Authentication principal, SessionRegistry sessionRegistry, DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, String errorUri, Set<String> requestedScopes) throws OAuth2AuthenticationException {
        OAuth2Token oidcIdToken;
        if (requestedScopes.contains(SystemConstants.SCOPE_OPENID)) {
            SessionInformation sessionInformation = getSessionInformation(principal, sessionRegistry);
            if (sessionInformation != null) {
                try {
                    tokenContextBuilder.put(SessionInformation.class, new SessionInformation(sessionInformation.getPrincipal(), createHash(sessionInformation.getSessionId()), sessionInformation.getLastRequest()));
                } catch (NoSuchAlgorithmException e) {
                    OAuth2Error error = new OAuth2Error(OAuth2ErrorKeys.SERVER_ERROR, "Failed to compute hash for Session ID.", errorUri);
                    throw new OAuth2AuthenticationException(error);
                }
            }
            Jwt jwtGenerate = tokenGenerator.generate(tokenContextBuilder.tokenType(ID_TOKEN_TOKEN_TYPE).authorization(authorizationBuilder.build()).build());
            if (!(jwtGenerate instanceof Jwt)) {
                OAuth2Error error2 = new OAuth2Error(OAuth2ErrorKeys.SERVER_ERROR, "The token generator failed to generate the ID token.", errorUri);
                throw new OAuth2AuthenticationException(error2);
            }
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated id token");
            }
            oidcIdToken = new OidcIdToken(jwtGenerate.getTokenValue(), jwtGenerate.getIssuedAt(), jwtGenerate.getExpiresAt(), jwtGenerate.getClaims());
            authorizationBuilder.token(oidcIdToken, metadata -> {
                metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, oidcIdToken.getClaims());
            });
        } else {
            oidcIdToken = null;
        }
        return oidcIdToken;
    }

    private SessionInformation getSessionInformation(Authentication principal, SessionRegistry sessionRegistry) {
        SessionInformation sessionInformation = null;
        if (sessionRegistry != null) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal.getPrincipal(), false);
            if (!CollectionUtils.isEmpty(sessions)) {
                sessionInformation = sessions.get(0);
                if (sessions.size() > 1) {
                    List<SessionInformation> sessions2 = new ArrayList<>(sessions);
                    sessions2.sort(Comparator.comparing((v0) -> {
                        return v0.getLastRequest();
                    }));
                    sessionInformation = sessions2.get(sessions2.size() - 1);
                }
            }
        }
        return sessionInformation;
    }

    protected Map<String, Object> idTokenAdditionalParameters(OidcIdToken idToken) {
        Map<String, Object> additionalParameters = Collections.emptyMap();
        if (idToken != null) {
            additionalParameters = new HashMap();
            additionalParameters.put("id_token", idToken.getTokenValue());
        }
        return additionalParameters;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    protected Set<String> validateScopes(Set<String> requestedScopes, RegisteredClient registeredClient) throws OAuth2AuthenticationException {
        Set<String> authorizedScopes = Collections.emptySet();
        if (!CollectionUtils.isEmpty(requestedScopes)) {
            for (String requestedScope : requestedScopes) {
                if (!registeredClient.getScopes().contains(requestedScope)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_SCOPE);
                }
            }
            authorizedScopes = new LinkedHashSet(requestedScopes);
        }
        return authorizedScopes;
    }

    protected OAuth2AccessTokenAuthenticationToken createOAuth2AccessTokenAuthenticationToken(Authentication source, OAuth2AccessTokenAuthenticationToken destination) {
        if (source instanceof UsernamePasswordAuthenticationToken) {
            Object principal = source.getPrincipal();
            if (principal instanceof HerodotusUser) {
                HerodotusUser user = (HerodotusUser) principal;
                destination.setDetails(PrincipalUtils.toPrincipalDetails(user));
            }
        }
        return destination;
    }
}
