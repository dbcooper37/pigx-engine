package com.pigx.engine.assistant.access.config;

import com.pigx.engine.assistant.access.condition.ConditionalOnWxappEnabled;
import com.pigx.engine.assistant.access.processor.WxappAccessHandler;
import com.pigx.engine.assistant.access.processor.WxappProcessor;
import com.pigx.engine.assistant.access.properties.WxappProperties;
import com.pigx.engine.core.identity.enums.AccountCategory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({WxappProperties.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnWxappEnabled
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/config/AssistantAccessWxappConfiguration.class */
public class AssistantAccessWxappConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AssistantAccessWxappConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Assistant Access Wxapp] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public WxappProcessor wxappProcessor(WxappProperties wxappProperties) {
        WxappProcessor wxappProcessor = new WxappProcessor(wxappProperties);
        log.trace("[Herodotus] |- Bean [Wxapp Processor] Configure.");
        return wxappProcessor;
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean({WxappProcessor.class})
    @Bean({AccountCategory.WECHAT_MINI_APP_HANDLER})
    public WxappAccessHandler wxappAccessHandler(WxappProcessor wxappProcessor) {
        WxappAccessHandler wxappAccessHandler = new WxappAccessHandler(wxappProcessor);
        log.trace("[Herodotus] |- Bean [Wxapp Access Handler] Configure.");
        return wxappAccessHandler;
    }
}
