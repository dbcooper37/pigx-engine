package com.pigx.engine.oauth2.persistence.sas.jpa.converter;

import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorizationConsent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/converter/HerodotusToOAuth2AuthorizationConsentConverter.class */
public class HerodotusToOAuth2AuthorizationConsentConverter implements Converter<HerodotusAuthorizationConsent, OAuth2AuthorizationConsent> {
    private final RegisteredClientRepository registeredClientRepository;

    public HerodotusToOAuth2AuthorizationConsentConverter(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.dao.DataRetrievalFailureException */
    public OAuth2AuthorizationConsent convert(HerodotusAuthorizationConsent authorizationConsent) throws DataRetrievalFailureException {
        String registeredClientId = authorizationConsent.getRegisteredClientId();
        RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException("The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }
        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(registeredClientId, authorizationConsent.getPrincipalName());
        if (authorizationConsent.getAuthorities() != null) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
                builder.authority(new HerodotusGrantedAuthority(authority));
            }
        }
        return builder.build();
    }
}
