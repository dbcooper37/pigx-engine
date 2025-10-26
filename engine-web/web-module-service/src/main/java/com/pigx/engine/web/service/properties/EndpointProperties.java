package com.pigx.engine.web.service.properties;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_ENDPOINT)
/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/EndpointProperties.class */
public class EndpointProperties {
    private String uaaServiceName;
    private String upmsServiceName;
    private String messageServiceName;
    private String ossServiceName;
    private String gatewayServiceUri;
    private String uaaServiceUri;
    private String upmsServiceUri;
    private String messageServiceUri;
    private String ossServiceUri;
    private String authorizationUri;
    private String pushedAuthorizationRequestUri;
    private String accessTokenUri;
    private String jwkSetUri;
    private String tokenRevocationUri;
    private String tokenIntrospectionUri;
    private String deviceAuthorizationUri;
    private String deviceVerificationUri;
    private String oidcClientRegistrationUri;
    private String oidcLogoutUri;
    private String oidcUserInfoUri;
    private String issuerUri;
    private String authorizationEndpoint = SystemConstants.OAUTH2_AUTHORIZATION_ENDPOINT;
    private String pushedAuthorizationRequestEndpoint = SystemConstants.OAUTH2_PUSHED_AUTHORIZATION_REQUEST_ENDPOINT;
    private String accessTokenEndpoint = SystemConstants.OAUTH2_TOKEN_ENDPOINT;
    private String jwkSetEndpoint = SystemConstants.OAUTH2_JWK_SET_ENDPOINT;
    private String tokenRevocationEndpoint = SystemConstants.OAUTH2_TOKEN_REVOCATION_ENDPOINT;
    private String tokenIntrospectionEndpoint = SystemConstants.OAUTH2_TOKEN_INTROSPECTION_ENDPOINT;
    private String deviceAuthorizationEndpoint = SystemConstants.OAUTH2_DEVICE_AUTHORIZATION_ENDPOINT;
    private String deviceVerificationEndpoint = SystemConstants.OAUTH2_DEVICE_VERIFICATION_ENDPOINT;
    private String oidcClientRegistrationEndpoint = SystemConstants.OIDC_CLIENT_REGISTRATION_ENDPOINT;
    private String oidcLogoutEndpoint = SystemConstants.OIDC_LOGOUT_ENDPOINT;
    private String oidcUserInfoEndpoint = SystemConstants.OIDC_USER_INFO_ENDPOINT;

    public String getUaaServiceName() {
        return this.uaaServiceName;
    }

    public void setUaaServiceName(String uaaServiceName) {
        this.uaaServiceName = uaaServiceName;
    }

    public String getUpmsServiceName() {
        return this.upmsServiceName;
    }

    public void setUpmsServiceName(String upmsServiceName) {
        this.upmsServiceName = upmsServiceName;
    }

    public String getMessageServiceName() {
        return this.messageServiceName;
    }

    public void setMessageServiceName(String messageServiceName) {
        this.messageServiceName = messageServiceName;
    }

    public String getOssServiceName() {
        return this.ossServiceName;
    }

    public void setOssServiceName(String ossServiceName) {
        this.ossServiceName = ossServiceName;
    }

    public String getGatewayServiceUri() {
        return this.gatewayServiceUri;
    }

    public void setGatewayServiceUri(String gatewayServiceUri) {
        this.gatewayServiceUri = gatewayServiceUri;
    }

    public String getUaaServiceUri() {
        return WellFormedUtils.serviceUri(this.uaaServiceUri, getUaaServiceName(), getGatewayServiceUri(), "UAA");
    }

    public void setUaaServiceUri(String uaaServiceUri) {
        this.uaaServiceUri = uaaServiceUri;
    }

    public String getUpmsServiceUri() {
        return WellFormedUtils.serviceUri(this.upmsServiceUri, getUpmsServiceName(), getGatewayServiceUri(), "UPMS");
    }

    public void setUpmsServiceUri(String upmsServiceUri) {
        this.upmsServiceUri = upmsServiceUri;
    }

    public String getMessageServiceUri() {
        return WellFormedUtils.serviceUri(this.messageServiceUri, getMessageServiceName(), getGatewayServiceUri(), "MESSAGE");
    }

    public void setMessageServiceUri(String messageServiceUri) {
        this.messageServiceUri = messageServiceUri;
    }

    public String getOssServiceUri() {
        return WellFormedUtils.serviceUri(this.ossServiceUri, getOssServiceName(), getGatewayServiceUri(), "OSS");
    }

    public void setOssServiceUri(String ossServiceUri) {
        this.ossServiceUri = ossServiceUri;
    }

    public String getAuthorizationUri() {
        return WellFormedUtils.sasUri(this.authorizationUri, getAuthorizationEndpoint(), getIssuerUri());
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getPushedAuthorizationRequestUri() {
        return WellFormedUtils.sasUri(this.pushedAuthorizationRequestUri, getPushedAuthorizationRequestEndpoint(), getIssuerUri());
    }

    public void setPushedAuthorizationRequestUri(String pushedAuthorizationRequestUri) {
        this.pushedAuthorizationRequestUri = pushedAuthorizationRequestUri;
    }

    public String getAccessTokenUri() {
        return WellFormedUtils.sasUri(this.accessTokenUri, getAccessTokenEndpoint(), getIssuerUri());
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getJwkSetUri() {
        return WellFormedUtils.sasUri(this.jwkSetUri, getJwkSetEndpoint(), getIssuerUri());
    }

    public void setJwkSetUri(String jwkSetUri) {
        this.jwkSetUri = jwkSetUri;
    }

    public String getTokenRevocationUri() {
        return WellFormedUtils.sasUri(this.tokenRevocationUri, getTokenRevocationEndpoint(), getIssuerUri());
    }

    public void setTokenRevocationUri(String tokenRevocationUri) {
        this.tokenRevocationUri = tokenRevocationUri;
    }

    public String getTokenIntrospectionUri() {
        return WellFormedUtils.sasUri(this.tokenIntrospectionUri, getTokenIntrospectionEndpoint(), getIssuerUri());
    }

    public void setTokenIntrospectionUri(String tokenIntrospectionUri) {
        this.tokenIntrospectionUri = tokenIntrospectionUri;
    }

    public String getDeviceAuthorizationUri() {
        return WellFormedUtils.sasUri(this.deviceAuthorizationUri, getDeviceAuthorizationEndpoint(), getIssuerUri());
    }

    public void setDeviceAuthorizationUri(String deviceAuthorizationUri) {
        this.deviceAuthorizationUri = deviceAuthorizationUri;
    }

    public String getDeviceVerificationUri() {
        return WellFormedUtils.sasUri(this.deviceVerificationUri, getDeviceVerificationEndpoint(), getIssuerUri());
    }

    public void setDeviceVerificationUri(String deviceVerificationUri) {
        this.deviceVerificationUri = deviceVerificationUri;
    }

    public String getOidcClientRegistrationUri() {
        return WellFormedUtils.sasUri(this.oidcClientRegistrationUri, getOidcClientRegistrationEndpoint(), getIssuerUri());
    }

    public void setOidcClientRegistrationUri(String oidcClientRegistrationUri) {
        this.oidcClientRegistrationUri = oidcClientRegistrationUri;
    }

    public String getOidcLogoutUri() {
        return WellFormedUtils.sasUri(this.oidcLogoutUri, getOidcLogoutEndpoint(), getIssuerUri());
    }

    public void setOidcLogoutUri(String oidcLogoutUri) {
        this.oidcLogoutUri = oidcLogoutUri;
    }

    public String getOidcUserInfoUri() {
        return WellFormedUtils.sasUri(this.oidcUserInfoUri, getOidcUserInfoEndpoint(), getIssuerUri());
    }

    public void setOidcUserInfoUri(String oidcUserInfoUri) {
        this.oidcUserInfoUri = oidcUserInfoUri;
    }

    public String getIssuerUri() {
        return this.issuerUri;
    }

    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    public String getAuthorizationEndpoint() {
        return this.authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    public String getPushedAuthorizationRequestEndpoint() {
        return this.pushedAuthorizationRequestEndpoint;
    }

    public void setPushedAuthorizationRequestEndpoint(String pushedAuthorizationRequestEndpoint) {
        this.pushedAuthorizationRequestEndpoint = pushedAuthorizationRequestEndpoint;
    }

    public String getAccessTokenEndpoint() {
        return this.accessTokenEndpoint;
    }

    public void setAccessTokenEndpoint(String accessTokenEndpoint) {
        this.accessTokenEndpoint = accessTokenEndpoint;
    }

    public String getJwkSetEndpoint() {
        return this.jwkSetEndpoint;
    }

    public void setJwkSetEndpoint(String jwkSetEndpoint) {
        this.jwkSetEndpoint = jwkSetEndpoint;
    }

    public String getTokenRevocationEndpoint() {
        return this.tokenRevocationEndpoint;
    }

    public void setTokenRevocationEndpoint(String tokenRevocationEndpoint) {
        this.tokenRevocationEndpoint = tokenRevocationEndpoint;
    }

    public String getTokenIntrospectionEndpoint() {
        return this.tokenIntrospectionEndpoint;
    }

    public void setTokenIntrospectionEndpoint(String tokenIntrospectionEndpoint) {
        this.tokenIntrospectionEndpoint = tokenIntrospectionEndpoint;
    }

    public String getDeviceAuthorizationEndpoint() {
        return this.deviceAuthorizationEndpoint;
    }

    public void setDeviceAuthorizationEndpoint(String deviceAuthorizationEndpoint) {
        this.deviceAuthorizationEndpoint = deviceAuthorizationEndpoint;
    }

    public String getDeviceVerificationEndpoint() {
        return this.deviceVerificationEndpoint;
    }

    public void setDeviceVerificationEndpoint(String deviceVerificationEndpoint) {
        this.deviceVerificationEndpoint = deviceVerificationEndpoint;
    }

    public String getOidcClientRegistrationEndpoint() {
        return this.oidcClientRegistrationEndpoint;
    }

    public void setOidcClientRegistrationEndpoint(String oidcClientRegistrationEndpoint) {
        this.oidcClientRegistrationEndpoint = oidcClientRegistrationEndpoint;
    }

    public String getOidcUserInfoEndpoint() {
        return this.oidcUserInfoEndpoint;
    }

    public void setOidcUserInfoEndpoint(String oidcUserInfoEndpoint) {
        this.oidcUserInfoEndpoint = oidcUserInfoEndpoint;
    }

    public String getOidcLogoutEndpoint() {
        return this.oidcLogoutEndpoint;
    }

    public void setOidcLogoutEndpoint(String oidcLogoutEndpoint) {
        this.oidcLogoutEndpoint = oidcLogoutEndpoint;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("uaaServiceName", this.uaaServiceName).add("upmsServiceName", this.upmsServiceName).add("gatewayServiceUri", this.gatewayServiceUri).add("uaaServiceUri", this.uaaServiceUri).add("upmsServiceUri", this.upmsServiceUri).add("authorizationUri", this.authorizationUri).add("authorizationEndpoint", this.authorizationEndpoint).add("accessTokenUri", this.accessTokenUri).add("accessTokenEndpoint", this.accessTokenEndpoint).add("jwkSetUri", this.jwkSetUri).add("jwkSetEndpoint", this.jwkSetEndpoint).add("tokenRevocationUri", this.tokenRevocationUri).add("tokenRevocationEndpoint", this.tokenRevocationEndpoint).add("tokenIntrospectionUri", this.tokenIntrospectionUri).add("tokenIntrospectionEndpoint", this.tokenIntrospectionEndpoint).add("deviceAuthorizationUri", this.deviceAuthorizationUri).add("deviceAuthorizationEndpoint", this.deviceAuthorizationEndpoint).add("deviceVerificationUri", this.deviceVerificationUri).add("deviceVerificationEndpoint", this.deviceVerificationEndpoint).add("oidcClientRegistrationUri", this.oidcClientRegistrationUri).add("oidcClientRegistrationEndpoint", this.oidcClientRegistrationEndpoint).add("oidcLogoutUri", this.oidcLogoutUri).add("oidcLogoutEndpoint", this.oidcLogoutEndpoint).add("oidcUserInfoUri", this.oidcUserInfoUri).add("oidcUserInfoEndpoint", this.oidcUserInfoEndpoint).add("issuerUri", this.issuerUri).toString();
    }
}
