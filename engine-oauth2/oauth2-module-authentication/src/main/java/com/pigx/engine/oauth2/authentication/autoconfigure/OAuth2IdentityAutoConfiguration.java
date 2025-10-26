package com.pigx.engine.oauth2.authentication.autoconfigure;

import com.pigx.engine.logic.identity.config.LogicIdentityConfiguration;
import com.pigx.engine.oauth2.extension.config.OAuth2ExtensionConfiguration;
import com.pigx.engine.rest.servlet.identity.config.RestServletIdentityConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration(after = {OAuth2AuthenticationAutoConfiguration.class})
@Import({OAuth2ExtensionConfiguration.class, LogicIdentityConfiguration.class, RestServletIdentityConfiguration.class})
/* loaded from: oauth2-authentication-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/autoconfigure/OAuth2IdentityAutoConfiguration.class */
public class OAuth2IdentityAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OAuth2IdentityAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [OAuth2 Identity] Configure.");
    }
}
