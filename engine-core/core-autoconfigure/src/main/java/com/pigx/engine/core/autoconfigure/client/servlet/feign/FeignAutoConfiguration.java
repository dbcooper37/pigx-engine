package com.pigx.engine.autoconfigure.client.servlet.feign;

import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;

@AutoConfiguration
@ConditionalOnClass({Feign.class})
@ConditionalOnServletApplication
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/feign/FeignAutoConfiguration.class */
public class FeignAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FeignAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Feign] Configure.");
    }

    @Bean
    public Contract feignContract(List<AnnotatedParameterProcessor> parameterProcessors, ConversionService feignConversionService, FeignClientProperties feignClientProperties) {
        log.info("[Herodotus] |- Bean [Herodotus FeignInnerContract] Configure.");
        return new FeignInnerContract(parameterProcessors, feignConversionService, feignClientProperties);
    }

    @ConditionalOnMissingBean({FeignRequestInterceptor.class})
    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        FeignRequestInterceptor feignRequestInterceptor = new FeignRequestInterceptor();
        log.trace("[Herodotus] |- Bean [Feign Request Interceptor] Configure.");
        return feignRequestInterceptor;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Logger.Level logger() {
        return Logger.Level.BASIC;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(10L, TimeUnit.SECONDS, 60L, TimeUnit.SECONDS, true);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
