package com.pigx.engine.rest.servlet.message.config;

import com.pigx.engine.logic.message.annotation.EnableHerodotusLogicMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableHerodotusLogicMessage
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.pigx.engine.rest.servlet.message.controller"})
/* loaded from: rest-module-servlet-message-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/message/config/RestServletMessageConfiguration.class */
public class RestServletMessageConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RestServletMessageConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Rest Servlet Message] Configure.");
    }
}
