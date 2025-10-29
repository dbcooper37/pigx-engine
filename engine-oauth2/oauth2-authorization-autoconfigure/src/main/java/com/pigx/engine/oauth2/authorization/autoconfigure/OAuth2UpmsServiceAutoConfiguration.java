package com.pigx.engine.oauth2.authorization.autoconfigure;

import com.pigx.engine.core.foundation.condition.ConditionalOnArchitecture;
import com.pigx.engine.core.foundation.enums.Architecture;
import com.pigx.engine.logic.upms.annotation.EnableHerodotusLogicUpms;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import com.pigx.engine.oauth2.authorization.autoconfigure.condition.ConditionalOnUpmsService;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.*;
import com.pigx.engine.oauth2.authorization.autoconfigure.processor.AttributeTransmitterDistributeProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@AutoConfiguration
@ConditionalOnUpmsService
@EnableHerodotusLogicUpms
@ComponentScan(basePackages = {
        "com.pigx.engine.oauth2.authorization.autoconfigure.processor",
})
public class OAuth2UpmsServiceAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2UpmsServiceAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [OAuth2 Upms Service] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    public static class UpmsLocalListenerConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public LocalAccountStatusChangedListener localAccountStatusChangedListener(SysUserService sysUserService) {
            LocalAccountStatusChangedListener listener = new LocalAccountStatusChangedListener(sysUserService);
            log.trace("[PIGXD] |- Bean [Local Account Status Changed Listener] Configure.");
            return listener;
        }

        @Bean
        @ConditionalOnMissingBean
        public LocalRestMappingGatherListener localRequestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
            LocalRestMappingGatherListener listener = new LocalRestMappingGatherListener(attributeTransmitterDistributeProcessor);
            log.trace("[PIGXD] |- Bean [Local Request Mapping Gather Listener] Configure.");
            return listener;
        }

        @Bean
        @ConditionalOnMissingBean
        public SysAttributeChangeListener sysAttributeChangeListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
            SysAttributeChangeListener listener = new SysAttributeChangeListener(attributeTransmitterDistributeProcessor);
            log.trace("[PIGXD] |- Bean [SysAttribute Change Listener] Configure.");
            return listener;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    public static class UpmsRemoteListenerConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RemoteAccountStatusChangedListener remoteAccountStatusChangedListener(SysUserService sysUserService) {
            RemoteAccountStatusChangedListener listener = new RemoteAccountStatusChangedListener(sysUserService);
            log.trace("[PIGXD] |- Bean [Remote Account Status Changed Listener] Configure.");
            return listener;
        }

        @Bean
        @ConditionalOnMissingBean
        public RemoteRestMappingGatherListener remoteRequestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
            RemoteRestMappingGatherListener listener = new RemoteRestMappingGatherListener(attributeTransmitterDistributeProcessor);
            log.trace("[PIGXD] |- Bean [Remote Request Mapping Gather Listener] Configure.");
            return listener;
        }
    }
}
