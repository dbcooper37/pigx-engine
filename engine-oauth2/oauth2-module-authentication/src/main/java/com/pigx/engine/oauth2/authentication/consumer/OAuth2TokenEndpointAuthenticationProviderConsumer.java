package com.pigx.engine.oauth2.authentication.consumer;

import com.pigx.engine.core.identity.service.ClientDetailsService;
import com.pigx.engine.oauth2.authentication.provider.OAuth2AuthorizationCodeAuthenticationProvider;
import com.pigx.engine.oauth2.authentication.provider.OAuth2ClientCredentialsAuthenticationProvider;
import com.pigx.engine.oauth2.authentication.utils.OAuth2ConfigurerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.List;
import java.util.function.Consumer;


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

    @Override
    public void accept(List<AuthenticationProvider> authenticationProviders) {

        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);

        // 删除 SAS 默认的 OAuth2AuthorizationCodeAuthenticationProvider
        authenticationProviders.removeIf(authenticationProvider ->
                authenticationProvider instanceof org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationProvider);

        OAuth2AuthorizationCodeAuthenticationProvider authorizationCodeProvider = new OAuth2AuthorizationCodeAuthenticationProvider(authorizationService, tokenGenerator);
        authorizationCodeProvider.setSessionRegistry(sessionRegistry);
        log.debug("[PIGXD] |- Custom OAuth2AuthorizationCodeAuthenticationProvider is in effect!");
        authenticationProviders.add(authorizationCodeProvider);

        // 删除 SAS 默认的 OAuth2ClientCredentialsAuthenticationProvider
        authenticationProviders.removeIf(authenticationProvider ->
                authenticationProvider instanceof org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationProvider);

        OAuth2ClientCredentialsAuthenticationProvider clientCredentialsProvider = new OAuth2ClientCredentialsAuthenticationProvider(authorizationService, tokenGenerator, clientDetailsService);
        log.debug("[PIGXD] |- Custom OAuth2ClientCredentialsAuthenticationProvider is in effect!");
        authenticationProviders.add(clientCredentialsProvider);
    }
}
