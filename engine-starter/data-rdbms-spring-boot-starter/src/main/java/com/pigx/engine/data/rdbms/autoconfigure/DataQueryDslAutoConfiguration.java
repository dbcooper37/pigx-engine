package com.pigx.engine.data.rdbms.autoconfigure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class DataQueryDslAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DataQueryDslAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Data Query Dsl] Configure.");
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        log.trace("[PIGXD] |- Bean [JPA Query Factory] Configure.");
        return queryFactory;
    }
}
