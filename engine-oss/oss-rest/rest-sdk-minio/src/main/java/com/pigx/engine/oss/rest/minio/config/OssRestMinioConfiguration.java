package com.pigx.engine.oss.rest.minio.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@AutoConfiguration
@ComponentScan(basePackages = {
        "com.pigx.engine.oss.rest.minio.service",
        "com.pigx.engine.oss.rest.minio.controller",
})
public class OssRestMinioConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssRestMinioConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Oss Rest Minio] Configure.");
    }
}
