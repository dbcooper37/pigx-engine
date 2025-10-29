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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Set;


public class ArrayToCommaDelimitedStringDeserializer extends StdDeserializer<String> {

    protected ArrayToCommaDelimitedStringDeserializer() {
        super(String.class);
    }

    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(Set.class);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        // spring-cloud-function-context.jar 会开启DeserializationFeature.FAIL_ON_TRAILING_TOKENS，这会导致前端传过来的数组无法解析抛错
        // see ContextFunctionCatalogAutoConfiguration#JsonMapperConfiguration
        // 临时新建一个 ObjectMapper，解决数组解析
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        jsonParser.setCodec(objectMapper);

        Set<String> collection = jsonParser.readValueAs(new TypeReference<Set<String>>() {
        });

        if (CollectionUtils.isNotEmpty(collection)) {
            return StringUtils.collectionToCommaDelimitedString(collection);
        }

        return null;
    }
}
