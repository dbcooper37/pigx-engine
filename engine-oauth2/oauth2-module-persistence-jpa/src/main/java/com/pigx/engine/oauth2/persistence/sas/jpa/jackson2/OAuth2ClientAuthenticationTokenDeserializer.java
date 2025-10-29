package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import cn.hutool.v7.core.reflect.FieldUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.IOException;
import java.util.Set;


public class OAuth2ClientAuthenticationTokenDeserializer extends JsonDeserializer<OAuth2ClientAuthenticationToken> {

    private static final TypeReference<Set<HerodotusGrantedAuthority>> HERODOTUS_GRANTED_AUTHORITY_SET = new TypeReference<Set<HerodotusGrantedAuthority>>() {
    };

    @Override
    public OAuth2ClientAuthenticationToken deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        return deserialize(jsonParser, mapper, jsonNode);
    }

    private OAuth2ClientAuthenticationToken deserialize(JsonParser parser, ObjectMapper mapper, JsonNode root) throws IOException {
        Set<HerodotusGrantedAuthority> authorities = JsonNodeUtils.findValue(root, "authorities", HERODOTUS_GRANTED_AUTHORITY_SET, mapper);
        RegisteredClient registeredClient = JsonNodeUtils.findValue(root, "registeredClient", new TypeReference<RegisteredClient>() {
        }, mapper);
        String credentials = JsonNodeUtils.findStringValue(root, "credentials");
        ClientAuthenticationMethod clientAuthenticationMethod = JsonNodeUtils.findValue(root, "clientAuthenticationMethod", new TypeReference<ClientAuthenticationMethod>() {
        }, mapper);

        OAuth2ClientAuthenticationToken clientAuthenticationToken = new OAuth2ClientAuthenticationToken(registeredClient, clientAuthenticationMethod, credentials);
        if (CollectionUtils.isNotEmpty(authorities)) {
            FieldUtil.setFieldValue(clientAuthenticationToken, "authorities", authorities);
        }
        return clientAuthenticationToken;
    }
}
