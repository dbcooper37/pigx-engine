package com.pigx.engine.oss.dialect.minio.config;

import com.pigx.engine.oss.dialect.minio.definition.pool.*;
import com.pigx.engine.oss.dialect.minio.properties.MinioProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class MinioClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MinioClientConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Minio Client] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioClientObjectPool minioClientPool(MinioProperties minioProperties) {
        MinioClientPooledObjectFactory factory = new MinioClientPooledObjectFactory(minioProperties);
        MinioClientObjectPool pool = new MinioClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [Minio Client Pool] Configure.");
        return pool;
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioAsyncClientObjectPool minioAsyncClientPool(MinioProperties minioProperties) {
        MinioAsyncClientPooledObjectFactory factory = new MinioAsyncClientPooledObjectFactory(minioProperties);
        MinioAsyncClientObjectPool pool = new MinioAsyncClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [Minio Async Client Pool] Configure.");
        return pool;
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioAdminClientObjectPool minioAdminClientPool(MinioProperties minioProperties) {
        MinioAdminClientPooledObjectFactory factory = new MinioAdminClientPooledObjectFactory(minioProperties);
        MinioAdminClientObjectPool pool = new MinioAdminClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [Minio Admin Client Pool] Configure.");
        return pool;
    }
}
