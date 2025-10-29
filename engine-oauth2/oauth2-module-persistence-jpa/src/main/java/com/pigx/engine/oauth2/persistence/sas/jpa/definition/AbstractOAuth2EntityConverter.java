package com.pigx.engine.oauth2.persistence.sas.jpa.definition;

import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;


public abstract class AbstractOAuth2EntityConverter<S, T> implements Converter<S, T> {

    private final OAuth2JacksonProcessor jacksonProcessor;

    public AbstractOAuth2EntityConverter(OAuth2JacksonProcessor jacksonProcessor) {
        this.jacksonProcessor = jacksonProcessor;
    }

    protected Map<String, Object> parseMap(String data) {
        return jacksonProcessor.parseMap(data);
    }

    protected String writeMap(Map<String, Object> data) {
        return jacksonProcessor.writeMap(data);
    }
}
