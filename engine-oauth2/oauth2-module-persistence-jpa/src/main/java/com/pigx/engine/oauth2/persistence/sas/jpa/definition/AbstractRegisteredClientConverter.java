package com.pigx.engine.oauth2.persistence.sas.jpa.definition;

import com.pigx.engine.core.identity.domain.RegisteredClientDetails;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;


public abstract class AbstractRegisteredClientConverter<S extends RegisteredClientDetails> extends AbstractOAuth2EntityConverter<S, RegisteredClient> implements RegisteredClientConverter<S> {

    public AbstractRegisteredClientConverter(OAuth2JacksonProcessor jacksonProcessor) {
        super(jacksonProcessor);
    }

    @Override
    public RegisteredClient convert(S details) {
        return RegisteredClientConverter.super.convert(details);
    }
}
