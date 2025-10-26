package com.pigx.engine.autoconfigure.session;

import com.pigx.engine.core.autoconfigure.jackson2.Jackson2AutoConfiguration;
import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@AutoConfiguration(after = {Jackson2AutoConfiguration.class})
@ConditionalOnServletApplication
@ConditionalOnClass({SessionRepository.class})
@EnableRedisIndexedHttpSession
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/session/ServletSessionAutoConfiguration.class */
public class ServletSessionAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ServletSessionAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Servlet Spring Session] Configure.");
    }

    @Bean
    public SpringSessionRememberMeServices springSessionRememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        log.trace("[Herodotus] |- Bean [Spring Session Remember Me Services] Configure.");
        return rememberMeServices;
    }

    @Bean
    public SessionRepositoryCustomizer<RedisIndexedSessionRepository> sessionRepositoryCustomizer() {
        return sessionRepository -> {
            sessionRepository.setCleanupCron("* * * 31 2 ?");
        };
    }

    @Configuration(proxyBeanMethods = false)
    @EnableSpringHttpSession
    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/session/ServletSessionAutoConfiguration$SpringHttpSessionConfiguration.class */
    static class SpringHttpSessionConfiguration {
        SpringHttpSessionConfiguration() {
        }
    }
}
