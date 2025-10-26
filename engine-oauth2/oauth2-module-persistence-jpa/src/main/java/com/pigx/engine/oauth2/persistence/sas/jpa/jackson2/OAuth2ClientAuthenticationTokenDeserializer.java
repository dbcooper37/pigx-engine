package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;
import cn.hutool.v7.core.reflect.FieldUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/OAuth2ClientAuthenticationTokenDeserializer.class */
public class OAuth2ClientAuthenticationTokenDeserializer extends JsonDeserializer<OAuth2ClientAuthenticationToken> {
    private static final TypeReference<Set<HerodotusGrantedAuthority>> HERODOTUS_GRANTED_AUTHORITY_SET = new TypeReference<Set<HerodotusGrantedAuthority>>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2ClientAuthenticationTokenDeserializer.1
    };

    /* renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public OAuth2ClientAuthenticationToken m226deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = (JsonNode) mapper.readTree(jsonParser);
        return deserialize(jsonParser, mapper, jsonNode);
    }

    private OAuth2ClientAuthenticationToken deserialize(JsonParser parser, ObjectMapper mapper, JsonNode root) throws IOException {
        Set<HerodotusGrantedAuthority> authorities = (Set) JsonNodeUtils.findValue(root, SystemConstants.AUTHORITIES, HERODOTUS_GRANTED_AUTHORITY_SET, mapper);
        RegisteredClient registeredClient = (RegisteredClient) JsonNodeUtils.findValue(root, "registeredClient", new TypeReference<RegisteredClient>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2ClientAuthenticationTokenDeserializer.2
        }, mapper);
        String credentials = JsonNodeUtils.findStringValue(root, "credentials");
        ClientAuthenticationMethod clientAuthenticationMethod = (ClientAuthenticationMethod) JsonNodeUtils.findValue(root, "clientAuthenticationMethod", new TypeReference<ClientAuthenticationMethod>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2ClientAuthenticationTokenDeserializer.3
        }, mapper);
        OAuth2ClientAuthenticationToken clientAuthenticationToken = new OAuth2ClientAuthenticationToken(registeredClient, clientAuthenticationMethod, credentials);
        if (CollectionUtils.isNotEmpty(authorities)) {
            FieldUtil.setFieldValue(clientAuthenticationToken, SystemConstants.AUTHORITIES, authorities);
        }
        return clientAuthenticationToken;
    }
}
