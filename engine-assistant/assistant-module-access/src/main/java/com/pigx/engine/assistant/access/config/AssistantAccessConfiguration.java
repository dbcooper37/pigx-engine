package com.pigx.engine.assistant.access.config;

import com.pigx.engine.assistant.access.customizer.AccessErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.assistant.access.definition.AccessHandler;
import com.pigx.engine.assistant.access.factory.AccessHandlerStrategyFactory;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import({AssistantAccessJustAuthConfiguration.class, AssistantAccessSmsConfiguration.class, AssistantAccessWxappConfiguration.class, AssistantAccessWxmppConfiguration.class})
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/config/AssistantAccessConfiguration.class */
public class AssistantAccessConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AssistantAccessConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Assistant Access] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public AccessHandlerStrategyFactory accessHandlerStrategyFactory(Map<String, AccessHandler> handlers) {
        AccessHandlerStrategyFactory accessHandlerStrategyFactory = new AccessHandlerStrategyFactory(handlers);
        log.trace("[Herodotus] |- Bean [Access Handler Strategy Factory] Configure.");
        return accessHandlerStrategyFactory;
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer accessErrorCodeMapperBuilderCustomizer() {
        AccessErrorCodeMapperBuilderCustomizer customizer = new AccessErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Access ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }
}
