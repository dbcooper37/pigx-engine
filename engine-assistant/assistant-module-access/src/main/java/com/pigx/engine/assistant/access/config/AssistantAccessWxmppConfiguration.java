package com.pigx.engine.assistant.access.config;

import com.pigx.engine.assistant.access.condition.ConditionalOnWxmppEnabled;
import com.pigx.engine.assistant.access.processor.WxmppLogHandler;
import com.pigx.engine.assistant.access.processor.WxmppProcessor;
import com.pigx.engine.assistant.access.properties.WxmppProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@EnableConfigurationProperties({WxmppProperties.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnWxmppEnabled
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/config/AssistantAccessWxmppConfiguration.class */
public class AssistantAccessWxmppConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AssistantAccessWxmppConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Assistant Access Wxmpp] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public WxmppProcessor wxmppProcessor(WxmppProperties wxmppProperties, StringRedisTemplate stringRedisTemplate) {
        WxmppProcessor wxmppProcessor = new WxmppProcessor();
        wxmppProcessor.setWxmppProperties(wxmppProperties);
        wxmppProcessor.setWxmppLogHandler(new WxmppLogHandler());
        wxmppProcessor.setStringRedisTemplate(stringRedisTemplate);
        log.trace("[Herodotus] |- Bean [Wxmpp Processor] Configure.");
        return wxmppProcessor;
    }
}
