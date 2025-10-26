package com.pigx.engine.oauth2.persistence.sas.jpa.definition;

import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/definition/AbstractOAuth2EntityConverter.class */
public abstract class AbstractOAuth2EntityConverter<S, T> implements Converter<S, T> {
    private final OAuth2JacksonProcessor jacksonProcessor;

    public AbstractOAuth2EntityConverter(OAuth2JacksonProcessor jacksonProcessor) {
        this.jacksonProcessor = jacksonProcessor;
    }

    protected Map<String, Object> parseMap(String data) {
        return this.jacksonProcessor.parseMap(data);
    }

    protected String writeMap(Map<String, Object> data) {
        return this.jacksonProcessor.writeMap(data);
    }
}
