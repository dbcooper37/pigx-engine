package com.pigx.engine.servlet.message.autoconfigure;

import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.message.websocket.servlet.annotation.EnableHerodotusServletWebSocket;
import com.pigx.engine.rest.servlet.message.annotation.EnableHerodotusRestServletMessage;
import com.pigx.engine.servlet.message.autoconfigure.customizer.MessageErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
@EnableHerodotusServletWebSocket
@EnableHerodotusRestServletMessage
public class ServletMessageAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ServletMessageAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Servlet Message] Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer messageErrorCodeMapperBuilderCustomizer() {
        MessageErrorCodeMapperBuilderCustomizer customizer = new MessageErrorCodeMapperBuilderCustomizer();
        log.debug("[PIGXD] |- Strategy [Message ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }
}
