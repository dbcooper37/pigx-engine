package com.pigx.engine.web.servlet.config;

import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Configuration(proxyBeanMethods = false)
public class TemplateConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TemplateConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Servlet Template] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public ThymeleafTemplateHandler thymeleafTemplateHandler(SpringTemplateEngine springTemplateEngine) {
        ThymeleafTemplateHandler handler = new ThymeleafTemplateHandler(springTemplateEngine);
        log.trace("[PIGXD] |- Bean [Servlet Thymeleaf Template Handler] Configure.");
        return handler;
    }
}
