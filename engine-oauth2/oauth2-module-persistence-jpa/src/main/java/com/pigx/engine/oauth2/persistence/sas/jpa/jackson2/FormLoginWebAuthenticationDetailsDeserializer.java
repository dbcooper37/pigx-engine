package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;
import com.pigx.engine.oauth2.core.domain.FormLoginWebAuthenticationDetails;

import java.io.IOException;


public class FormLoginWebAuthenticationDetailsDeserializer extends JsonDeserializer<FormLoginWebAuthenticationDetails> {
    @Override
    public FormLoginWebAuthenticationDetails deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        String remoteAddress = JsonNodeUtils.findStringValue(jsonNode, "remoteAddress");
        String sessionId = JsonNodeUtils.findStringValue(jsonNode, "sessionId");
        String parameterName = JsonNodeUtils.findStringValue(jsonNode, "parameterName");
        String category = JsonNodeUtils.findStringValue(jsonNode, "category");
        String code = JsonNodeUtils.findStringValue(jsonNode, "code");
        boolean enabled = JsonNodeUtils.findBooleanValue(jsonNode, "enabled");

        return new FormLoginWebAuthenticationDetails(remoteAddress, sessionId, enabled, parameterName, category, code);
    }
}
