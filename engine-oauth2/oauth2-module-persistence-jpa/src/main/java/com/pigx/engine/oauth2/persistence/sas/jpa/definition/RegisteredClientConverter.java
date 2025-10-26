package com.pigx.engine.oauth2.persistence.sas.jpa.definition;

import com.pigx.engine.core.identity.domain.RegisteredClientDetails;
import com.pigx.engine.oauth2.core.utils.OAuth2AuthenticationUtils;
import cn.hutool.v7.core.date.DateUtil;
import java.util.Set;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/definition/RegisteredClientConverter.class */
public interface RegisteredClientConverter<S extends RegisteredClientDetails> extends Converter<S, RegisteredClient> {
    Set<String> getScopes(S details);

    ClientSettings getClientSettings(S details);

    TokenSettings getTokenSettings(S details);

    @Override // 
    default RegisteredClient convert(S details) {
        Set<String> clientScopes = getScopes(details);
        ClientSettings clientSettings = getClientSettings(details);
        TokenSettings tokenSettings = getTokenSettings(details);
        Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(details.getClientAuthenticationMethods());
        Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(details.getAuthorizationGrantTypes());
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(details.getRedirectUris());
        Set<String> postLogoutRedirectUris = StringUtils.commaDelimitedListToSet(details.getPostLogoutRedirectUris());
        return RegisteredClient.withId(details.getId()).clientId(details.getClientId()).clientIdIssuedAt(DateUtil.toInstant(details.getClientIdIssuedAt())).clientSecret(details.getClientSecret()).clientSecretExpiresAt(DateUtil.toInstant(details.getClientSecretExpiresAt())).clientName(details.getId()).clientAuthenticationMethods(authenticationMethods -> {
            clientAuthenticationMethods.forEach(authenticationMethod -> {
                authenticationMethods.add(OAuth2AuthenticationUtils.resolveClientAuthenticationMethod(authenticationMethod));
            });
        }).authorizationGrantTypes(grantTypes -> {
            authorizationGrantTypes.forEach(grantType -> {
                grantTypes.add(OAuth2AuthenticationUtils.resolveAuthorizationGrantType(grantType));
            });
        }).redirectUris(uris -> {
            uris.addAll(redirectUris);
        }).postLogoutRedirectUris(uris2 -> {
            uris2.addAll(postLogoutRedirectUris);
        }).scopes(scopes -> {
            scopes.addAll(clientScopes);
        }).clientSettings(clientSettings).tokenSettings(tokenSettings).build();
    }
}
