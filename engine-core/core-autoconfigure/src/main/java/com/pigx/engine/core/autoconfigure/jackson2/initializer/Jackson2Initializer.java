package com.pigx.engine.core.autoconfigure.jackson2.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import org.springframework.stereotype.Component;


@Component
public class Jackson2Initializer {

    private final ObjectMapper objectMapper;

    public Jackson2Initializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        Jackson2Utils.setObjectMapper(objectMapper);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
