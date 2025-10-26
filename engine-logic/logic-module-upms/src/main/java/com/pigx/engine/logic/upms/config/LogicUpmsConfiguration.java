package com.pigx.engine.logic.upms.config;

import com.pigx.engine.assistant.access.config.AssistantAccessConfiguration;
import com.pigx.engine.assistant.access.factory.AccessHandlerStrategyFactory;
import com.pigx.engine.logic.upms.definition.SocialAuthenticationHandler;
import com.pigx.engine.logic.upms.handler.DefaultSocialAuthenticationHandler;
import com.pigx.engine.logic.upms.service.security.SysSocialUserService;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = {"com.pigx.engine.logic.upms.repository.security", "com.pigx.engine.logic.upms.repository.hr"})
@EntityScan(basePackages = {"com.pigx.engine.logic.upms.entity.security", "com.pigx.engine.logic.upms.entity.hr"})
@ComponentScan(basePackages = {"com.pigx.engine.logic.upms.service.assistant", "com.pigx.engine.logic.upms.service.security", "com.pigx.engine.logic.upms.service.hr"})
@Import({AssistantAccessConfiguration.class})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/config/LogicUpmsConfiguration.class */
public class LogicUpmsConfiguration {
    private static final Logger log = LoggerFactory.getLogger(LogicUpmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Logic Upms] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public SocialAuthenticationHandler socialAuthenticationHandler(SysUserService sysUserService, SysSocialUserService sysSocialUserService, AccessHandlerStrategyFactory accessHandlerStrategyFactory) {
        DefaultSocialAuthenticationHandler defaultSocialAuthenticationHandler = new DefaultSocialAuthenticationHandler(sysUserService, sysSocialUserService, accessHandlerStrategyFactory);
        log.trace("[Herodotus] |- Bean [Default Social Authentication Handler] Configure.");
        return defaultSocialAuthenticationHandler;
    }
}
