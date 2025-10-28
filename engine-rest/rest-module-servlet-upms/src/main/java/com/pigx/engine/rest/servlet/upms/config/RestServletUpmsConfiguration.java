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
@ComponentScan(basePackages = {"com.pigx.engine.rest.servlet.upms.controller.assistant", "com.pigx.engine.rest.servlet.upms.controller.hr", "com.pigx.engine.rest.servlet.upms.controller.security"})
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/config/RestServletUpmsConfiguration.class */
public class RestServletUpmsConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RestServletUpmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Rest Servlet Upms] Configure.");
    }

    @ConditionalOnMissingBean
    @ConditionalOnSmsEnabled
    @Bean
    public PhoneNumberAccessController phoneNumberAccessController() {
        PhoneNumberAccessController phoneNumberAuthenticationController = new PhoneNumberAccessController();
        log.trace("[Herodotus] |- Bean [Phone Number Access Controller] Configure.");
        return phoneNumberAuthenticationController;
    }

    @ConditionalOnMissingBean
    @Bean
    @ConditionalOnJustAuthEnabled
    public JustAuthAccessController justAuthSignInController() {
        JustAuthAccessController justAuthAuthenticationController = new JustAuthAccessController();
        log.trace("[Herodotus] |- Bean [Just Auth Access Controller] Configure.");
        return justAuthAuthenticationController;
    }

    @ConditionalOnMissingBean
    @ConditionalOnWxappEnabled
    @Bean
    public WxappAccessController wxappAccessController() {
        WxappAccessController wxappAccessController = new WxappAccessController();
        log.trace("[Herodotus] |- Bean [Wxapp Access Controller] Configure.");
        return wxappAccessController;
    }
}
