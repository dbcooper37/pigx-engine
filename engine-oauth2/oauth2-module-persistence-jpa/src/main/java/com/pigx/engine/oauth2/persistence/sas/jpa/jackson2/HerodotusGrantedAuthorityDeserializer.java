package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;

import java.io.IOException;


public class HerodotusGrantedAuthorityDeserializer extends JsonDeserializer<HerodotusGrantedAuthority> {
    @Override
    public HerodotusGrantedAuthority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        String authority = JsonNodeUtils.findStringValue(jsonNode, "authority");
        return new HerodotusGrantedAuthority(authority);
    }
}
