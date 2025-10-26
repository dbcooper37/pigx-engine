package com.pigx.engine.core.foundation.jackson2;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.util.StringUtils;

public class ArrayOrStringToSetDeserializer extends StdDeserializer<Set<String>> {
    public ArrayOrStringToSetDeserializer() {
        super(Set.class);
    }

    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(String.class);
    }

    public Set<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonToken token = jsonParser.getCurrentToken();
        if (token.isScalarValue()) {
            String value = jsonParser.getText();
            value = value.replaceAll("\\s+", ",");
            return new LinkedHashSet(Arrays.asList(StringUtils.commaDelimitedListToStringArray(value)));
        } else {
            return (Set)jsonParser.readValueAs(new TypeReference<Set<String>>() {
            });
        }
    }
}