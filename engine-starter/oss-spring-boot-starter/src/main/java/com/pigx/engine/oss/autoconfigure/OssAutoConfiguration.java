package com.pigx.engine.oss.autoconfigure;

import com.pigx.engine.oss.dialect.autoconfigure.annotation.ConditionalOnUseMinioDialect;
import com.pigx.engine.oss.rest.minio.config.OssRestMinioConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@AutoConfiguration
public class OssAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [OSS] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnUseMinioDialect
    @Import({
            OssRestMinioConfiguration.class,
    })
    static class MinioRestConfiguration {

    }
}
