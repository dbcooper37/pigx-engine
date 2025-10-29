package com.pigx.engine.oss.s3.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class OssS3AutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssS3AutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Oss S3 Starter] Configure.");
    }
}
