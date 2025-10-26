package com.pigx.engine.oauth2.persistence.sas.jpa.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusRegisteredClient;
import com.pigx.engine.oauth2.persistence.sas.jpa.repository.HerodotusRegisteredClientRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/service/HerodotusRegisteredClientService.class */
public class HerodotusRegisteredClientService extends AbstractJpaService<HerodotusRegisteredClient, String> {
    private static final Logger log = LoggerFactory.getLogger(HerodotusRegisteredClientService.class);
    private final HerodotusRegisteredClientRepository registeredClientRepository;

    @Autowired
    public HerodotusRegisteredClientService(HerodotusRegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<HerodotusRegisteredClient, String> getRepository() {
        return this.registeredClientRepository;
    }

    public Optional<HerodotusRegisteredClient> findByClientId(String clientId) {
        Optional<HerodotusRegisteredClient> result = this.registeredClientRepository.findByClientId(clientId);
        log.trace("[Herodotus] |- HerodotusRegisteredClient Service findByClientId.");
        return result;
    }
}
