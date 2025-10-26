package com.pigx.engine.assistant.access.config;

import com.pigx.engine.assistant.access.condition.ConditionalOnJustAuthEnabled;
import com.pigx.engine.assistant.access.processor.JustAuthAccessHandler;
import com.pigx.engine.assistant.access.processor.JustAuthProcessor;
import com.pigx.engine.assistant.access.properties.JustAuthProperties;
import com.pigx.engine.assistant.access.stamp.JustAuthStateStampManager;
import com.pigx.engine.core.identity.enums.AccountCategory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JustAuthProperties.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnJustAuthEnabled
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/config/AssistantAccessJustAuthConfiguration.class */
public class AssistantAccessJustAuthConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AssistantAccessJustAuthConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Assistant Access JustAuth] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public JustAuthStateStampManager justAuthStateStampManager(JustAuthProperties justAuthProperties) {
        JustAuthStateStampManager justAuthStateStampManager = new JustAuthStateStampManager(justAuthProperties);
        log.trace("[Herodotus] |- Bean [Just Auth State Redis Cache] Configure.");
        return justAuthStateStampManager;
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean({JustAuthStateStampManager.class})
    @Bean
    public JustAuthProcessor justAuthProcessor(JustAuthStateStampManager justAuthStateStampManager, JustAuthProperties justAuthProperties) {
        JustAuthProcessor justAuthProcessor = new JustAuthProcessor();
        justAuthProcessor.setJustAuthStateRedisCache(justAuthStateStampManager);
        justAuthProcessor.setJustAuthProperties(justAuthProperties);
        log.trace("[Herodotus] |- Bean [Just Auth Request Generator] Configure.");
        return justAuthProcessor;
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean({JustAuthProcessor.class})
    @Bean({AccountCategory.JUST_AUTH_HANDLER})
    public JustAuthAccessHandler justAuthAccessHandler(JustAuthProcessor justAuthProcessor) {
        JustAuthAccessHandler justAuthAccessHandler = new JustAuthAccessHandler(justAuthProcessor);
        log.trace("[Herodotus] |- Bean [Just Auth Access Handler] Configure.");
        return justAuthAccessHandler;
    }
}
