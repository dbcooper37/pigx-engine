package com.pigx.engine.web.servlet.config;

import com.pigx.engine.web.service.stamp.AccessLimitedStampManager;
import com.pigx.engine.web.service.stamp.IdempotentStampManager;
import com.pigx.engine.web.servlet.secure.AccessLimitedInterceptor;
import com.pigx.engine.web.servlet.secure.IdempotentInterceptor;
import com.pigx.engine.web.servlet.secure.XssHttpServletFilter;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/config/SecureConfiguration.class */
public class SecureConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SecureConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Servlet Secure] Configure.");
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean({IdempotentStampManager.class})
    @Bean
    public IdempotentInterceptor idempotentInterceptor(IdempotentStampManager idempotentStampManager) {
        IdempotentInterceptor interceptor = new IdempotentInterceptor(idempotentStampManager);
        log.trace("[Herodotus] |- Bean [Idempotent Interceptor] Configure.");
        return interceptor;
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean({AccessLimitedStampManager.class})
    @Bean
    public AccessLimitedInterceptor accessLimitedInterceptor(AccessLimitedStampManager accessLimitedStampManager) {
        AccessLimitedInterceptor interceptor = new AccessLimitedInterceptor(accessLimitedStampManager);
        log.trace("[Herodotus] |- Bean [Access Limited Interceptor] Configure.");
        return interceptor;
    }

    @ConditionalOnMissingBean
    @Bean
    public XssHttpServletFilter xssHttpServletFilter() {
        XssHttpServletFilter filter = new XssHttpServletFilter();
        log.trace("[Herodotus] |- Bean [Xss Http Servlet Filter] Configure.");
        return filter;
    }
}
