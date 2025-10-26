package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/OAuth2JacksonProcessor.class */
public class OAuth2JacksonProcessor {
    private static final Logger log = LoggerFactory.getLogger(OAuth2JacksonProcessor.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OAuth2JacksonProcessor() {
        ClassLoader classLoader = OAuth2JacksonProcessor.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
        this.objectMapper.registerModules(new Module[]{new OAuth2AuthorizationServerJackson2Module()});
        this.objectMapper.registerModules(new Module[]{new HerodotusJackson2Module()});
        this.objectMapper.registerModules(new Module[]{new OAuth2TokenJackson2Module()});
    }

    public Map<String, Object> parseMap(String data) {
        try {
            return (Map) this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor.1
            });
        } catch (Exception ex) {
            log.error("[Herodotus] |- OAuth2 jackson processing parseMap catch error {}", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public String writeMap(Map<String, Object> data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            log.error("[Herodotus] |- OAuth2 jackson processing writeMap catch error {}", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
