package com.pigx.engine.oss.dialect.s3.config;

import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientPooledObjectFactory;
import com.pigx.engine.oss.dialect.s3.properties.S3Properties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class S3ClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(S3ClientConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [S3 Client] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public S3ClientObjectPool s3ClientObjectPool(S3Properties s3Properties) {
        S3ClientPooledObjectFactory factory = new S3ClientPooledObjectFactory(s3Properties);
        S3ClientObjectPool pool = new S3ClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [S3 Client Pool] Configure.");
        return pool;
    }
}
