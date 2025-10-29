package com.pigx.engine.web.service.initializer;

import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.core.foundation.enums.Architecture;
import com.pigx.engine.web.service.properties.EndpointProperties;
import com.pigx.engine.web.service.properties.PlatformProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;


public class ServiceContextHolderBuilder {

    private PlatformProperties platformProperties;
    private EndpointProperties endpointProperties;
    private ServerProperties serverProperties;

    private ServiceContextHolderBuilder() {

    }

    public static ServiceContextHolderBuilder builder() {
        return new ServiceContextHolderBuilder();
    }

    public ServiceContextHolderBuilder platformProperties(PlatformProperties platformProperties) {
        this.platformProperties = platformProperties;
        return this;
    }

    public ServiceContextHolderBuilder endpointProperties(EndpointProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
        return this;
    }

    public ServiceContextHolderBuilder serverProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
        return this;
    }

    public void build() {
        ServiceContextHolder.setPort(String.valueOf(this.getPort()));
        ServiceContextHolder.setIp(getHostAddress());
        setProperties(platformProperties, endpointProperties);
    }

    private String getHostAddress() {
        String address = WellFormedUtils.getHostAddress();
        if (ObjectUtils.isNotEmpty(serverProperties.getAddress())) {
            address = serverProperties.getAddress().getHostAddress();
        }

        if (StringUtils.isNotBlank(address)) {
            return address;
        } else {
            return "localhost";
        }
    }

    private Integer getPort() {
        Integer port = serverProperties.getPort();
        if (ObjectUtils.isNotEmpty(port)) {
            return port;
        } else {
            return 8080;
        }
    }

    private void setProperties(PlatformProperties platformProperties, EndpointProperties endpointProperties) {
        ServiceContextHolder.setArchitecture(platformProperties.getArchitecture());
        ServiceContextHolder.setDataAccessStrategy(platformProperties.getDataAccessStrategy());
        ServiceContextHolder.setProtocol(platformProperties.getProtocol());

        String issuerUri = endpointProperties.getIssuerUri();

        if (StringUtils.isNotBlank(issuerUri)) {
            if (platformProperties.getArchitecture() == Architecture.MONOCOQUE) {
                ServiceContextHolder.setGatewayServiceUri(issuerUri);
                ServiceContextHolder.setUaaServiceUri(issuerUri);
                ServiceContextHolder.setUpmsServiceUri(issuerUri);
                ServiceContextHolder.setMessageServiceUri(issuerUri);
                ServiceContextHolder.setOssServiceUri(issuerUri);
            } else {
                ServiceContextHolder.setUaaServiceName(endpointProperties.getUaaServiceName());
                ServiceContextHolder.setUpmsServiceName(endpointProperties.getUpmsServiceName());
                ServiceContextHolder.setMessageServiceName(endpointProperties.getMessageServiceName());
                ServiceContextHolder.setOssServiceName(endpointProperties.getOssServiceName());
                ServiceContextHolder.setGatewayServiceUri(endpointProperties.getGatewayServiceUri());
                ServiceContextHolder.setUaaServiceUri(endpointProperties.getUaaServiceUri());
                ServiceContextHolder.setUpmsServiceUri(endpointProperties.getUpmsServiceUri());
                ServiceContextHolder.setMessageServiceUri(endpointProperties.getMessageServiceUri());
                ServiceContextHolder.setOssServiceUri(endpointProperties.getOssServiceUri());
            }

            ServiceContextHolder.setAuthorizationUri(endpointProperties.getAuthorizationUri());
            ServiceContextHolder.setAuthorizationEndpoint(endpointProperties.getAuthorizationEndpoint());
            ServiceContextHolder.setPushedAuthorizationRequestUri(endpointProperties.getPushedAuthorizationRequestUri());
            ServiceContextHolder.setPushedAuthorizationRequestEndpoint(endpointProperties.getPushedAuthorizationRequestEndpoint());
            ServiceContextHolder.setAccessTokenUri(endpointProperties.getAccessTokenUri());
            ServiceContextHolder.setAccessTokenEndpoint(endpointProperties.getAccessTokenEndpoint());
            ServiceContextHolder.setJwkSetUri(endpointProperties.getJwkSetUri());
            ServiceContextHolder.setJwkSetEndpoint(endpointProperties.getJwkSetEndpoint());
            ServiceContextHolder.setTokenRevocationUri(endpointProperties.getTokenRevocationUri());
            ServiceContextHolder.setTokenRevocationEndpoint(endpointProperties.getTokenRevocationEndpoint());
            ServiceContextHolder.setTokenIntrospectionUri(endpointProperties.getTokenIntrospectionUri());
            ServiceContextHolder.setTokenIntrospectionEndpoint(endpointProperties.getTokenIntrospectionEndpoint());
            ServiceContextHolder.setDeviceAuthorizationUri(endpointProperties.getDeviceAuthorizationUri());
            ServiceContextHolder.setDeviceAuthorizationEndpoint(endpointProperties.getDeviceAuthorizationEndpoint());
            ServiceContextHolder.setDeviceVerificationUri(endpointProperties.getDeviceVerificationUri());
            ServiceContextHolder.setDeviceVerificationEndpoint(endpointProperties.getDeviceVerificationEndpoint());
            ServiceContextHolder.setOidcClientRegistrationUri(endpointProperties.getOidcClientRegistrationUri());
            ServiceContextHolder.setOidcClientRegistrationEndpoint(endpointProperties.getOidcClientRegistrationEndpoint());
            ServiceContextHolder.setOidcLogoutUri(endpointProperties.getOidcLogoutUri());
            ServiceContextHolder.setOidcLogoutEndpoint(endpointProperties.getOidcLogoutEndpoint());
            ServiceContextHolder.setOidcUserInfoUri(endpointProperties.getOidcUserInfoUri());
            ServiceContextHolder.setOidcUserInfoEndpoint(endpointProperties.getOidcUserInfoEndpoint());
            ServiceContextHolder.setIssuerUri(issuerUri);
        }
    }
}
