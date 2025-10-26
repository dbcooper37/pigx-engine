package com.pigx.engine.oauth2.extension.config;

import com.pigx.engine.oauth2.extension.condition.ConditionalOnAutoUnlockAccount;
import com.pigx.engine.oauth2.extension.listener.AutoUnlockAccountListener;
import com.pigx.engine.oauth2.extension.listener.SignOutComplianceListener;
import com.pigx.engine.oauth2.extension.manager.OAuth2ComplianceManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.pigx.engine.oauth2.extension.stamp", "com.pigx.engine.oauth2.extension.manager"})
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/config/OAuth2ComplianceConfiguration.class */
public class OAuth2ComplianceConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2ComplianceConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Extension] Configure.");
    }

    @ConditionalOnAutoUnlockAccount
    @Bean
    public AutoUnlockAccountListener autoUnlockAccountListener(RedisMessageListenerContainer redisMessageListenerContainer, OAuth2ComplianceManager oauth2ComplianceManager) {
        AutoUnlockAccountListener listener = new AutoUnlockAccountListener(redisMessageListenerContainer, oauth2ComplianceManager);
        log.trace("[Herodotus] |- Bean [OAuth2 Account Lock Status Listener] Configure.");
        return listener;
    }

    @ConditionalOnMissingBean
    @Bean
    public SignOutComplianceListener signOutComplianceListener(OAuth2ComplianceManager complianceManager) {
        SignOutComplianceListener listener = new SignOutComplianceListener(complianceManager);
        log.trace("[Herodotus] |- Bean [Sign Out Compliance Listener] Configure.");
        return listener;
    }
}
