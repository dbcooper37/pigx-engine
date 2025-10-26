package com.pigx.engine.core.foundation.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

public class ArrayToCommaDelimitedStringDeserializer extends StdDeserializer<String> {
    protected ArrayToCommaDelimitedStringDeserializer() {
        super(String.class);
    }

    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(Set.class);
    }

    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        jsonParser.setCodec(objectMapper);
        Set<String> collection = (Set)jsonParser.readValueAs(new TypeReference<Set<String>>() {
        });
        return CollectionUtils.isNotEmpty(collection) ? StringUtils.collectionToCommaDelimitedString(collection) : null;
    }
}
