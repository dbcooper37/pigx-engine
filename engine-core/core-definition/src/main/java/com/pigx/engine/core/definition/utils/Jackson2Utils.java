package com.pigx.engine.core.definition.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author gengwei.zheng
 */
public class Jackson2Utils {

    private static final Logger logger = LoggerFactory.getLogger(Jackson2Utils.class);

    private static volatile Jackson2Utils instance;

    private ObjectMapper objectMapper;

    private Jackson2Utils() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        objectMapper.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        objectMapper.registerModules(new Jdk8Module(), new JavaTimeModule());
        this.objectMapper = objectMapper;
    }

    public static Jackson2Utils getInstance() {
        if (ObjectUtils.isEmpty(instance)) {
            synchronized (Jackson2Utils.class) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = new Jackson2Utils();
                }
            }
        }

        return instance;
    }

    public static <T> String toJson(T domain) {
        try {
            return getObjectMapper().writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 json processing error, when to json! {}", e.getMessage());
            return null;
        }
    }

    private void objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return getInstance().objectMapper();
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        getInstance().objectMapper(objectMapper);
    }

    public static <T> T toObject(String content, Class<T> valueType) {
        try {
            return getObjectMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 json processing error, when to object with value type! {}", e.getMessage());
            return null;
        }
    }

    public static TypeFactory getTypeFactory() {
        return getObjectMapper().getTypeFactory();
    }

    public static <T> T toObject(Map<String, Object> content, Class<T> valueType) {
        try {
            return getObjectMapper().convertValue(content, valueType);
        } catch (IllegalArgumentException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use STRING with valueType processing error! {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String content, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(content, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use STRING with typeReference processing error! {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String content, JavaType javaType) {
        try {
            return getObjectMapper().readValue(content, javaType);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use STRING with javaType processing error! {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(InputStream inputStream, Class<T> valueType) {
        try {
            return getObjectMapper().readValue(inputStream, valueType);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use INPUT_STREAM with class processing error! {}", e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use INPUT_STREAM with class processing catch IO error {}.", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(InputStream inputStream, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(inputStream, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use INPUT_STREAM with typeReference processing error! {}", e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use INPUT_STREAM with typeReference processing catch IO error {}.", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(InputStream inputStream, JavaType javaType) {
        try {
            return getObjectMapper().readValue(inputStream, javaType);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use INPUT_STREAM with javaType processing error! {}", e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("[PIGXD] |- Jackson2 toObject use INPUT_STREAM with javaType processing catch IO error {}.", e.getMessage());
            return null;
        }
    }

    public static <T> List<T> toList(String content, Class<T> clazz) {
        JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(List.class, clazz);
        return toObject(content, javaType);
    }

    public static <K, V> Map<K, V> toMap(String content, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = getObjectMapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return toObject(content, javaType);
    }

    public static <T> Set<T> toSet(String content, Class<T> clazz) {
        JavaType javaType = getTypeFactory().constructCollectionLikeType(Set.class, clazz);
        return toObject(content, javaType);
    }

    public static Map<String, Object> toMap(String content) {
        return toMap(content, String.class, Object.class);
    }

    public static <T> T[] toArray(String content, Class<T> clazz) {
        JavaType javaType = getTypeFactory().constructArrayType(clazz);
        return toObject(content, javaType);
    }

    public static <T> T[] toArray(String content) {
        return toObject(content, new TypeReference<>() {
        });
    }

    public static JsonNode toNode(String content) {
        try {
            return getObjectMapper().readTree(content);
        } catch (JsonProcessingException e) {
            logger.error("[PIGXD] |- Jackson2 json processing error, when to node with string! {}", e.getMessage());
            return null;
        }
    }

    public static JsonNode toNode(JsonParser jsonParser) {
        try {
            return getObjectMapper().readTree(jsonParser);
        } catch (IOException e) {
            logger.error("[PIGXD] |- Jackson2 io error, when to node with json parser! {}", e.getMessage());
            return null;
        }
    }

    public static JsonParser createParser(String content) {
        try {
            return getObjectMapper().createParser(content);
        } catch (IOException e) {
            logger.error("[PIGXD] |- Jackson2 io error, when create parser! {}", e.getMessage());
            return null;
        }
    }

    public static <R> R loop(JsonNode jsonNode, Function<JsonNode, R> function) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                loop(entry.getValue(), function);
            }
        }

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                loop(node, function);
            }
        }

        if (jsonNode.isValueNode()) {
            return function.apply(jsonNode);
        } else {
            return null;
        }
    }

    private ObjectMapper objectMapper() {
        return objectMapper;
    }
}
