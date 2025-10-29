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

import java.util.Optional;


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

    @Override
    public void save(RegisteredClient registeredClient) {
        this.herodotusRegisteredClientService.save(toEntity(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<HerodotusRegisteredClient> herodotusRegisteredClient = this.herodotusRegisteredClientService.findById(id);
        return herodotusRegisteredClient.map(this::toObject).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.herodotusRegisteredClientService.findByClientId(clientId).map(this::toObject).orElse(null);
    }

    public void remove(String id) {
        this.herodotusRegisteredClientService.deleteById(id);
    }

    private RegisteredClient toObject(HerodotusRegisteredClient herodotusRegisteredClient) {
        return herodotusToOAuth2Converter.convert(herodotusRegisteredClient);
    }

    private HerodotusRegisteredClient toEntity(RegisteredClient registeredClient) {
        return oauth2ToHerodotusConverter.convert(registeredClient);
    }
}
