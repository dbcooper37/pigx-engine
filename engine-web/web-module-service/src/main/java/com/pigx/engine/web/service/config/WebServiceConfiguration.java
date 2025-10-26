package com.pigx.engine.web.service.config;

import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.web.core.support.WebPropertyFinder;
import com.pigx.engine.web.service.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import com.pigx.engine.web.service.customizer.WebErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.web.service.initializer.ServiceContextHolderBuilder;
import com.pigx.engine.web.service.properties.EndpointProperties;
import com.pigx.engine.web.service.properties.PlatformProperties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties({EndpointProperties.class, PlatformProperties.class})
@Configuration(proxyBeanMethods = false)
@Import({SpringdocConfiguration.class, SecureStampConfiguration.class})
/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/config/WebServiceConfiguration.class */
public class WebServiceConfiguration implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(WebServiceConfiguration.class);

    public WebServiceConfiguration(PlatformProperties platformProperties, EndpointProperties endpointProperties, ServerProperties serverProperties) {
        ServiceContextHolderBuilder.builder().endpointProperties(endpointProperties).platformProperties(platformProperties).serverProperties(serverProperties).build();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Web Service] Configure.");
    }

    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        ServiceContextHolder.setApplicationContext(applicationContext);
        ServiceContextHolder.setApplicationName(WebPropertyFinder.getApplicationName(applicationContext));
        log.debug("[Herodotus] |- HERODOTUS ApplicationContext initialization completed.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Jackson2 Xss ObjectMapper Builder Customizer] Configure.");
        return customizer;
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer webErrorCodeMapperBuilderCustomizer() {
        WebErrorCodeMapperBuilderCustomizer customizer = new WebErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Web ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }
}
