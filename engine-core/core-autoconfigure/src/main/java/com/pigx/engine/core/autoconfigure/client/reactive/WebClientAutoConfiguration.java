package com.pigx.engine.core.autoconfigure.client.reactive;

import com.pigx.engine.core.definition.utils.NumberUtils;
import com.pigx.engine.core.foundation.condition.ConditionalOnArchitecture;
import com.pigx.engine.core.foundation.enums.Architecture;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
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
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.function.Function;


@AutoConfiguration
@ConditionalOnClass(WebClient.class)
@EnableConfigurationProperties(WebClientProperties.class)
public class WebClientAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebClientAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [WebClient] Configure.");
    }

    /**
     * 增加个 {@link ReactorResourceFactory} 配置。方便配置使用。
     *
     * @return {@link ReactorResourceFactory}
     */
    @Bean
    @ConditionalOnMissingBean
    public ReactorResourceFactory reactorResourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(true);
        log.trace("[PIGXD] |- Bean [Reactor Resource Factory] Configure.");
        return factory;
    }

    @Bean
    public Function<HttpClient, HttpClient> httpClientMapper(WebClientProperties webClientProperties) {
        return httpClient -> httpClient
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, NumberUtils.longToInt(webClientProperties.getConnectTimeout().toMillis()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(NumberUtils.longToInt(webClientProperties.getReadTimeout().toSeconds())))
                        .addHandlerLast(new WriteTimeoutHandler(NumberUtils.longToInt(webClientProperties.getWriteTimeout().toSeconds()))));
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder, ReactorResourceFactory reactorResourceFactory, Function<HttpClient, HttpClient> mapper) {
        ClientHttpConnector connector = new ReactorClientHttpConnector(reactorResourceFactory, mapper);
        WebClient webClient = webClientBuilder.clientConnector(connector).build();
        log.trace("[PIGXD] |- Bean [WebClient] Configure.");
        return webClient;
    }

    /**
     * 在 WebClient.Builder Bean上配置了@LoadBalanced 才会开启 WebClient 负载均衡
     * <p>
     * 如果在非服务发现的环境下，引入了本包。这种情况下，如果给 WebClient.Builder Bean 配置了 @LoadBalanced 会出现 503 错误。因为 @LoadBalanced 是通过服务发现，根据服务名进行调用的。
     * 所以，增加了 @ConditionalOnArchitecture 条件，即在
     * · 分布式环境下才开启负载均衡，因为作为服务都需要读取配置中心配置，目前大多数读取配置都需要使用 bootstrap
     * · 单体环境下使用默认的 WebClient Builder {@link org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration}
     *
     * <code>org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerWebClientBuilderBeanPostProcessor</code>
     * <p>
     * 在 Spring Boot Admin 环境下，如果开启了 @LoadBalanced 会导致响应式服务无法连接到 Spring Boot Admin
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    @ConditionalOnMissingClass({"de.codecentric.boot.admin.server.config.EnableAdminServer"})
    static class WebClientBuilderConfiguration {

        @Bean
        @LoadBalanced
        public WebClient.Builder webClientBuilder(ObjectProvider<WebClientCustomizer> customizerProvider) {
            WebClient.Builder builder = WebClient.builder();
            customizerProvider.orderedStream().forEach((customizer) -> customizer.customize(builder));
            log.trace("[PIGXD] |- Bean [LoadBalanced WebClient Builder] Configure.");
            return builder;
        }
    }
}
