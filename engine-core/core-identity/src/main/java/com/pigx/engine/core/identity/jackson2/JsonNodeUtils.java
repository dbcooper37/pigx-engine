package com.pigx.engine.core.identity.jackson2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

public class JsonNodeUtils {
    public static final TypeReference<Instant> INSTANT = new TypeReference<Instant>() {
    };
    public static final TypeReference<Set<String>> STRING_SET = new TypeReference<Set<String>>() {
    };
    public static final TypeReference<Map<String, Object>> STRING_OBJECT_MAP = new TypeReference<Map<String, Object>>() {
    };

    public static String findStringValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        } else {
            JsonNode value = jsonNode.findValue(fieldName);
            return value != null && value.isTextual() ? value.asText() : null;
        }
    }

    public static boolean findBooleanValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return false;
        } else {
            JsonNode value = jsonNode.findValue(fieldName);
            return value != null && value.isBoolean() && value.asBoolean();
        }
    }

    public static <T> T findValue(JsonNode jsonNode, String fieldName, TypeReference<T> valueTypeReference, ObjectMapper mapper) {
        if (jsonNode == null) {
            return null;
        } else {
            JsonNode value = jsonNode.findValue(fieldName);
            return (T)(value != null && value.isContainerNode() ? mapper.convertValue(value, valueTypeReference) : null);
        }
    }

    public static JsonNode findObjectNode(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        } else {
            JsonNode value = jsonNode.findValue(fieldName);
            return value != null && value.isObject() ? value : null;
        }
    }

    public static JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return (JsonNode)(jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance());
    }
}