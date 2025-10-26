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

/* JADX WARN: Classes with same name are omitted:
  
 */
@AutoConfiguration
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/OAuth2AuthorizationMessageAutoConfiguration.class */
public class OAuth2AuthorizationMessageAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationMessageAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [OAuth2 Authorization Message] Configure.");
    }

    @ConditionalOnMissingBean
    @ConditionalOnServletApplication
    @Bean
    public RestMappingScanEventManager servletRequestMappingScanEventManager(SecurityAttributeAnalyzer analyzer) {
        DefaultRestMappingScanEventManager manager = new DefaultRestMappingScanEventManager(analyzer);
        log.trace("[Herodotus] |- Bean [Servlet Request Mapping Scan Manager] Configure.");
        return manager;
    }

    /* JADX WARN: Classes with same name are omitted:
  
 */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({StreamBusBridge.class})
    @RemoteApplicationEventScan({"com.pigx.engine.oauth2.authorization.autoconfigure.bus"})
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    /* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/OAuth2AuthorizationMessageAutoConfiguration$BusMessageConfiguration.class */
    static class BusMessageConfiguration {
        BusMessageConfiguration() {
        }

        @ConditionalOnMissingBean
        @Bean
        public RemoteAttributeTransmitterSyncListener remoteSecurityMetadataSyncListener(SecurityAttributeAnalyzer analyzer, ServiceMatcher serviceMatcher) {
            RemoteAttributeTransmitterSyncListener listener = new RemoteAttributeTransmitterSyncListener(analyzer, serviceMatcher);
            OAuth2AuthorizationMessageAutoConfiguration.log.trace("[Herodotus] |- Bean [Security Metadata Refresh Listener] Configure.");
            return listener;
        }
    }
}
