package com.pigx.engine.facility.gateway.autoconfigure;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

@AutoConfiguration
public class FacilityGatewayAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FacilityGatewayAutoConfiguration.class);
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public FacilityGatewayAutoConfiguration(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolvers;
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD-ENGINE] |- Starter [Facility Gateway] Configure.");
    }

    @Bean
    @Order(Integer.MIN_VALUE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(this.viewResolvers, this.serverCodecConfigurer);
    }

    @ConditionalOnMissingBean({SentinelGatewayFilter.class})
    @Bean
    public SentinelGatewayFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }
}
