package com.pigx.engine.logic.message.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration(proxyBeanMethods = false)
@EntityScan(basePackages = {
        "com.pigx.engine.logic.message.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.pigx.engine.logic.message.repository",
})
@ComponentScan(basePackages = {
        "com.pigx.engine.logic.message.service",
        "com.pigx.engine.logic.message.listener",
})
public class LogicMessageConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LogicMessageConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Logic Message] Configure.");
    }
}
