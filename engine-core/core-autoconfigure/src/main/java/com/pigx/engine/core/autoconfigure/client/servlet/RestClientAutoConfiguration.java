package com.pigx.engine.autoconfigure.client.servlet;

import com.pigx.engine.core.foundation.condition.ConditionalOnArchitecture;
import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import com.pigx.engine.core.foundation.enums.Architecture;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@ConditionalOnClass({RestClient.class})
@ConditionalOnServletApplication
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/RestClientAutoConfiguration.class */
public class RestClientAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RestClientAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Rest Client] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.MONOCOQUE)
    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/RestClientAutoConfiguration$RestClientMonocoqueBuilderConfiguration.class */
    static class RestClientMonocoqueBuilderConfiguration {
        RestClientMonocoqueBuilderConfiguration() {
        }

        @Bean
        public RestClient.Builder restClientBuilder(RestClientBuilderConfigurer restClientBuilderConfigurer) {
            return restClientBuilderConfigurer.configure(RestClient.builder());
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/RestClientAutoConfiguration$RestClientDistributedBuilderConfiguration.class */
    static class RestClientDistributedBuilderConfiguration {
        RestClientDistributedBuilderConfiguration() {
        }

        @Bean
        @LoadBalanced
        public RestClient.Builder restClientBuilder(RestClientBuilderConfigurer restClientBuilderConfigurer) {
            return restClientBuilderConfigurer.configure(RestClient.builder());
        }
    }

    @ConditionalOnMissingBean
    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder.build();
        log.trace("[Herodotus] |- Bean [RestClient] Configure.");
        return restClient;
    }
}
