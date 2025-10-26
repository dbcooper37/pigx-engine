package com.pigx.engine.autoconfigure.captcha;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.foundation.support.captcha.CaptchaRendererFactory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({ResourceProvider.class})
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/captcha/CaptchaAutoConfiguration.class */
public class CaptchaAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CaptchaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Captcha] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public CaptchaRendererFactory captchaRendererFactory() {
        CaptchaRendererFactory captchaRendererFactory = new CaptchaRendererFactory();
        log.trace("[Herodotus] |- Bean [Captcha Renderer Factory] Configure.");
        return captchaRendererFactory;
    }
}
