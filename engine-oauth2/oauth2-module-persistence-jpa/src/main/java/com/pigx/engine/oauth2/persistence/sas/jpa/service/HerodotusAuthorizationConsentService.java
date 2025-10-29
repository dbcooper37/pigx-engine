package com.pigx.engine.oauth2.persistence.sas.jpa.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorizationConsent;
import com.pigx.engine.oauth2.persistence.sas.jpa.generator.HerodotusAuthorizationConsentId;
import com.pigx.engine.oauth2.persistence.sas.jpa.repository.HerodotusAuthorizationConsentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class HerodotusAuthorizationConsentService extends AbstractJpaService<HerodotusAuthorizationConsent, HerodotusAuthorizationConsentId> {

    private static final Logger log = LoggerFactory.getLogger(HerodotusAuthorizationConsentService.class);

    private final HerodotusAuthorizationConsentRepository authorizationConsentRepository;

    @Autowired
    public HerodotusAuthorizationConsentService(HerodotusAuthorizationConsentRepository authorizationConsentRepository) {
        this.authorizationConsentRepository = authorizationConsentRepository;
    }

    @Override
    public BaseJpaRepository<HerodotusAuthorizationConsent, HerodotusAuthorizationConsentId> getRepository() {
        return this.authorizationConsentRepository;
    }

    public Optional<HerodotusAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        Optional<HerodotusAuthorizationConsent> result = this.authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        log.trace("[PIGXD] |- HerodotusAuthorizationConsent Service findByRegisteredClientIdAndPrincipalName.");
        return result;
    }

    public void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        this.authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        log.trace("[PIGXD] |- HerodotusAuthorizationConsent Service deleteByRegisteredClientIdAndPrincipalName.");
    }
}
