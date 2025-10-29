package com.pigx.engine.oauth2.authentication.autoconfigure.message;

import com.pigx.engine.message.core.definition.strategy.AccountStatusChangedEventManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class OAuth2AuthenticationMessageConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationMessageConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Authentication Server Message] Configure.");
    }

    @Bean
    public AccountStatusChangedEventManager accountStatusChangedEventManager() {
        DefaultAccountStatusChangedEventManager manager = new DefaultAccountStatusChangedEventManager();
        log.trace("[PIGXD] |- Bean [Herodotus Account Status Event Manager] Configure.");
        return manager;
    }
}
