package com.pigx.engine.oss.dialect.autoconfigure;

import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.oss.dialect.autoconfigure.annotation.ConditionalOnUseMinioDialect;
import com.pigx.engine.oss.dialect.autoconfigure.annotation.ConditionalOnUseS3Dialect;
import com.pigx.engine.oss.dialect.autoconfigure.customizer.OssErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.oss.dialect.autoconfigure.properties.OssProperties;
import com.pigx.engine.oss.dialect.minio.config.OssDialectMinioConfiguration;
import com.pigx.engine.oss.dialect.s3.config.OssDialectS3Configuration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@AutoConfiguration
@EnableConfigurationProperties(OssProperties.class)
public class OssDialectAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssDialectAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [OSS Dialect] Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer ossErrorCodeMapperBuilderCustomizer() {
        OssErrorCodeMapperBuilderCustomizer customizer = new OssErrorCodeMapperBuilderCustomizer();
        log.trace("[Herodotus] |- Strategy [Oss ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnUseMinioDialect
    @Import({
            OssDialectMinioConfiguration.class,
    })
    static class UserMinioDialectConfiguration {

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnUseS3Dialect
    @Import({
            OssDialectS3Configuration.class,
    })
    static class UserS3DialectConfiguration {

    }
}
