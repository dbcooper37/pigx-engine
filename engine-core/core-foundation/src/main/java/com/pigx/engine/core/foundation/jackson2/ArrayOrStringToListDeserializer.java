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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;

public class ArrayOrStringToListDeserializer extends StdDeserializer<List<String>> {
    public ArrayOrStringToListDeserializer() {
        super(List.class);
    }

    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(String.class);
    }

    public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonToken token = jsonParser.getCurrentToken();
        if (token.isScalarValue()) {
            String value = jsonParser.getText();
            value = value.replaceAll("\\s+", ",");
            return new ArrayList(Arrays.asList(StringUtils.commaDelimitedListToStringArray(value)));
        } else {
            return (List)jsonParser.readValueAs(new TypeReference<List<String>>() {
            });
        }
    }
}