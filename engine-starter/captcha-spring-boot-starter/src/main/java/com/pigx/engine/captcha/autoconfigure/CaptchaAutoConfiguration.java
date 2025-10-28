package com.pigx.engine.captcha.autoconfigure;

import cn.herodotus.engine.assistant.captcha.config.AssistantCaptchaConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({AssistantCaptchaConfiguration.class})
public class CaptchaAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CaptchaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Captcha] Configure.");
    }
}

