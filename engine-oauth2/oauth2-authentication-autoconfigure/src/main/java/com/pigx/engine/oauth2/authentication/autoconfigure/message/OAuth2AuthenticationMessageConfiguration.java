package com.pigx.engine.oauth2.authentication.autoconfigure.message;

import com.pigx.engine.message.core.definition.strategy.AccountStatusChangedEventManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Configuration(proxyBeanMethods = false)
/* loaded from: oauth2-authentication-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/autoconfigure/message/OAuth2AuthenticationMessageConfiguration.class */
public class OAuth2AuthenticationMessageConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationMessageConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Authentication Server Message] Configure.");
    }

    @Bean
    public AccountStatusChangedEventManager accountStatusChangedEventManager() {
        DefaultAccountStatusChangedEventManager manager = new DefaultAccountStatusChangedEventManager();
        log.trace("[Herodotus] |- Bean [Herodotus Account Status Event Manager] Configure.");
        return manager;
    }
}
