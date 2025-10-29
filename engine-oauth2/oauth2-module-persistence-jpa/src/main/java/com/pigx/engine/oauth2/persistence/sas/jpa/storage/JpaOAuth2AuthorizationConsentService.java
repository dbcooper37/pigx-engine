package com.pigx.engine.oauth2.persistence.sas.jpa.storage;

import com.pigx.engine.oauth2.persistence.sas.jpa.converter.HerodotusToOAuth2AuthorizationConsentConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.converter.OAuth2ToHerodotusAuthorizationConsentConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorizationConsent;
import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusAuthorizationConsentService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;


public class JpaOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final HerodotusAuthorizationConsentService herodotusAuthorizationConsentService;
    private final Converter<HerodotusAuthorizationConsent, OAuth2AuthorizationConsent> herodotusToOAuth2Converter;
    private final Converter<OAuth2AuthorizationConsent, HerodotusAuthorizationConsent> oauth2ToherodotusConverter;

    public JpaOAuth2AuthorizationConsentService(HerodotusAuthorizationConsentService herodotusAuthorizationConsentService, RegisteredClientRepository registeredClientRepository) {
        this.herodotusAuthorizationConsentService = herodotusAuthorizationConsentService;
        this.herodotusToOAuth2Converter = new HerodotusToOAuth2AuthorizationConsentConverter(registeredClientRepository);
        this.oauth2ToherodotusConverter = new OAuth2ToHerodotusAuthorizationConsentConverter();
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        this.herodotusAuthorizationConsentService.save(toEntity(authorizationConsent));
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        this.herodotusAuthorizationConsentService.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return this.herodotusAuthorizationConsentService.findByRegisteredClientIdAndPrincipalName(
                registeredClientId, principalName).map(this::toObject).orElse(null);
    }

    private OAuth2AuthorizationConsent toObject(HerodotusAuthorizationConsent authorizationConsent) {
        return herodotusToOAuth2Converter.convert(authorizationConsent);
    }

    private HerodotusAuthorizationConsent toEntity(OAuth2AuthorizationConsent authorizationConsent) {
        return oauth2ToherodotusConverter.convert(authorizationConsent);
    }
}
