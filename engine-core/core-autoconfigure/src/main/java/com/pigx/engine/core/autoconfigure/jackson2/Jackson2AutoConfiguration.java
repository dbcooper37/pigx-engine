package com.pigx.engine.core.autoconfigure.jackson2;

import com.pigx.engine.core.autoconfigure.jackson2.initializer.Jackson2Initializer;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@AutoConfiguration
public class Jackson2AutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Jackson2AutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Jackson2] Configure.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultObjectMapperBuilderCustomizer() {
        Jackson2DefaultObjectMapperBuilderCustomizer customizer = new Jackson2DefaultObjectMapperBuilderCustomizer();
        log.debug("[PIGXD] |- Strategy [Jackson2 Default ObjectMapper Builder Customizer] Configure.");
        return customizer;
    }

    /**
     * 注意：这里必须要使用 {@link ComponentScan} 的方式来扫描 {@link Jackson2Initializer} 。这种方式会让 {@link Jackson2AutoConfiguration} 在启动时优先配置。
     * <p>
     * 如果要换成用 @Bean 标注方法的方式，
     * 1. 将会直接导致初始化 Jackson 相关配置初始化延后，
     * 2. 间接导致 {@link ServiceContextHolder} 初始化延后使得 {@link ServiceContextHolder#getTokenIntrospectionUri()} 为空
     * 3. 最终会引起系统启动抛错
     * <p>
     * 目测 @ComponentScan 注解的优先级比 @Configuration 中 @Bean 的优先级更高。
     */
    @Configuration(proxyBeanMethods = false)
    @ComponentScan({
            "com.pigx.engine.core.autoconfigure.jackson2.initializer"
    })
    static class JacksonUtilsConfiguration {

    }
}
