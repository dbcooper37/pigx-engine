package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/HerodotusGrantedAuthorityDeserializer.class */
public class HerodotusGrantedAuthorityDeserializer extends JsonDeserializer<HerodotusGrantedAuthority> {
    /* renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public HerodotusGrantedAuthority m223deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        String authority = JsonNodeUtils.findStringValue(jsonNode, "authority");
        return new HerodotusGrantedAuthority(authority);
    }
}
