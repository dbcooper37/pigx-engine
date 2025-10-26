package com.pigx.engine.oauth2.persistence.sas.jpa.converter;

import com.pigx.engine.oauth2.persistence.sas.jpa.definition.AbstractOAuth2EntityConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusRegisteredClient;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import cn.hutool.v7.core.date.DateUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/converter/OAuth2ToHerodotusRegisteredClientConverter.class */
public class OAuth2ToHerodotusRegisteredClientConverter extends AbstractOAuth2EntityConverter<RegisteredClient, HerodotusRegisteredClient> {
    private final PasswordEncoder passwordEncoder;

    public OAuth2ToHerodotusRegisteredClientConverter(OAuth2JacksonProcessor jacksonProcessor, PasswordEncoder passwordEncoder) {
        super(jacksonProcessor);
        this.passwordEncoder = passwordEncoder;
    }

    public HerodotusRegisteredClient convert(RegisteredClient registeredClient) {
        List<String> clientAuthenticationMethods = new ArrayList<>(registeredClient.getClientAuthenticationMethods().size());
        registeredClient.getClientAuthenticationMethods().forEach(clientAuthenticationMethod -> {
            clientAuthenticationMethods.add(clientAuthenticationMethod.getValue());
        });
        List<String> authorizationGrantTypes = new ArrayList<>(registeredClient.getAuthorizationGrantTypes().size());
        registeredClient.getAuthorizationGrantTypes().forEach(authorizationGrantType -> {
            authorizationGrantTypes.add(authorizationGrantType.getValue());
        });
        HerodotusRegisteredClient entity = new HerodotusRegisteredClient();
        entity.setId(registeredClient.getId());
        entity.setClientId(registeredClient.getClientId());
        entity.setClientIdIssuedAt(DateUtil.toLocalDateTime(registeredClient.getClientIdIssuedAt()));
        entity.setClientSecret(encode(registeredClient.getClientSecret()));
        entity.setClientSecretExpiresAt(DateUtil.toLocalDateTime(registeredClient.getClientSecretExpiresAt()));
        entity.setClientName(registeredClient.getClientName());
        entity.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods));
        entity.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes));
        entity.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        entity.setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getPostLogoutRedirectUris()));
        entity.setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()));
        entity.setClientSettings(writeMap(registeredClient.getClientSettings().getSettings()));
        entity.setTokenSettings(writeMap(registeredClient.getTokenSettings().getSettings()));
        return entity;
    }

    private String encode(String value) {
        if (value != null) {
            return this.passwordEncoder.encode(value);
        }
        return null;
    }
}
