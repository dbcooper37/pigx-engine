package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import com.pigx.engine.core.identity.service.EnhanceUserDetailsService;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusGrantType;
import com.pigx.engine.oauth2.authentication.utils.DPoPProofVerifier;
import com.pigx.engine.oauth2.authentication.utils.OAuth2AuthenticationProviderUtils;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/OAuth2ResourceOwnerPasswordAuthenticationProvider.class */
public class OAuth2ResourceOwnerPasswordAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(OAuth2ResourceOwnerPasswordAuthenticationProvider.class);
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private SessionRegistry sessionRegistry;

    public OAuth2ResourceOwnerPasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, UserDetailsService userDetailsService, OAuth2AuthenticationProperties complianceProperties) {
        super(authorizationService, userDetailsService, complianceProperties);
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.BadCredentialsException */
    @Override // com.pigx.engine.oauth2.authentication.provider.AbstractUserDetailsAuthenticationProvider
    protected void additionalAuthenticationChecks(UserDetails userDetails, Map<String, Object> additionalParameters) throws BadCredentialsException, AuthenticationException {
        String presentedPassword = (String) additionalParameters.get(SystemConstants.PASSWORD);
        if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            log.debug("[Herodotus] |- Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException("Bad credentials");
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.InternalAuthenticationServiceException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.core.userdetails.UsernameNotFoundException */
    @Override // com.pigx.engine.oauth2.authentication.provider.AbstractUserDetailsAuthenticationProvider
    protected UserDetails retrieveUser(Map<String, Object> additionalParameters) throws UsernameNotFoundException, InternalAuthenticationServiceException, AuthenticationException {
        String username = (String) additionalParameters.get(SystemConstants.USERNAME);
        try {
            EnhanceUserDetailsService enhanceUserDetailsService = getUserDetailsService();
            UserDetails userDetails = enhanceUserDetailsService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            }
            return userDetails;
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex2) {
            throw new InternalAuthenticationServiceException(ex2.getMessage(), ex2);
        } catch (UsernameNotFoundException ex3) {
            log.error("[Herodotus] |- User name can not found ：[{}]", username);
            throw ex3;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    public Authentication authenticate(Authentication authentication) throws OAuth2AuthenticationException, AuthenticationException {
        OAuth2ResourceOwnerPasswordAuthenticationToken resourceOwnerPasswordAuthentication = (OAuth2ResourceOwnerPasswordAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(resourceOwnerPasswordAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (!registeredClient.getAuthorizationGrantTypes().contains(HerodotusGrantType.PASSWORD)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorKeys.UNAUTHORIZED_CLIENT);
        }
        Jwt dPoPProof = DPoPProofVerifier.verifyIfAvailable(resourceOwnerPasswordAuthentication);
        if (log.isTraceEnabled()) {
            log.trace("Validated token request parameters");
        }
        Authentication principal = getUsernamePasswordAuthentication(resourceOwnerPasswordAuthentication.getAdditionalParameters(), registeredClient.getId());
        Set<String> authorizedScopes = validateScopes(resourceOwnerPasswordAuthentication.getScopes(), registeredClient);
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient).principalName(principal.getName()).authorizationGrantType(HerodotusGrantType.PASSWORD).authorizedScopes(authorizedScopes).attribute(Principal.class.getName(), principal);
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = (DefaultOAuth2TokenContext.Builder) DefaultOAuth2TokenContext.builder().registeredClient(registeredClient).principal(principal).authorizationServerContext(AuthorizationServerContextHolder.getContext()).authorizedScopes(authorizedScopes).tokenType(OAuth2TokenType.ACCESS_TOKEN).authorizationGrantType(HerodotusGrantType.PASSWORD).authorizationGrant(resourceOwnerPasswordAuthentication);
        if (dPoPProof != null) {
            tokenContextBuilder.put(OAuth2TokenContext.DPOP_PROOF_KEY, dPoPProof);
        }
        OAuth2AccessToken accessToken = createOAuth2AccessToken(tokenContextBuilder, authorizationBuilder, this.tokenGenerator, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
        OAuth2RefreshToken refreshToken = creatOAuth2RefreshToken(tokenContextBuilder, authorizationBuilder, this.tokenGenerator, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2", clientPrincipal, registeredClient);
        OidcIdToken idToken = createOidcIdToken(principal, this.sessionRegistry, tokenContextBuilder, authorizationBuilder, this.tokenGenerator, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2", resourceOwnerPasswordAuthentication.getScopes());
        OAuth2Authorization authorization = authorizationBuilder.build();
        this.authorizationService.save(authorization);
        log.debug("[Herodotus] |- Resource Owner Password returning OAuth2AccessTokenAuthenticationToken.");
        Map<String, Object> additionalParameters = idTokenAdditionalParameters(idToken);
        OAuth2AccessTokenAuthenticationToken accessTokenAuthenticationToken = new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
        return createOAuth2AccessTokenAuthenticationToken(principal, accessTokenAuthenticationToken);
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        Assert.notNull(sessionRegistry, "sessionRegistry cannot be null");
        this.sessionRegistry = sessionRegistry;
    }

    public boolean supports(Class<?> authentication) {
        boolean supports = OAuth2ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authentication);
        log.trace("[Herodotus] |- Resource Owner Password Authentication is supports! [{}]", Boolean.valueOf(supports));
        return supports;
    }
}
