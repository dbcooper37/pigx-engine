package com.pigx.engine.oauth2.authentication.consumer;

import com.pigx.engine.core.identity.service.ClientDetailsService;
import com.pigx.engine.oauth2.authentication.utils.OAuth2ConfigurerUtils;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/consumer/OAuth2TokenEndpointAuthenticationProviderConsumer.class */
public class OAuth2TokenEndpointAuthenticationProviderConsumer implements Consumer<List<AuthenticationProvider>> {
    private static final Logger log = LoggerFactory.getLogger(OAuth2TokenEndpointAuthenticationProviderConsumer.class);
    private final HttpSecurity httpSecurity;
    private final SessionRegistry sessionRegistry;
    private final ClientDetailsService clientDetailsService;

    public OAuth2TokenEndpointAuthenticationProviderConsumer(HttpSecurity httpSecurity, SessionRegistry sessionRegistry, ClientDetailsService clientDetailsService) {
        this.httpSecurity = httpSecurity;
        this.sessionRegistry = sessionRegistry;
        this.clientDetailsService = clientDetailsService;
    }

    @Override // java.util.function.Consumer
    public void accept(List<AuthenticationProvider> authenticationProviders) {
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(this.httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(this.httpSecurity);
        authenticationProviders.removeIf(authenticationProvider -> {
            return authenticationProvider instanceof OAuth2AuthorizationCodeAuthenticationProvider;
        });
        com.pigx.engine.oauth2.authentication.provider.OAuth2AuthorizationCodeAuthenticationProvider authorizationCodeProvider = new com.pigx.engine.oauth2.authentication.provider.OAuth2AuthorizationCodeAuthenticationProvider(authorizationService, tokenGenerator);
        authorizationCodeProvider.setSessionRegistry(this.sessionRegistry);
        log.debug("[Herodotus] |- Custom OAuth2AuthorizationCodeAuthenticationProvider is in effect!");
        authenticationProviders.add(authorizationCodeProvider);
        authenticationProviders.removeIf(authenticationProvider2 -> {
            return authenticationProvider2 instanceof OAuth2ClientCredentialsAuthenticationProvider;
        });
        com.pigx.engine.oauth2.authentication.provider.OAuth2ClientCredentialsAuthenticationProvider clientCredentialsProvider = new com.pigx.engine.oauth2.authentication.provider.OAuth2ClientCredentialsAuthenticationProvider(authorizationService, tokenGenerator, this.clientDetailsService);
        log.debug("[Herodotus] |- Custom OAuth2ClientCredentialsAuthenticationProvider is in effect!");
        authenticationProviders.add(clientCredentialsProvider);
    }
}
