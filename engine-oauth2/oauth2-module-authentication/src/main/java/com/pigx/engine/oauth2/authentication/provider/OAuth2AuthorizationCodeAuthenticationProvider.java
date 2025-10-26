package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import com.pigx.engine.oauth2.authentication.utils.DPoPProofVerifier;
import com.pigx.engine.oauth2.authentication.utils.OAuth2AuthenticationProviderUtils;
import java.security.Principal;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/OAuth2AuthorizationCodeAuthenticationProvider.class */
public final class OAuth2AuthorizationCodeAuthenticationProvider extends AbstractAuthenticationProvider {
    private final Log logger = LogFactory.getLog(getClass());
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private static final OAuth2TokenType AUTHORIZATION_CODE_TOKEN_TYPE = new OAuth2TokenType(SystemConstants.CODE);
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private SessionRegistry sessionRegistry;

    public OAuth2AuthorizationCodeAuthenticationProvider(OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    public Authentication authenticate(Authentication authentication) throws OAuth2AuthenticationException, AuthenticationException {
        OAuth2Authorization.Token<? extends OAuth2Token> accessToken;
        OAuth2AuthorizationCodeAuthenticationToken authorizationCodeAuthentication = (OAuth2AuthorizationCodeAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(authorizationCodeAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved registered client");
        }
        OAuth2Authorization authorization = this.authorizationService.findByToken(authorizationCodeAuthentication.getCode(), AUTHORIZATION_CODE_TOKEN_TYPE);
        if (authorization == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_GRANT);
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved authorization with authorization code");
        }
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        OAuth2AuthorizationRequest authorizationRequest = (OAuth2AuthorizationRequest) authorization.getAttribute(OAuth2AuthorizationRequest.class.getName());
        if (!registeredClient.getClientId().equals(authorizationRequest.getClientId())) {
            if (!authorizationCode.isInvalidated()) {
                this.authorizationService.save(OAuth2Authorization.from(authorization).invalidate(authorizationCode.getToken()).build());
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn(LogMessage.format("Invalidated authorization code used by registered client '%s'", registeredClient.getId()));
                }
            }
            throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_GRANT);
        }
        if (StringUtils.hasText(authorizationRequest.getRedirectUri()) && !authorizationRequest.getRedirectUri().equals(authorizationCodeAuthentication.getRedirectUri())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_GRANT);
        }
        if (!authorizationCode.isActive()) {
            if (authorizationCode.isInvalidated()) {
                if (authorization.getRefreshToken() != null) {
                    accessToken = authorization.getRefreshToken();
                } else {
                    accessToken = authorization.getAccessToken();
                }
                OAuth2Authorization.Token<? extends OAuth2Token> token = accessToken;
                if (token != null) {
                    this.authorizationService.save(OAuth2Authorization.from(authorization).invalidate(token.getToken()).build());
                    if (this.logger.isWarnEnabled()) {
                        this.logger.warn(LogMessage.format("Invalidated authorization token(s) previously issued to registered client '%s'", registeredClient.getId()));
                    }
                }
            }
            throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_GRANT);
        }
        Jwt dPoPProof = DPoPProofVerifier.verifyIfAvailable(authorizationCodeAuthentication);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Validated token request parameters");
        }
        Authentication principal = (Authentication) authorization.getAttribute(Principal.class.getName());
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = (DefaultOAuth2TokenContext.Builder) DefaultOAuth2TokenContext.builder().registeredClient(registeredClient).principal(principal).authorizationServerContext(AuthorizationServerContextHolder.getContext()).authorization(authorization).authorizedScopes(authorization.getAuthorizedScopes()).authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).authorizationGrant(authorizationCodeAuthentication);
        if (dPoPProof != null) {
            tokenContextBuilder.put(OAuth2TokenContext.DPOP_PROOF_KEY, dPoPProof);
        }
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization);
        OAuth2AccessToken accessToken2 = createOAuth2AccessToken(tokenContextBuilder, authorizationBuilder, this.tokenGenerator, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
        OAuth2RefreshToken refreshToken = creatOAuth2RefreshToken(tokenContextBuilder, authorizationBuilder, this.tokenGenerator, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2", clientPrincipal, registeredClient);
        OidcIdToken idToken = createOidcIdToken(principal, this.sessionRegistry, tokenContextBuilder, authorizationBuilder, this.tokenGenerator, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2", authorizationRequest.getScopes());
        OAuth2Authorization authorization2 = authorizationBuilder.build();
        authorizationBuilder.invalidate(authorizationCode.getToken());
        this.authorizationService.save(authorization2);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Saved authorization");
        }
        Map<String, Object> additionalParameters = idTokenAdditionalParameters(idToken);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Authenticated token request");
        }
        OAuth2AccessTokenAuthenticationToken accessTokenAuthenticationToken = new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken2, refreshToken, additionalParameters);
        return createOAuth2AccessTokenAuthenticationToken(principal, accessTokenAuthenticationToken);
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        Assert.notNull(sessionRegistry, "sessionRegistry cannot be null");
        this.sessionRegistry = sessionRegistry;
    }

    public boolean supports(Class<?> authentication) {
        return OAuth2AuthorizationCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
