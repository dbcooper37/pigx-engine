package com.pigx.engine.autoconfigure.jackson2;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/jackson2/Jackson2AutoConfiguration.class */
public class Jackson2AutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(Jackson2AutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Jackson2] Configure.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultObjectMapperBuilderCustomizer() {
        Jackson2DefaultObjectMapperBuilderCustomizer customizer = new Jackson2DefaultObjectMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Jackson2 Default ObjectMapper Builder Customizer] Configure.");
        return customizer;
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan({"com.pigx.engine.core.autoconfigure.jackson2.initializer"})
    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/jackson2/Jackson2AutoConfiguration$JacksonUtilsConfiguration.class */
    static class JacksonUtilsConfiguration {
        JacksonUtilsConfiguration() {
        }
    }
}
