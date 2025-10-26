package com.pigx.engine.logic.identity.service;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.identity.converter.OAuth2DeviceToRegisteredClientConverter;
import com.pigx.engine.logic.identity.converter.RegisteredClientToOAuth2DeviceConverter;
import com.pigx.engine.logic.identity.entity.OAuth2Device;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.logic.identity.repository.OAuth2DeviceRepository;
import com.pigx.engine.oauth2.persistence.sas.jpa.repository.HerodotusRegisteredClientRepository;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/service/OAuth2DeviceService.class */
public class OAuth2DeviceService extends AbstractJpaService<OAuth2Device, String> {
    private static final Logger log = LoggerFactory.getLogger(OAuth2ApplicationService.class);
    private final RegisteredClientRepository registeredClientRepository;
    private final HerodotusRegisteredClientRepository herodotusRegisteredClientRepository;
    private final OAuth2DeviceRepository deviceRepository;
    private final Converter<OAuth2Device, RegisteredClient> oauth2DeviceToRegisteredClientConverter = new OAuth2DeviceToRegisteredClientConverter();
    private final Converter<RegisteredClient, OAuth2Device> registeredClientToOAuth2DeviceConverter;

    public OAuth2DeviceService(RegisteredClientRepository registeredClientRepository, HerodotusRegisteredClientRepository herodotusRegisteredClientRepository, OAuth2DeviceRepository deviceRepository, OAuth2ScopeService scopeService) {
        this.registeredClientRepository = registeredClientRepository;
        this.herodotusRegisteredClientRepository = herodotusRegisteredClientRepository;
        this.deviceRepository = deviceRepository;
        this.registeredClientToOAuth2DeviceConverter = new RegisteredClientToOAuth2DeviceConverter(scopeService);
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<OAuth2Device, String> getRepository() {
        return this.deviceRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public OAuth2Device saveAndFlush(OAuth2Device entity) {
        OAuth2Device device = (OAuth2Device) super.saveAndFlush((OAuth2DeviceService) entity);
        if (ObjectUtils.isNotEmpty(device)) {
            this.registeredClientRepository.save((RegisteredClient) this.oauth2DeviceToRegisteredClientConverter.convert(device));
            return device;
        }
        log.error("[Herodotus] |- OAuth2DeviceService saveOrUpdate error, rollback data!");
        throw new NullPointerException("save or update OAuth2DeviceService failed");
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService, com.pigx.engine.data.core.service.BaseService
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public void deleteById(String id) {
        super.deleteById((OAuth2DeviceService) id);
        this.herodotusRegisteredClientRepository.deleteById((HerodotusRegisteredClientRepository) id);
    }

    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public OAuth2Device authorize(String deviceId, String[] scopeIds) {
        Set<OAuth2Scope> scopes = new HashSet<>();
        for (String scopeId : scopeIds) {
            OAuth2Scope scope = new OAuth2Scope();
            scope.setScopeId(scopeId);
            scopes.add(scope);
        }
        return (OAuth2Device) findById(deviceId).map(entity -> {
            entity.setScopes(scopes);
            return entity;
        }).map(this::saveAndFlush).orElse(null);
    }

    public boolean sync(OidcClientRegistration oidcClientRegistration) {
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(oidcClientRegistration.getClientId());
        if (ObjectUtils.isNotEmpty(registeredClient)) {
            OAuth2Device oauth2Device = (OAuth2Device) this.registeredClientToOAuth2DeviceConverter.convert(registeredClient);
            if (ObjectUtils.isNotEmpty(oauth2Device)) {
                OAuth2Device result = (OAuth2Device) this.deviceRepository.save(oauth2Device);
                return ObjectUtils.isNotEmpty(result);
            }
            return false;
        }
        return false;
    }

    public boolean activate(String clientId, boolean isActivated) {
        int result = this.deviceRepository.activate(clientId, isActivated);
        return result != 0;
    }
}
