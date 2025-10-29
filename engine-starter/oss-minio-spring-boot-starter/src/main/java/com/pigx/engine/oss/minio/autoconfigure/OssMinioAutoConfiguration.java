package com.pigx.engine.oss.minio.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class OssMinioAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssMinioAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Oss Minio] Configure.");
    }
}
