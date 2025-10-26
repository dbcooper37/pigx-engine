package com.pigx.engine.message.autoconfigure.message;

import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
/* loaded from: message-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/message/autoconfigure/message/MessageAutoConfiguration.class */
public class MessageAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MessageAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Message] Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer messageErrorCodeMapperBuilderCustomizer() {
        MessageErrorCodeMapperBuilderCustomizer customizer = new MessageErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Message ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }
}
