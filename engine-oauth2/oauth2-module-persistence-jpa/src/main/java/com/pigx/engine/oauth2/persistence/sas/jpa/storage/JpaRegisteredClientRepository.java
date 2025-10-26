package com.pigx.engine.oauth2.persistence.sas.jpa.storage;

import com.pigx.engine.oauth2.persistence.sas.jpa.converter.HerodotusToOAuth2RegisteredClientConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.converter.OAuth2ToHerodotusRegisteredClientConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusRegisteredClient;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusRegisteredClientService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/storage/JpaRegisteredClientRepository.class */
public class JpaRegisteredClientRepository implements RegisteredClientRepository {
    private final HerodotusRegisteredClientService herodotusRegisteredClientService;
    private final Converter<HerodotusRegisteredClient, RegisteredClient> herodotusToOAuth2Converter;
    private final Converter<RegisteredClient, HerodotusRegisteredClient> oauth2ToHerodotusConverter;

    public JpaRegisteredClientRepository(HerodotusRegisteredClientService herodotusRegisteredClientService, PasswordEncoder passwordEncoder) {
        this.herodotusRegisteredClientService = herodotusRegisteredClientService;
        OAuth2JacksonProcessor jacksonProcessor = new OAuth2JacksonProcessor();
        this.herodotusToOAuth2Converter = new HerodotusToOAuth2RegisteredClientConverter(jacksonProcessor);
        this.oauth2ToHerodotusConverter = new OAuth2ToHerodotusRegisteredClientConverter(jacksonProcessor, passwordEncoder);
    }

    public void save(RegisteredClient registeredClient) {
        this.herodotusRegisteredClientService.save(toEntity(registeredClient));
    }

    public RegisteredClient findById(String id) {
        return (RegisteredClient) this.herodotusRegisteredClientService.findById(id).map(this::toObject).orElse(null);
    }

    public RegisteredClient findByClientId(String clientId) {
        return (RegisteredClient) this.herodotusRegisteredClientService.findByClientId(clientId).map(this::toObject).orElse(null);
    }

    public void remove(String id) {
        this.herodotusRegisteredClientService.deleteById(id);
    }

    private RegisteredClient toObject(HerodotusRegisteredClient herodotusRegisteredClient) {
        return (RegisteredClient) this.herodotusToOAuth2Converter.convert(herodotusRegisteredClient);
    }

    private HerodotusRegisteredClient toEntity(RegisteredClient registeredClient) {
        return (HerodotusRegisteredClient) this.oauth2ToHerodotusConverter.convert(registeredClient);
    }
}
