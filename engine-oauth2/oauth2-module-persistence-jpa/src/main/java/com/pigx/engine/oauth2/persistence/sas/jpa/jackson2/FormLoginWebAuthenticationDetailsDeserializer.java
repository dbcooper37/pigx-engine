package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;
import com.pigx.engine.oauth2.core.domain.FormLoginWebAuthenticationDetails;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/FormLoginWebAuthenticationDetailsDeserializer.class */
public class FormLoginWebAuthenticationDetailsDeserializer extends JsonDeserializer<FormLoginWebAuthenticationDetails> {
    /* renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public FormLoginWebAuthenticationDetails m222deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        String remoteAddress = JsonNodeUtils.findStringValue(jsonNode, "remoteAddress");
        String sessionId = JsonNodeUtils.findStringValue(jsonNode, "sessionId");
        String parameterName = JsonNodeUtils.findStringValue(jsonNode, "parameterName");
        String category = JsonNodeUtils.findStringValue(jsonNode, "category");
        String code = JsonNodeUtils.findStringValue(jsonNode, SystemConstants.CODE);
        boolean enabled = JsonNodeUtils.findBooleanValue(jsonNode, BaseConstants.PROPERTY_NAME_ENABLED);
        return new FormLoginWebAuthenticationDetails(remoteAddress, sessionId, Boolean.valueOf(enabled), parameterName, category, code);
    }
}
