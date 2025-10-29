package com.pigx.engine.data.mongodb.autoconfigure;

import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@AutoConfiguration
public class DataMongodbAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DataMongodbAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Data MongoDB] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnServletApplication
    @EnableMongoAuditing
    static class ServletMongoDBConfiguration {

    }
}
