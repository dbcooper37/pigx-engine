package com.pigx.engine.oauth2.persistence.sas.jpa.definition;

import com.pigx.engine.core.identity.domain.RegisteredClientDetails;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/definition/AbstractRegisteredClientConverter.class */
public abstract class AbstractRegisteredClientConverter<S extends RegisteredClientDetails> extends AbstractOAuth2EntityConverter<S, RegisteredClient> implements RegisteredClientConverter<S> {
    public AbstractRegisteredClientConverter(OAuth2JacksonProcessor jacksonProcessor) {
        super(jacksonProcessor);
    }

    @Override // com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter
    public RegisteredClient convert(S details) {
        return super.convert((AbstractRegisteredClientConverter<S>) details);
    }
}
