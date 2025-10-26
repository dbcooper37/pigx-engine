package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.pigx.engine.core.identity.jackson2.JsonNodeUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;
import java.util.Set;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/RegisteredClientDeserializer.class */
public class RegisteredClientDeserializer extends JsonDeserializer<RegisteredClient> {
    private static final TypeReference<Set<ClientAuthenticationMethod>> CLIENT_AUTHENTICATION_METHOD_SET = new TypeReference<Set<ClientAuthenticationMethod>>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.RegisteredClientDeserializer.1
    };
    private static final TypeReference<Set<AuthorizationGrantType>> AUTHORIZATION_GRANT_TYPE_SET = new TypeReference<Set<AuthorizationGrantType>>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.RegisteredClientDeserializer.2
    };

    /* renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public RegisteredClient m229deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = (JsonNode) mapper.readTree(jsonParser);
        return deserialize(jsonParser, mapper, root);
    }

    private RegisteredClient deserialize(JsonParser parser, ObjectMapper mapper, JsonNode root) throws IOException {
        String id = JsonNodeUtils.findStringValue(root, "id");
        String clientId = JsonNodeUtils.findStringValue(root, "clientId");
        Instant clientIdIssuedAt = (Instant) JsonNodeUtils.findValue(root, "clientIdIssuedAt", JsonNodeUtils.INSTANT, mapper);
        String clientSecret = JsonNodeUtils.findStringValue(root, "clientSecret");
        Instant clientSecretExpiresAt = (Instant) JsonNodeUtils.findValue(root, "clientSecretExpiresAt", JsonNodeUtils.INSTANT, mapper);
        String clientName = JsonNodeUtils.findStringValue(root, "clientName");
        Set<ClientAuthenticationMethod> clientAuthenticationMethods = (Set) JsonNodeUtils.findValue(root, "clientAuthenticationMethods", CLIENT_AUTHENTICATION_METHOD_SET, mapper);
        Set<AuthorizationGrantType> authorizationGrantTypes = (Set) JsonNodeUtils.findValue(root, "authorizationGrantTypes", AUTHORIZATION_GRANT_TYPE_SET, mapper);
        Set<String> redirectUris = (Set) JsonNodeUtils.findValue(root, "redirectUris", JsonNodeUtils.STRING_SET, mapper);
        Set<String> postLogoutRedirectUris = (Set) JsonNodeUtils.findValue(root, "postLogoutRedirectUris", JsonNodeUtils.STRING_SET, mapper);
        Set<String> scopes = (Set) JsonNodeUtils.findValue(root, "scopes", JsonNodeUtils.STRING_SET, mapper);
        ClientSettings clientSettings = (ClientSettings) JsonNodeUtils.findValue(root, "clientSettings", new TypeReference<ClientSettings>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.RegisteredClientDeserializer.3
        }, mapper);
        TokenSettings tokenSettings = (TokenSettings) JsonNodeUtils.findValue(root, "tokenSettings", new TypeReference<TokenSettings>() { // from class: com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.RegisteredClientDeserializer.4
        }, mapper);
        return RegisteredClient.withId(id).clientId(clientId).clientIdIssuedAt(clientIdIssuedAt).clientSecret(clientSecret).clientSecretExpiresAt(clientSecretExpiresAt).clientName(clientName).clientAuthenticationMethods(methods -> {
            methods.addAll(clientAuthenticationMethods);
        }).authorizationGrantTypes(types -> {
            types.addAll(authorizationGrantTypes);
        }).redirectUris(uris -> {
            uris.addAll(redirectUris);
        }).postLogoutRedirectUris(uris2 -> {
            uris2.addAll(postLogoutRedirectUris);
        }).scopes(s -> {
            s.addAll(scopes);
        }).clientSettings(clientSettings).tokenSettings(tokenSettings).build();
    }
}
