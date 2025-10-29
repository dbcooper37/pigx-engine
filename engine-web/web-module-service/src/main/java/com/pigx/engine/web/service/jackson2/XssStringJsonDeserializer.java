package com.pigx.engine.web.service.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pigx.engine.core.foundation.utils.XssUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


public class XssStringJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        if (StringUtils.isNotBlank(value)) {
            return XssUtils.process(value);
        }

        return value;
    }
}
