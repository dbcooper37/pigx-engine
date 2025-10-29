package com.pigx.engine.oss.dialect.minio.config;

import com.pigx.engine.oss.dialect.minio.properties.MinioProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@AutoConfiguration
@EnableConfigurationProperties(MinioProperties.class)
@Import({
        MinioClientConfiguration.class
})
@ComponentScan(basePackages = {
        "com.pigx.engine.oss.dialect.minio.service",
        "com.pigx.engine.oss.dialect.minio.repository",
})
public class OssDialectMinioConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssDialectMinioConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Oss Dialect Minio] Configure.");
    }
}
