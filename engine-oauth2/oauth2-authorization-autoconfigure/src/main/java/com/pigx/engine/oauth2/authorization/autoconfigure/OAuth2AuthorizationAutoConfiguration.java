package com.pigx.engine.oauth2.authorization.autoconfigure;

import com.pigx.engine.core.autoconfigure.oauth2.definition.SecurityGlobalExceptionHandler;
import com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.RemoteAttributeTransmitterSyncListener;
import com.pigx.engine.oauth2.authorization.autoconfigure.strategy.DefaultRestMappingScanEventManager;
import com.pigx.engine.oauth2.authorization.config.OAuth2ServletAuthorizationConfiguration;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@AutoConfiguration
@EnableAsync
@RemoteApplicationEventScan({"com.pigx.engine.oauth2.authorization.autoconfigure.bus"})
@Import({OAuth2ServletAuthorizationConfiguration.class})
@ComponentScan(basePackageClasses = {SecurityGlobalExceptionHandler.class})
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/OAuth2AuthorizationAutoConfiguration.class */
public class OAuth2AuthorizationAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [OAuth2 Resource Server Starter] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public RemoteAttributeTransmitterSyncListener remoteSecurityMetadataSyncListener(SecurityAttributeAnalyzer securityAttributeAnalyzer, ServiceMatcher serviceMatcher) {
        RemoteAttributeTransmitterSyncListener listener = new RemoteAttributeTransmitterSyncListener(securityAttributeAnalyzer, serviceMatcher);
        log.trace("[Herodotus] |- Bean [Security Metadata Refresh Listener] Configure.");
        return listener;
    }

    @ConditionalOnMissingBean
    @Bean
    public RestMappingScanEventManager requestMappingScanEventManager(SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        DefaultRestMappingScanEventManager manager = new DefaultRestMappingScanEventManager(securityAttributeAnalyzer);
        log.trace("[Herodotus] |- Bean [Request Mapping Scan Manager] Configure.");
        return manager;
    }
}
