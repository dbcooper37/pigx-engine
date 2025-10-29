package com.pigx.engine.oauth2.authentication.configurer;

import com.pigx.engine.oauth2.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.pigx.engine.oauth2.authentication.provider.OAuth2SocialCredentialsAuthenticationProvider;
import com.pigx.engine.oauth2.authentication.utils.OAuth2ConfigurerUtils;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;


public class OAuth2AuthenticationProviderConfigurer extends AbstractHttpConfigurer<OAuth2AuthenticationProviderConfigurer, HttpSecurity> {

    private final SessionRegistry sessionRegistry;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final OAuth2AuthenticationProperties authenticationProperties;

    public OAuth2AuthenticationProviderConfigurer(SessionRegistry sessionRegistry, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, OAuth2AuthenticationProperties authenticationProperties) {
        this.sessionRegistry = sessionRegistry;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider =
                new OAuth2ResourceOwnerPasswordAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, authenticationProperties);
        resourceOwnerPasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        resourceOwnerPasswordAuthenticationProvider.setSessionRegistry(sessionRegistry);
        httpSecurity.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);

        OAuth2SocialCredentialsAuthenticationProvider socialCredentialsAuthenticationProvider =
                new OAuth2SocialCredentialsAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, authenticationProperties);
        socialCredentialsAuthenticationProvider.setSessionRegistry(sessionRegistry);
        httpSecurity.authenticationProvider(socialCredentialsAuthenticationProvider);
    }
}
