package com.pigx.engine.logic.identity.definition;

import com.pigx.engine.logic.identity.definition.AbstractOAuth2RegisteredClient;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.oauth2.core.enums.AllJwsAlgorithm;
import com.pigx.engine.oauth2.core.enums.SignatureJwsAlgorithm;
import com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/definition/AbstractOAuth2RegisteredClientConverter.class */
public abstract class AbstractOAuth2RegisteredClientConverter<T extends AbstractOAuth2RegisteredClient> implements RegisteredClientConverter<T> {
    @Override // com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter
    public Set<String> getScopes(T details) {
        Set<OAuth2Scope> clientScopes = details.getScopes();
        return (Set) clientScopes.stream().map((v0) -> {
            return v0.getScopeCode();
        }).collect(Collectors.toSet());
    }

    @Override // com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter
    public ClientSettings getClientSettings(T details) {
        ClientSettings.Builder clientSettingsBuilder = ClientSettings.builder();
        clientSettingsBuilder.requireAuthorizationConsent(details.getRequireAuthorizationConsent().booleanValue());
        clientSettingsBuilder.requireProofKey(details.getRequireProofKey().booleanValue());
        if (StringUtils.hasText(details.getJwkSetUrl())) {
            clientSettingsBuilder.jwkSetUrl(details.getJwkSetUrl());
        }
        JwsAlgorithm jwsAlgorithm = toJwsAlgorithm(details.getAuthenticationSigningAlgorithm());
        if (ObjectUtils.isNotEmpty(jwsAlgorithm)) {
            clientSettingsBuilder.tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm);
        }
        if (StringUtils.hasText(details.getX509CertificateSubjectDN())) {
            clientSettingsBuilder.x509CertificateSubjectDN(details.getX509CertificateSubjectDN());
        }
        return clientSettingsBuilder.build();
    }

    @Override // com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter
    public TokenSettings getTokenSettings(T details) {
        TokenSettings.Builder tokenSettingsBuilder = TokenSettings.builder();
        tokenSettingsBuilder.authorizationCodeTimeToLive(details.getAuthorizationCodeValidity());
        tokenSettingsBuilder.accessTokenTimeToLive(details.getAccessTokenValidity());
        tokenSettingsBuilder.accessTokenFormat(new OAuth2TokenFormat(details.getAccessTokenFormat().getFormat()));
        tokenSettingsBuilder.deviceCodeTimeToLive(details.getDeviceCodeValidity());
        tokenSettingsBuilder.reuseRefreshTokens(details.getReuseRefreshTokens().booleanValue());
        tokenSettingsBuilder.refreshTokenTimeToLive(details.getRefreshTokenValidity());
        SignatureAlgorithm signatureAlgorithm = toSignatureAlgorithm(details.getIdTokenSignatureAlgorithm());
        if (ObjectUtils.isNotEmpty(signatureAlgorithm)) {
            tokenSettingsBuilder.idTokenSignatureAlgorithm(signatureAlgorithm);
        }
        tokenSettingsBuilder.x509CertificateBoundAccessTokens(details.getX509CertificateBoundAccessTokens().booleanValue());
        return tokenSettingsBuilder.build();
    }

    private JwsAlgorithm toJwsAlgorithm(AllJwsAlgorithm allJwsAlgorithm) {
        if (ObjectUtils.isNotEmpty(allJwsAlgorithm)) {
            if (allJwsAlgorithm.getValue().intValue() < AllJwsAlgorithm.HS256.getValue().intValue()) {
                return SignatureAlgorithm.from(allJwsAlgorithm.name());
            }
            return MacAlgorithm.from(allJwsAlgorithm.name());
        }
        return null;
    }

    private SignatureAlgorithm toSignatureAlgorithm(SignatureJwsAlgorithm signatureJwsAlgorithm) {
        if (ObjectUtils.isNotEmpty(signatureJwsAlgorithm)) {
            return SignatureAlgorithm.from(signatureJwsAlgorithm.name());
        }
        return null;
    }
}
