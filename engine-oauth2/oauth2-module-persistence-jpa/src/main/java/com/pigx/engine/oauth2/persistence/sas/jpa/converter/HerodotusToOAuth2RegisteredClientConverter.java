package com.pigx.engine.oauth2.persistence.sas.jpa.converter;

import com.pigx.engine.oauth2.persistence.sas.jpa.definition.AbstractRegisteredClientConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusRegisteredClient;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import java.util.Map;
import java.util.Set;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/converter/HerodotusToOAuth2RegisteredClientConverter.class */
public class HerodotusToOAuth2RegisteredClientConverter extends AbstractRegisteredClientConverter<HerodotusRegisteredClient> {
    public HerodotusToOAuth2RegisteredClientConverter(OAuth2JacksonProcessor jacksonProcessor) {
        super(jacksonProcessor);
    }

    @Override // com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter
    public Set<String> getScopes(HerodotusRegisteredClient details) {
        return StringUtils.commaDelimitedListToSet(details.getScopes());
    }

    @Override // com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter
    public ClientSettings getClientSettings(HerodotusRegisteredClient details) {
        Map<String, Object> clientSettingsMap = parseMap(details.getClientSettings());
        return ClientSettings.withSettings(clientSettingsMap).build();
    }

    @Override // com.pigx.engine.oauth2.persistence.sas.jpa.definition.RegisteredClientConverter
    public TokenSettings getTokenSettings(HerodotusRegisteredClient details) {
        Map<String, Object> tokenSettingsMap = parseMap(details.getTokenSettings());
        return TokenSettings.withSettings(tokenSettingsMap).build();
    }
}
