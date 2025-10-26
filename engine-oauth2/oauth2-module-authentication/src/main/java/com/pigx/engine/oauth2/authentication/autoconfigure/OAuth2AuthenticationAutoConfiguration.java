package com.pigx.engine.oauth2.authentication.autoconfigure;

import com.pigx.engine.oauth2.authentication.autoconfigure.message.OAuth2AuthenticationMessageConfiguration;
import com.pigx.engine.oauth2.authentication.config.OAuth2AuthenticationConfiguration;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.oauth2.persistence.sas.jpa.config.OAuth2PersistenceSasJpaConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties({OAuth2AuthenticationProperties.class})
@AutoConfiguration
@Import({OAuth2PersistenceSasJpaConfiguration.class, OAuth2AuthenticationConfiguration.class, OAuth2AuthenticationMessageConfiguration.class})
/* loaded from: oauth2-authentication-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/autoconfigure/OAuth2AuthenticationAutoConfiguration.class */
public class OAuth2AuthenticationAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [OAuth2 Authentication] Configure.");
    }
}
