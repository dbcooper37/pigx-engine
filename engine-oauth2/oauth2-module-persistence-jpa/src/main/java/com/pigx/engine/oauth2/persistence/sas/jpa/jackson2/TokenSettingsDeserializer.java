package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/TokenSettingsDeserializer.class */
public class TokenSettingsDeserializer extends JsonDeserializer<TokenSettings> {
    /* renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public TokenSettings m230deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        Map<String, Object> settings = (Map) JsonNodeUtils.findValue(jsonNode, "settings", JsonNodeUtils.STRING_OBJECT_MAP, mapper);
        return TokenSettings.withSettings(settings).build();
    }
}
