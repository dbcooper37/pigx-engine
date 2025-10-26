package com.pigx.engine.autoconfigure.client.reactive;

import com.pigx.engine.core.definition.utils.NumberUtils;
import com.pigx.engine.core.foundation.condition.ConditionalOnArchitecture;
import com.pigx.engine.core.foundation.enums.Architecture;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@EnableConfigurationProperties({WebClientProperties.class})
@AutoConfiguration
@ConditionalOnClass({WebClient.class})
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/reactive/WebClientAutoConfiguration.class */
public class WebClientAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(WebClientAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [WebClient] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public ReactorResourceFactory reactorResourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(true);
        log.trace("[Herodotus] |- Bean [Reactor Resource Factory] Configure.");
        return factory;
    }

    @Bean
    public Function<HttpClient, HttpClient> httpClientMapper(WebClientProperties webClientProperties) {
        return httpClient -> {
            return httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.valueOf(NumberUtils.longToInt(webClientProperties.getConnectTimeout().toMillis()))).doOnConnected(conn -> {
                conn.addHandlerLast(new ReadTimeoutHandler(NumberUtils.longToInt(webClientProperties.getReadTimeout().toSeconds()))).addHandlerLast(new WriteTimeoutHandler(NumberUtils.longToInt(webClientProperties.getWriteTimeout().toSeconds())));
            });
        };
    }

    @ConditionalOnMissingClass({"de.codecentric.boot.admin.server.config.EnableAdminServer"})
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/reactive/WebClientAutoConfiguration$WebClientBuilderConfiguration.class */
    static class WebClientBuilderConfiguration {
        WebClientBuilderConfiguration() {
        }

        @Bean
        @LoadBalanced
        public WebClient.Builder webClientBuilder(ObjectProvider<WebClientCustomizer> customizerProvider) {
            WebClient.Builder builder = WebClient.builder();
            customizerProvider.orderedStream().forEach(customizer -> {
                customizer.customize(builder);
            });
            WebClientAutoConfiguration.log.trace("[Herodotus] |- Bean [LoadBalanced WebClient Builder] Configure.");
            return builder;
        }
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder, ReactorResourceFactory reactorResourceFactory, Function<HttpClient, HttpClient> mapper) {
        WebClient webClient = webClientBuilder.clientConnector(new ReactorClientHttpConnector(reactorResourceFactory, mapper)).build();
        log.trace("[Herodotus] |- Bean [WebClient] Configure.");
        return webClient;
    }
}
