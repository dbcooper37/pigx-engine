package com.pigx.engine.servlet.message.autoconfigure;

import com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager;
import com.pigx.engine.web.core.condition.ConditionalOnRestScanEnabled;
import com.pigx.engine.web.service.properties.ServiceProperties;
import com.pigx.engine.web.servlet.initializer.RestMappingScanner;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RestMappingScanEventManager.class)
@ConditionalOnRestScanEnabled
@EnableConfigurationProperties(ServiceProperties.class)
public class WebMvcRestMappingScanConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebMvcRestMappingScanConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Servlet Rest Mapping Scan] Configure.");
    }

    @Bean
    @ConditionalOnBean(RestMappingScanEventManager.class)
    @ConditionalOnMissingBean
    public RestMappingScanner restMappingScanner(ServiceProperties serviceProperties, RestMappingScanEventManager requestMappingScanManager) {
        RestMappingScanner scanner = new RestMappingScanner(serviceProperties.getScan(), requestMappingScanManager);
        log.trace("[PIGXD] |- Bean [Servlet Rest Mapping Scanner] Configure.");
        return scanner;
    }
}
