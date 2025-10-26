package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.service.ClientDetailsService;
import com.pigx.engine.oauth2.authentication.utils.OAuth2AuthenticationProviderUtils;
import cn.hutool.v7.core.reflect.FieldUtil;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/OAuth2ClientCredentialsAuthenticationProvider.class */
public class OAuth2ClientCredentialsAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private final Log logger = LogFactory.getLog(getClass());
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final ClientDetailsService clientDetailsService;

    public OAuth2ClientCredentialsAuthenticationProvider(OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, ClientDetailsService clientDetailsService) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.clientDetailsService = clientDetailsService;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    @NotNull
    private static Set<String> getScopes(OAuth2ClientCredentialsAuthenticationToken clientCredentialsAuthentication, RegisteredClient registeredClient) throws OAuth2AuthenticationException {
        Set<String> authorizedScopes = Collections.emptySet();
        if (!CollectionUtils.isEmpty(clientCredentialsAuthentication.getScopes())) {
            for (String requestedScope : clientCredentialsAuthentication.getScopes()) {
                if (!registeredClient.getScopes().contains(requestedScope)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_SCOPE);
                }
            }
            authorizedScopes = new LinkedHashSet(clientCredentialsAuthentication.getScopes());
        }
        return authorizedScopes;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    public Authentication authenticate(Authentication authentication) throws OAuth2AuthenticationException, AuthenticationException {
        OAuth2ClientCredentialsAuthenticationToken clientCredentialsAuthentication = (OAuth2ClientCredentialsAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(clientCredentialsAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved registered client");
        }
        if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorKeys.UNAUTHORIZED_CLIENT);
        }
        Set<String> authorizedScopes = getScopes(clientCredentialsAuthentication, registeredClient);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Validated token request parameters");
        }
        Set<HerodotusGrantedAuthority> authorities = this.clientDetailsService.findAuthoritiesById(registeredClient.getClientId());
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(authorities)) {
            FieldUtil.setFieldValue(clientPrincipal, SystemConstants.AUTHORITIES, authorities);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("[Herodotus] |- Assign authorities to OAuth2ClientAuthenticationToken.");
            }
        }
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient).principalName(clientPrincipal.getName()).authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).authorizedScopes(authorizedScopes);
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = (DefaultOAuth2TokenContext.Builder) DefaultOAuth2TokenContext.builder().registeredClient(registeredClient).principal(clientPrincipal).authorizationServerContext(AuthorizationServerContextHolder.getContext()).authorizedScopes(authorizedScopes).tokenType(OAuth2TokenType.ACCESS_TOKEN).authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).authorizationGrant(clientCredentialsAuthentication);
        OAuth2AccessToken accessToken = createOAuth2AccessToken(tokenContextBuilder, authorizationBuilder, this.tokenGenerator, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
        OAuth2Authorization authorization = authorizationBuilder.build();
        this.authorizationService.save(authorization);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Saved authorization");
            this.logger.trace("Authenticated token request");
        }
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
    }

    public boolean supports(Class<?> authentication) {
        return OAuth2ClientCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
