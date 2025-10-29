package com.pigx.engine.oss.solution.config;

import com.pigx.engine.oss.solution.properties.OssProxyProperties;
import com.pigx.engine.oss.solution.proxy.OssPresignedUrlProxy;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OssProxyProperties.class)
@ComponentScan(basePackages = {
        "com.pigx.engine.oss.solution.service",
})
public class OssSolutionConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssSolutionConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Oss Solution] Configure.");
    }

    @Bean
    public OssPresignedUrlProxy ossPresignedUrlProxy(OssProxyProperties ossProxyProperties) {
        OssPresignedUrlProxy ossPresignedUrlProxy = new OssPresignedUrlProxy(ossProxyProperties);
        log.trace("[Herodotus] |- Bean [Oss Presigned Url Proxy] Configure.");
        return ossPresignedUrlProxy;
    }
}
