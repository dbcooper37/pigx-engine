package com.pigx.engine.rest.servlet.upms.config;

import com.pigx.engine.assistant.access.condition.ConditionalOnJustAuthEnabled;
import com.pigx.engine.assistant.access.condition.ConditionalOnSmsEnabled;
import com.pigx.engine.assistant.access.condition.ConditionalOnWxappEnabled;
import com.pigx.engine.logic.upms.annotation.EnableHerodotusLogicUpms;
import com.pigx.engine.rest.servlet.upms.controller.social.JustAuthAccessController;
import com.pigx.engine.rest.servlet.upms.controller.social.PhoneNumberAccessController;
import com.pigx.engine.rest.servlet.upms.controller.social.WxappAccessController;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@EnableHerodotusLogicUpms
@ComponentScan(basePackages = {
        "com.pigx.engine.rest.servlet.upms.controller.assistant",
        "com.pigx.engine.rest.servlet.upms.controller.hr",
        "com.pigx.engine.rest.servlet.upms.controller.security",
})
public class RestServletUpmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestServletUpmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Rest Servlet Upms] Configure.");
    }

    @Bean
    @ConditionalOnSmsEnabled
    @ConditionalOnMissingBean
    public PhoneNumberAccessController phoneNumberAccessController() {
        PhoneNumberAccessController phoneNumberAuthenticationController = new PhoneNumberAccessController();
        log.trace("[PIGXD] |- Bean [Phone Number Access Controller] Configure.");
        return phoneNumberAuthenticationController;
    }

    @Bean
    @ConditionalOnJustAuthEnabled
    @ConditionalOnMissingBean
    public JustAuthAccessController justAuthSignInController() {
        JustAuthAccessController justAuthAuthenticationController = new JustAuthAccessController();
        log.trace("[PIGXD] |- Bean [Just Auth Access Controller] Configure.");
        return justAuthAuthenticationController;
    }

    @Bean
    @ConditionalOnWxappEnabled
    @ConditionalOnMissingBean
    public WxappAccessController wxappAccessController() {
        WxappAccessController wxappAccessController = new WxappAccessController();
        log.trace("[PIGXD] |- Bean [Wxapp Access Controller] Configure.");
        return wxappAccessController;
    }
}
