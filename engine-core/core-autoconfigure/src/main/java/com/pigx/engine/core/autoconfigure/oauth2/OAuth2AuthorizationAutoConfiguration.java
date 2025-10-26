package com.pigx.engine.autoconfigure.oauth2;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.oauth2.core.OAuth2Token;

@EnableConfigurationProperties({OAuth2AuthorizationProperties.class})
@AutoConfiguration
@ConditionalOnClass({OAuth2Token.class})
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/OAuth2AuthorizationAutoConfiguration.class */
public class OAuth2AuthorizationAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [OAuth2 Authorization] Configure.");
    }
}
