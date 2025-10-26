package com.pigx.engine.autoconfigure.jackson2.initializer;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/jackson2/initializer/Jackson2Initializer.class */
public class Jackson2Initializer {
    private final ObjectMapper objectMapper;

    public Jackson2Initializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        Jackson2Utils.setObjectMapper(objectMapper);
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
}
