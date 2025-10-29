package com.pigx.engine.message.autoconfigure.message;

import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class MessageAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MessageAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD-ENGINE] |- Auto [Message] Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer messageErrorCodeMapperBuilderCustomizer() {
        MessageErrorCodeMapperBuilderCustomizer customizer = new MessageErrorCodeMapperBuilderCustomizer();
        log.debug("[PIGXD-ENGINE] |- Strategy [Message ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }
}
