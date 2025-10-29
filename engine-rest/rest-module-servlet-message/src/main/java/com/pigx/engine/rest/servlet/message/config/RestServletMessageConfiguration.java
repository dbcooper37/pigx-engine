package com.pigx.engine.rest.servlet.message.config;

import com.pigx.engine.logic.message.annotation.EnableHerodotusLogicMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@EnableHerodotusLogicMessage
@ComponentScan(basePackages = {
        "com.pigx.engine.rest.servlet.message.controller",
})
public class RestServletMessageConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestServletMessageConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Rest Servlet Message] Configure.");
    }
}
