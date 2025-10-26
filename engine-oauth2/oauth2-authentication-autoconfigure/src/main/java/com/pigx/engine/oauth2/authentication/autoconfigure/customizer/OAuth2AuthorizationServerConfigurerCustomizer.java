package com.pigx.engine.oauth2.authentication.autoconfigure.customizer;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.service.ClientDetailsService;
import com.pigx.engine.oauth2.authentication.configurer.OAuth2AuthenticationConfigurerManager;
import com.pigx.engine.oauth2.authentication.consumer.OAuth2TokenEndpointAuthenticationProviderConsumer;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusOidcUserInfoMapper;
import com.pigx.engine.oauth2.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.pigx.engine.oauth2.authentication.provider.OAuth2SocialCredentialsAuthenticationConverter;
import java.util.Arrays;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2DeviceCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

/* JADX WARN: Classes with same name are omitted:
  
 */
/* loaded from: oauth2-authentication-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/autoconfigure/customizer/OAuth2AuthorizationServerConfigurerCustomizer.class */
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

    public void customize(OAuth2AuthorizationServerConfigurer oauth2AuthorizationServerConfigurer) {
        oauth2AuthorizationServerConfigurer.clientAuthentication(endpoint -> {
            endpoint.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
        }).authorizationEndpoint(endpoint2 -> {
            endpoint2.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
            endpoint2.consentPage(SystemConstants.OAUTH2_AUTHORIZATION_CONSENT_URI);
        }).deviceAuthorizationEndpoint(endpoint3 -> {
            endpoint3.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
            endpoint3.verificationUri(SystemConstants.OAUTH2_DEVICE_ACTIVATION_URI);
        }).deviceVerificationEndpoint(endpoint4 -> {
            endpoint4.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
            endpoint4.consentPage(SystemConstants.OAUTH2_AUTHORIZATION_CONSENT_URI);
        }).tokenEndpoint(endpoint5 -> {
            endpoint5.accessTokenRequestConverter(new DelegatingAuthenticationConverter(Arrays.asList(new OAuth2AuthorizationCodeAuthenticationConverter(), new OAuth2RefreshTokenAuthenticationConverter(), new OAuth2ClientCredentialsAuthenticationConverter(), new OAuth2DeviceCodeAuthenticationConverter(), new OAuth2ResourceOwnerPasswordAuthenticationConverter(this.authenticationConfigurerManager.getHttpCryptoProcessor()), new OAuth2SocialCredentialsAuthenticationConverter(this.authenticationConfigurerManager.getHttpCryptoProcessor()))));
            endpoint5.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
            endpoint5.accessTokenResponseHandler(this.authenticationConfigurerManager.getOAuth2AccessTokenResponseHandler());
            endpoint5.authenticationProviders(new OAuth2TokenEndpointAuthenticationProviderConsumer(this.httpSecurity, this.sessionRegistry, this.clientDetailsService));
        }).tokenIntrospectionEndpoint(endpoint6 -> {
            endpoint6.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
        }).tokenRevocationEndpoint(endpoint7 -> {
            endpoint7.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
        }).oidc(oidc -> {
            oidc.clientRegistrationEndpoint(endpoint8 -> {
                endpoint8.errorResponseHandler(this.authenticationConfigurerManager.getOAuth2AuthenticationFailureHandler());
            }).userInfoEndpoint(userInfo -> {
                userInfo.userInfoMapper(new HerodotusOidcUserInfoMapper());
            });
        });
    }
}
