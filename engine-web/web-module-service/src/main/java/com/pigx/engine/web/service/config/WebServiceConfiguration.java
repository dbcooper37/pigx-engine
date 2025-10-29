package com.pigx.engine.web.service.config;

import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.web.core.support.WebPropertyFinder;
import com.pigx.engine.web.service.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import com.pigx.engine.web.service.customizer.WebErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.web.service.initializer.ServiceContextHolderBuilder;
import com.pigx.engine.web.service.properties.EndpointProperties;
import com.pigx.engine.web.service.properties.PlatformProperties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({EndpointProperties.class, PlatformProperties.class})
@Import({
        SpringdocConfiguration.class,
        SecureStampConfiguration.class
})
public class WebServiceConfiguration implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(WebServiceConfiguration.class);

    /**
     * 尝试过几种 ServiceContextHolder 的初始化的方式，但都是会出现“时机”的不正确，导致 ServiceContextHolder 没有正常初始化，而导致抛错
     * 1. 利用 @AutoConfiguration 控制 Configuration 的前后顺序。这种方式始终会出现 WebSocket 先一步ServiceContextHolder初始化导致抛错。
     * 2. 利用 @AutoConfigureOrder 指定 Configuration 顺序，也是同样问题。
     * 3. 使用 Bean 的方式来构建 ServiceContextHolderBuilder，并且完成 ServiceContextHolder 的初始化的方式，始终不成功会出现时机不对的问题，导致抛错。跟踪过代码，发现使用 Bean 的方式，构建 ServiceContextHolderBuilder 根本就不执行。
     * <p>
     * 最终使用构造函数的方式，可以确保时机正确，几个参数对象设置正确，最终保证 ServiceContextHolder 的初始化时机合理
     *
     * @param platformProperties {@link PlatformProperties}
     * @param endpointProperties {@link EndpointProperties}
     * @param serverProperties   {@link ServerProperties}
     */
    public WebServiceConfiguration(PlatformProperties platformProperties, EndpointProperties endpointProperties, ServerProperties serverProperties) {
        ServiceContextHolderBuilder.builder()
                .endpointProperties(endpointProperties)
                .platformProperties(platformProperties)
                .serverProperties(serverProperties)
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Web Service] Configure.");
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        ServiceContextHolder.setApplicationContext(applicationContext);
        ServiceContextHolder.setApplicationName(WebPropertyFinder.getApplicationName(applicationContext));
        log.debug("[PIGXD] |- HERODOTUS ApplicationContext initialization completed.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.debug("[PIGXD] |- Strategy [Jackson2 Xss ObjectMapper Builder Customizer] Configure.");
        return customizer;
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer webErrorCodeMapperBuilderCustomizer() {
        WebErrorCodeMapperBuilderCustomizer customizer = new WebErrorCodeMapperBuilderCustomizer();
        log.debug("[PIGXD] |- Strategy [Web ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }
}
