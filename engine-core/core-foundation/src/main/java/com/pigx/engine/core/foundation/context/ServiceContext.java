package com.pigx.engine.core.foundation.context;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.enums.Protocol;
import com.pigx.engine.core.foundation.enums.Architecture;
import com.pigx.engine.core.foundation.enums.DataAccessStrategy;
import com.google.common.base.MoreObjects;
import org.springframework.context.ApplicationContext;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/context/ServiceContext.class */
class ServiceContext {
    private String port;
    private String ip;
    private String address;
    private String url;
    private String applicationName;
    private ApplicationContext applicationContext;
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
    private Architecture architecture = Architecture.DISTRIBUTED;
    private DataAccessStrategy dataAccessStrategy = DataAccessStrategy.REMOTE;
    private Protocol protocol = Protocol.HTTP;
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

    ServiceContext() {
    }

    public Architecture getArchitecture() {
        return this.architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public DataAccessStrategy getDataAccessStrategy() {
        return this.dataAccessStrategy;
    }

    public void setDataAccessStrategy(DataAccessStrategy dataAccessStrategy) {
        this.dataAccessStrategy = dataAccessStrategy;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
        return this.uaaServiceUri;
    }

    public void setUaaServiceUri(String uaaServiceUri) {
        this.uaaServiceUri = uaaServiceUri;
    }

    public String getUpmsServiceUri() {
        return this.upmsServiceUri;
    }

    public void setUpmsServiceUri(String upmsServiceUri) {
        this.upmsServiceUri = upmsServiceUri;
    }

    public String getMessageServiceUri() {
        return this.messageServiceUri;
    }

    public void setMessageServiceUri(String messageServiceUri) {
        this.messageServiceUri = messageServiceUri;
    }

    public String getOssServiceUri() {
        return this.ossServiceUri;
    }

    public void setOssServiceUri(String ossServiceUri) {
        this.ossServiceUri = ossServiceUri;
    }

    public String getAuthorizationUri() {
        return this.authorizationUri;
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getAuthorizationEndpoint() {
        return this.authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    public String getPushedAuthorizationRequestUri() {
        return this.pushedAuthorizationRequestUri;
    }

    public void setPushedAuthorizationRequestUri(String pushedAuthorizationRequestUri) {
        this.pushedAuthorizationRequestUri = pushedAuthorizationRequestUri;
    }

    public String getPushedAuthorizationRequestEndpoint() {
        return this.pushedAuthorizationRequestEndpoint;
    }

    public void setPushedAuthorizationRequestEndpoint(String pushedAuthorizationRequestEndpoint) {
        this.pushedAuthorizationRequestEndpoint = pushedAuthorizationRequestEndpoint;
    }

    public String getAccessTokenUri() {
        return this.accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getAccessTokenEndpoint() {
        return this.accessTokenEndpoint;
    }

    public void setAccessTokenEndpoint(String accessTokenEndpoint) {
        this.accessTokenEndpoint = accessTokenEndpoint;
    }

    public String getJwkSetUri() {
        return this.jwkSetUri;
    }

    public void setJwkSetUri(String jwkSetUri) {
        this.jwkSetUri = jwkSetUri;
    }

    public String getJwkSetEndpoint() {
        return this.jwkSetEndpoint;
    }

    public void setJwkSetEndpoint(String jwkSetEndpoint) {
        this.jwkSetEndpoint = jwkSetEndpoint;
    }

    public String getTokenRevocationUri() {
        return this.tokenRevocationUri;
    }

    public void setTokenRevocationUri(String tokenRevocationUri) {
        this.tokenRevocationUri = tokenRevocationUri;
    }

    public String getTokenRevocationEndpoint() {
        return this.tokenRevocationEndpoint;
    }

    public void setTokenRevocationEndpoint(String tokenRevocationEndpoint) {
        this.tokenRevocationEndpoint = tokenRevocationEndpoint;
    }

    public String getTokenIntrospectionUri() {
        return this.tokenIntrospectionUri;
    }

    public void setTokenIntrospectionUri(String tokenIntrospectionUri) {
        this.tokenIntrospectionUri = tokenIntrospectionUri;
    }

    public String getTokenIntrospectionEndpoint() {
        return this.tokenIntrospectionEndpoint;
    }

    public void setTokenIntrospectionEndpoint(String tokenIntrospectionEndpoint) {
        this.tokenIntrospectionEndpoint = tokenIntrospectionEndpoint;
    }

    public String getDeviceAuthorizationUri() {
        return this.deviceAuthorizationUri;
    }

    public void setDeviceAuthorizationUri(String deviceAuthorizationUri) {
        this.deviceAuthorizationUri = deviceAuthorizationUri;
    }

    public String getDeviceAuthorizationEndpoint() {
        return this.deviceAuthorizationEndpoint;
    }

    public void setDeviceAuthorizationEndpoint(String deviceAuthorizationEndpoint) {
        this.deviceAuthorizationEndpoint = deviceAuthorizationEndpoint;
    }

    public String getDeviceVerificationUri() {
        return this.deviceVerificationUri;
    }

    public void setDeviceVerificationUri(String deviceVerificationUri) {
        this.deviceVerificationUri = deviceVerificationUri;
    }

    public String getDeviceVerificationEndpoint() {
        return this.deviceVerificationEndpoint;
    }

    public void setDeviceVerificationEndpoint(String deviceVerificationEndpoint) {
        this.deviceVerificationEndpoint = deviceVerificationEndpoint;
    }

    public String getOidcClientRegistrationUri() {
        return this.oidcClientRegistrationUri;
    }

    public void setOidcClientRegistrationUri(String oidcClientRegistrationUri) {
        this.oidcClientRegistrationUri = oidcClientRegistrationUri;
    }

    public String getOidcClientRegistrationEndpoint() {
        return this.oidcClientRegistrationEndpoint;
    }

    public void setOidcClientRegistrationEndpoint(String oidcClientRegistrationEndpoint) {
        this.oidcClientRegistrationEndpoint = oidcClientRegistrationEndpoint;
    }

    public String getOidcLogoutUri() {
        return this.oidcLogoutUri;
    }

    public void setOidcLogoutUri(String oidcLogoutUri) {
        this.oidcLogoutUri = oidcLogoutUri;
    }

    public String getOidcLogoutEndpoint() {
        return this.oidcLogoutEndpoint;
    }

    public void setOidcLogoutEndpoint(String oidcLogoutEndpoint) {
        this.oidcLogoutEndpoint = oidcLogoutEndpoint;
    }

    public String getOidcUserInfoUri() {
        return this.oidcUserInfoUri;
    }

    public void setOidcUserInfoUri(String oidcUserInfoUri) {
        this.oidcUserInfoUri = oidcUserInfoUri;
    }

    public String getOidcUserInfoEndpoint() {
        return this.oidcUserInfoEndpoint;
    }

    public void setOidcUserInfoEndpoint(String oidcUserInfoEndpoint) {
        this.oidcUserInfoEndpoint = oidcUserInfoEndpoint;
    }

    public String getIssuerUri() {
        return this.issuerUri;
    }

    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("architecture", this.architecture).add("dataAccessStrategy", this.dataAccessStrategy).add("protocol", this.protocol).add("port", this.port).add("ip", this.ip).add(SystemConstants.SCOPE_ADDRESS, this.address).add("url", this.url).add("applicationName", this.applicationName).add("applicationContext", this.applicationContext).add("uaaServiceName", this.uaaServiceName).add("upmsServiceName", this.upmsServiceName).add("messageServiceName", this.messageServiceName).add("ossServiceName", this.ossServiceName).add("gatewayServiceUri", this.gatewayServiceUri).add("uaaServiceUri", this.uaaServiceUri).add("upmsServiceUri", this.upmsServiceUri).add("messageServiceUri", this.messageServiceUri).add("ossServiceUri", this.ossServiceUri).add("authorizationUri", this.authorizationUri).add("authorizationEndpoint", this.authorizationEndpoint).add("pushedAuthorizationRequestUri", this.pushedAuthorizationRequestUri).add("pushedAuthorizationRequestEndpoint", this.pushedAuthorizationRequestEndpoint).add("accessTokenUri", this.accessTokenUri).add("accessTokenEndpoint", this.accessTokenEndpoint).add("jwkSetUri", this.jwkSetUri).add("jwkSetEndpoint", this.jwkSetEndpoint).add("tokenRevocationUri", this.tokenRevocationUri).add("tokenRevocationEndpoint", this.tokenRevocationEndpoint).add("tokenIntrospectionUri", this.tokenIntrospectionUri).add("tokenIntrospectionEndpoint", this.tokenIntrospectionEndpoint).add("deviceAuthorizationUri", this.deviceAuthorizationUri).add("deviceAuthorizationEndpoint", this.deviceAuthorizationEndpoint).add("deviceVerificationUri", this.deviceVerificationUri).add("deviceVerificationEndpoint", this.deviceVerificationEndpoint).add("oidcClientRegistrationUri", this.oidcClientRegistrationUri).add("oidcClientRegistrationEndpoint", this.oidcClientRegistrationEndpoint).add("oidcLogoutUri", this.oidcLogoutUri).add("oidcLogoutEndpoint", this.oidcLogoutEndpoint).add("oidcUserInfoUri", this.oidcUserInfoUri).add("oidcUserInfoEndpoint", this.oidcUserInfoEndpoint).add("issuerUri", this.issuerUri).toString();
    }
}
