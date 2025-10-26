package com.pigx.engine.oauth2.authorization.autoconfigure;

import com.pigx.engine.core.foundation.condition.ConditionalOnArchitecture;
import com.pigx.engine.core.foundation.enums.Architecture;
import com.pigx.engine.logic.upms.annotation.EnableHerodotusLogicUpms;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import com.pigx.engine.oauth2.authorization.autoconfigure.condition.ConditionalOnUpmsService;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.LocalAccountStatusChangedListener;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.LocalRestMappingGatherListener;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.RemoteAccountStatusChangedListener;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.RemoteRestMappingGatherListener;
import com.pigx.engine.oauth2.authorization.autoconfigure.listener.SysAttributeChangeListener;
import com.pigx.engine.oauth2.authorization.autoconfigure.processor.AttributeTransmitterDistributeProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* JADX WARN: Classes with same name are omitted:
  
 */
@ConditionalOnUpmsService
@AutoConfiguration
@EnableHerodotusLogicUpms
@ComponentScan(basePackages = {"com.pigx.engine.oauth2.authorization.autoconfigure.processor"})
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/OAuth2UpmsServiceAutoConfiguration.class */
public class OAuth2UpmsServiceAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2UpmsServiceAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [OAuth2 Upms Service] Configure.");
    }

    /* JADX WARN: Classes with same name are omitted:
  
 */
    @Configuration(proxyBeanMethods = false)
    /* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/OAuth2UpmsServiceAutoConfiguration$UpmsLocalListenerConfiguration.class */
    public static class UpmsLocalListenerConfiguration {
        @ConditionalOnMissingBean
        @Bean
        public LocalAccountStatusChangedListener localAccountStatusChangedListener(SysUserService sysUserService) {
            LocalAccountStatusChangedListener listener = new LocalAccountStatusChangedListener(sysUserService);
            OAuth2UpmsServiceAutoConfiguration.log.trace("[Herodotus] |- Bean [Local Account Status Changed Listener] Configure.");
            return listener;
        }

        @ConditionalOnMissingBean
        @Bean
        public LocalRestMappingGatherListener localRequestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
            LocalRestMappingGatherListener listener = new LocalRestMappingGatherListener(attributeTransmitterDistributeProcessor);
            OAuth2UpmsServiceAutoConfiguration.log.trace("[Herodotus] |- Bean [Local Request Mapping Gather Listener] Configure.");
            return listener;
        }

        @ConditionalOnMissingBean
        @Bean
        public SysAttributeChangeListener sysAttributeChangeListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
            SysAttributeChangeListener listener = new SysAttributeChangeListener(attributeTransmitterDistributeProcessor);
            OAuth2UpmsServiceAutoConfiguration.log.trace("[Herodotus] |- Bean [SysAttribute Change Listener] Configure.");
            return listener;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  
 */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    /* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/OAuth2UpmsServiceAutoConfiguration$UpmsRemoteListenerConfiguration.class */
    public static class UpmsRemoteListenerConfiguration {
        @ConditionalOnMissingBean
        @Bean
        public RemoteAccountStatusChangedListener remoteAccountStatusChangedListener(SysUserService sysUserService) {
            RemoteAccountStatusChangedListener listener = new RemoteAccountStatusChangedListener(sysUserService);
            OAuth2UpmsServiceAutoConfiguration.log.trace("[Herodotus] |- Bean [Remote Account Status Changed Listener] Configure.");
            return listener;
        }

        @ConditionalOnMissingBean
        @Bean
        public RemoteRestMappingGatherListener remoteRequestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
            RemoteRestMappingGatherListener listener = new RemoteRestMappingGatherListener(attributeTransmitterDistributeProcessor);
            OAuth2UpmsServiceAutoConfiguration.log.trace("[Herodotus] |- Bean [Remote Request Mapping Gather Listener] Configure.");
            return listener;
        }
    }
}
