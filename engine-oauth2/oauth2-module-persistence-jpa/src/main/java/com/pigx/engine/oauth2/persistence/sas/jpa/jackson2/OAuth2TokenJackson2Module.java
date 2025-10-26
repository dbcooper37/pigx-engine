package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/OAuth2TokenJackson2Module.class */
public class OAuth2TokenJackson2Module extends SimpleModule {
    public OAuth2TokenJackson2Module() {
        super(OAuth2TokenJackson2Module.class.getName(), Jackson2Constants.VERSION);
    }

    public void setupModule(Module.SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(ClientAuthenticationMethod.class, ClientAuthenticationMethodMixin.class);
        context.setMixInAnnotations(AuthorizationGrantType.class, AuthorizationGrantTypeMixin.class);
        context.setMixInAnnotations(TokenSettings.class, TokenSettingsMixin.class);
        context.setMixInAnnotations(ClientSettings.class, ClientSettingsMixin.class);
        context.setMixInAnnotations(RegisteredClient.class, RegisteredClientMixin.class);
        context.setMixInAnnotations(OAuth2ClientAuthenticationToken.class, OAuth2ClientAuthenticationTokenMixin.class);
    }
}
