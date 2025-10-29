package com.pigx.engine.rest.servlet.identity.config;

import com.pigx.engine.logic.identity.config.LogicIdentityConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(LogicIdentityConfiguration.class)
@ComponentScan(basePackages = {
        "com.pigx.engine.rest.servlet.identity.service",
        "com.pigx.engine.rest.servlet.identity.controller",
})
public class RestServletIdentityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestServletIdentityConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Rest Servlet Identity] Configure.");
    }
}
