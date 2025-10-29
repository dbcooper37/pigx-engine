package com.pigx.engine.oauth2.authorization.autoconfigure;

import com.pigx.engine.core.foundation.condition.ConditionalOnArchitecture;
import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import com.pigx.engine.core.foundation.enums.Architecture;
import com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.RemoteAttributeTransmitterSyncListener;
import com.pigx.engine.oauth2.authorization.autoconfigure.strategy.DefaultRestMappingScanEventManager;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.StreamBusBridge;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@AutoConfiguration
public class OAuth2AuthorizationMessageAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationMessageAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [OAuth2 Authorization Message] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnServletApplication
    public RestMappingScanEventManager servletRequestMappingScanEventManager(SecurityAttributeAnalyzer analyzer) {
        DefaultRestMappingScanEventManager manager = new DefaultRestMappingScanEventManager(analyzer);
        log.trace("[PIGXD] |- Bean [Servlet Request Mapping Scan Manager] Configure.");
        return manager;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(StreamBusBridge.class)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    @RemoteApplicationEventScan({
            "com.pigx.engine.oauth2.authorization.autoconfigure.bus"
    })
    static class BusMessageConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RemoteAttributeTransmitterSyncListener remoteSecurityMetadataSyncListener(SecurityAttributeAnalyzer analyzer, ServiceMatcher serviceMatcher) {
            RemoteAttributeTransmitterSyncListener listener = new RemoteAttributeTransmitterSyncListener(analyzer, serviceMatcher);
            log.trace("[PIGXD] |- Bean [Security Metadata Refresh Listener] Configure.");
            return listener;
        }
    }
}
