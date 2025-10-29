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


@Configuration(proxyBeanMethods = false)
@ConditionalOnWxappEnabled
@EnableConfigurationProperties(WxappProperties.class)
public class AssistantAccessWxappConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AssistantAccessWxappConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Assistant Access Wxapp] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public WxappProcessor wxappProcessor(WxappProperties wxappProperties) {
        WxappProcessor wxappProcessor = new WxappProcessor(wxappProperties);
        log.trace("[PIGXD] |- Bean [Wxapp Processor] Configure.");
        return wxappProcessor;
    }

    @Bean(AccountCategory.WECHAT_MINI_APP_HANDLER)
    @ConditionalOnBean(WxappProcessor.class)
    @ConditionalOnMissingBean
    public WxappAccessHandler wxappAccessHandler(WxappProcessor wxappProcessor) {
        WxappAccessHandler wxappAccessHandler = new WxappAccessHandler(wxappProcessor);
        log.trace("[PIGXD] |- Bean [Wxapp Access Handler] Configure.");
        return wxappAccessHandler;
    }
}
