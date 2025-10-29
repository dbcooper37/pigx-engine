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


@Configuration(proxyBeanMethods = false)
@ConditionalOnJustAuthEnabled
@EnableConfigurationProperties(JustAuthProperties.class)
public class AssistantAccessJustAuthConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AssistantAccessJustAuthConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Assistant Access JustAuth] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public JustAuthStateStampManager justAuthStateStampManager(JustAuthProperties justAuthProperties) {
        JustAuthStateStampManager justAuthStateStampManager = new JustAuthStateStampManager(justAuthProperties);
        log.trace("[PIGXD] |- Bean [Just Auth State Redis Cache] Configure.");
        return justAuthStateStampManager;
    }

    @Bean
    @ConditionalOnBean(JustAuthStateStampManager.class)
    @ConditionalOnMissingBean
    public JustAuthProcessor justAuthProcessor(JustAuthStateStampManager justAuthStateStampManager, JustAuthProperties justAuthProperties) {
        JustAuthProcessor justAuthProcessor = new JustAuthProcessor();
        justAuthProcessor.setJustAuthStateRedisCache(justAuthStateStampManager);
        justAuthProcessor.setJustAuthProperties(justAuthProperties);
        log.trace("[PIGXD] |- Bean [Just Auth Request Generator] Configure.");
        return justAuthProcessor;
    }

    @Bean(AccountCategory.JUST_AUTH_HANDLER)
    @ConditionalOnBean(JustAuthProcessor.class)
    @ConditionalOnMissingBean
    public JustAuthAccessHandler justAuthAccessHandler(JustAuthProcessor justAuthProcessor) {
        JustAuthAccessHandler justAuthAccessHandler = new JustAuthAccessHandler(justAuthProcessor);
        log.trace("[PIGXD] |- Bean [Just Auth Access Handler] Configure.");
        return justAuthAccessHandler;
    }
}
