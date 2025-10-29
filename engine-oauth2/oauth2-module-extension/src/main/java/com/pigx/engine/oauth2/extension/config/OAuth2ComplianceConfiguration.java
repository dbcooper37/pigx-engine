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
@ComponentScan(basePackages = {
        "com.pigx.engine.oauth2.extension.stamp",
        "com.pigx.engine.oauth2.extension.manager",
})
public class OAuth2ComplianceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ComplianceConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [OAuth2 Extension] Configure.");
    }

    @Bean
    @ConditionalOnAutoUnlockAccount
    public AutoUnlockAccountListener autoUnlockAccountListener(RedisMessageListenerContainer redisMessageListenerContainer, OAuth2ComplianceManager oauth2ComplianceManager) {
        AutoUnlockAccountListener listener = new AutoUnlockAccountListener(redisMessageListenerContainer, oauth2ComplianceManager);
        log.trace("[PIGXD] |- Bean [OAuth2 Account Lock Status Listener] Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public SignOutComplianceListener signOutComplianceListener(OAuth2ComplianceManager complianceManager) {
        SignOutComplianceListener listener = new SignOutComplianceListener(complianceManager);
        log.trace("[PIGXD] |- Bean [Sign Out Compliance Listener] Configure.");
        return listener;
    }
}
