package com.pigx.engine.core.definition.utils;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/utils/Jackson2YamlUtils.class */
public class Jackson2YamlUtils {
    private static final Logger log = LoggerFactory.getLogger(Jackson2YamlUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static <T> String writeAsString(T entity) {
        return writeAsString(entity, true);
    }

    public static <D> String writeAsString(D domain, boolean removeQuote) {
        try {
            String yaml = getObjectMapper().writeValueAsString(domain);
            if (StringUtils.isNotBlank(yaml) && removeQuote) {
                return Strings.CS.remove(yaml, SymbolConstants.QUOTE);
            }
            return yaml;
        } catch (JsonProcessingException e) {
            log.error("[Herodotus] |- Yaml writeAsString processing error! {}", e.getMessage());
            return null;
        }
    }
}
