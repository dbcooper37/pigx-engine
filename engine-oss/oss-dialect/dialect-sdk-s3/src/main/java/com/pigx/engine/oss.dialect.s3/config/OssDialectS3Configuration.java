package com.pigx.engine.oss.dialect.s3.config;

import com.pigx.engine.oss.dialect.s3.properties.S3Properties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@AutoConfiguration
@EnableConfigurationProperties(S3Properties.class)
@Import({
        S3ClientConfiguration.class
})
@ComponentScan(basePackages = {
        "com.pigx.engine.oss.dialect.s3.service",
        "com.pigx.engine.oss.dialect.s3.repository",
})
public class OssDialectS3Configuration {

    private static final Logger log = LoggerFactory.getLogger(OssDialectS3Configuration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Oss S3 Dialect] Configure.");
    }
}
