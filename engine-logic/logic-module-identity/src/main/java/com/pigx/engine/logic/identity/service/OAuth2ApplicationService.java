package com.pigx.engine.logic.identity.service;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.identity.converter.OAuth2ApplicationToRegisteredClientConverter;
import com.pigx.engine.logic.identity.entity.OAuth2Application;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.logic.identity.repository.OAuth2ApplicationRepository;
import com.pigx.engine.oauth2.persistence.sas.jpa.repository.HerodotusRegisteredClientRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class OAuth2ApplicationService extends AbstractJpaService<OAuth2Application, String> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ApplicationService.class);

    private final RegisteredClientRepository registeredClientRepository;
    private final HerodotusRegisteredClientRepository herodotusRegisteredClientRepository;
    private final OAuth2ApplicationRepository applicationRepository;
    private final Converter<OAuth2Application, RegisteredClient> objectConverter;

    public OAuth2ApplicationService(RegisteredClientRepository registeredClientRepository, HerodotusRegisteredClientRepository herodotusRegisteredClientRepository, OAuth2ApplicationRepository applicationRepository) {
        this.registeredClientRepository = registeredClientRepository;
        this.herodotusRegisteredClientRepository = herodotusRegisteredClientRepository;
        this.applicationRepository = applicationRepository;
        this.objectConverter = new OAuth2ApplicationToRegisteredClientConverter();
    }

    @Override
    public BaseJpaRepository<OAuth2Application, String> getRepository() {
        return this.applicationRepository;
    }

    @Override
    public OAuth2Application saveAndFlush(OAuth2Application entity) {
        OAuth2Application application = super.saveAndFlush(entity);
        if (ObjectUtils.isNotEmpty(application)) {
            registeredClientRepository.save(objectConverter.convert(application));
            log.debug("[PIGXD] |- OAuth2ApplicationService saveOrUpdate.");
            return application;
        } else {
            log.error("[PIGXD] |- OAuth2ApplicationService saveOrUpdate error, rollback data!");
            throw new NullPointerException("save or update OAuth2Application failed");
        }
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Override
    public void deleteById(String id) {
        super.deleteById(id);
        herodotusRegisteredClientRepository.deleteById(id);
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public OAuth2Application authorize(String applicationId, String[] scopeIds) {

        Set<OAuth2Scope> scopes = new HashSet<>();
        for (String scopeId : scopeIds) {
            OAuth2Scope scope = new OAuth2Scope();
            scope.setScopeId(scopeId);
            scopes.add(scope);
        }

        Optional<OAuth2Application> oldApplication = findById(applicationId);
        return oldApplication.map(entity -> {
                    entity.setScopes(scopes);
                    return entity;
                })
                .map(this::saveAndFlush)
                .orElse(null);
    }

    public OAuth2Application findByClientId(String clientId) {
        return applicationRepository.findByClientId(clientId);
    }
}
