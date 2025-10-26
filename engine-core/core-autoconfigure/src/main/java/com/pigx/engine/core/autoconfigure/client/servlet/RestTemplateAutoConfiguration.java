package com.pigx.engine.autoconfigure.client.servlet;

import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@AutoConfiguration(after = {org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.class})
@ConditionalOnServletApplication
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/RestTemplateAutoConfiguration.class */
public class RestTemplateAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [RestTemplate] Configure.");
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() { // from class: com.pigx.engine.core.autoconfigure.client.servlet.RestTemplateAutoConfiguration.1
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return true;
            }
        };
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(responseErrorHandler).build();
        log.trace("[Herodotus] |- Bean [LoadBalanced Rest Template] Configure.");
        return restTemplate;
    }
}
