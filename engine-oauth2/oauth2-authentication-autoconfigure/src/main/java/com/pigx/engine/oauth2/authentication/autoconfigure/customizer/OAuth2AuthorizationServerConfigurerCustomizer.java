package com.pigx.engine.oauth2.authentication.autoconfigure.customizer;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.service.ClientDetailsService;
import com.pigx.engine.oauth2.authentication.configurer.OAuth2AuthenticationConfigurerManager;
import com.pigx.engine.oauth2.authentication.consumer.OAuth2TokenEndpointAuthenticationProviderConsumer;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusOidcUserInfoMapper;
import com.pigx.engine.oauth2.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.pigx.engine.oauth2.authentication.provider.OAuth2SocialCredentialsAuthenticationConverter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2DeviceCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

import java.util.Arrays;


public class OAuth2AuthorizationServerConfigurerCustomizer implements Customizer<OAuth2AuthorizationServerConfigurer> {

    private final HttpSecurity httpSecurity;
    private final SessionRegistry sessionRegistry;
    private final ClientDetailsService clientDetailsService;
    private final OAuth2AuthenticationConfigurerManager authenticationConfigurerManager;

    public OAuth2AuthorizationServerConfigurerCustomizer(HttpSecurity httpSecurity, SessionRegistry sessionRegistry, ClientDetailsService clientDetailsService, OAuth2AuthenticationConfigurerManager authenticationConfigurerManager) {
        this.httpSecurity = httpSecurity;
        this.sessionRegistry = sessionRegistry;
        this.clientDetailsService = clientDetailsService;
        this.authenticationConfigurerManager = authenticationConfigurerManager;
    }


    @Override
    public void customize(OAuth2AuthorizationServerConfigurer oauth2AuthorizationServerConfigurer) {

        oauth2AuthorizationServerConfigurer
                .clientAuthentication(endpoint -> endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler()))
                .authorizationEndpoint(endpoint -> {
                    endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
                    endpoint.consentPage(SystemConstants.OAUTH2_AUTHORIZATION_CONSENT_URI);
                })
                .deviceAuthorizationEndpoint(endpoint -> {
                    endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
                    endpoint.verificationUri(SystemConstants.OAUTH2_DEVICE_ACTIVATION_URI);
                })
                .deviceVerificationEndpoint(endpoint -> {
                    endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
                    endpoint.consentPage(SystemConstants.OAUTH2_AUTHORIZATION_CONSENT_URI);
                })
                .tokenEndpoint(endpoint -> {
                    AuthenticationConverter authenticationConverter = new DelegatingAuthenticationConverter(
                            Arrays.asList(
                                    new OAuth2AuthorizationCodeAuthenticationConverter(),
                                    new OAuth2RefreshTokenAuthenticationConverter(),
                                    new OAuth2ClientCredentialsAuthenticationConverter(),
                                    new OAuth2DeviceCodeAuthenticationConverter(),
                                    new OAuth2ResourceOwnerPasswordAuthenticationConverter(authenticationConfigurerManager.getHttpCryptoProcessor()),
                                    new OAuth2SocialCredentialsAuthenticationConverter(authenticationConfigurerManager.getHttpCryptoProcessor())
                            ));
                    endpoint.accessTokenRequestConverter(authenticationConverter);
                    endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
                    endpoint.accessTokenResponseHandler(authenticationConfigurerManager.getOAuth2AccessTokenResponseHandler());
                    endpoint.authenticationProviders(new OAuth2TokenEndpointAuthenticationProviderConsumer(httpSecurity, sessionRegistry, clientDetailsService));
                })
                .tokenIntrospectionEndpoint(endpoint -> endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler()))
                .tokenRevocationEndpoint(endpoint -> endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler()))
                .oidc(oidc -> oidc.clientRegistrationEndpoint(endpoint -> {
                            endpoint.errorResponseHandler(authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
                        })
                        .userInfoEndpoint(userInfo -> userInfo
                                .userInfoMapper(new HerodotusOidcUserInfoMapper())));
    }
}
