package com.pigx.engine.core.identity.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Set;


public class HerodotusUserDeserializer extends JsonDeserializer<HerodotusUser> {

    private static final TypeReference<Set<HerodotusGrantedAuthority>> HERODOTUS_GRANTED_AUTHORITY_SET = new TypeReference<Set<HerodotusGrantedAuthority>>() {
    };
    private static final TypeReference<Set<String>> HERODOTUS_ROLE_SET = new TypeReference<Set<String>>() {
    };

    /**
     * This method will create {@link User} object. It will ensure successful object
     * creation even if password key is null in serialized json, because credentials may
     * be removed from the {@link User} by invoking {@link User#eraseCredentials()}. In
     * that case there won't be any password key in serialized json.
     *
     * @param jp   the JsonParser
     * @param ctxt the DeserializationContext
     * @return the user
     * @throws IOException             if a exception during IO occurs
     * @throws JsonProcessingException if an error during JSON processing occurs
     */
    @Override
    public HerodotusUser deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        Set<? extends GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"), HERODOTUS_GRANTED_AUTHORITY_SET);
        Set<String> roles = mapper.convertValue(jsonNode.get("roles"), HERODOTUS_ROLE_SET);
        JsonNode passwordNode = JsonNodeUtils.readJsonNode(jsonNode, "password");
        String userId = JsonNodeUtils.findStringValue(jsonNode, "userId");
        String username = JsonNodeUtils.findStringValue(jsonNode, "username");
        String password = passwordNode.asText("");
        boolean enabled = JsonNodeUtils.findBooleanValue(jsonNode, "enabled");
        boolean accountNonExpired = JsonNodeUtils.findBooleanValue(jsonNode, "accountNonExpired");
        boolean credentialsNonExpired = JsonNodeUtils.findBooleanValue(jsonNode, "credentialsNonExpired");
        boolean accountNonLocked = JsonNodeUtils.findBooleanValue(jsonNode, "accountNonLocked");
        String employeeId = JsonNodeUtils.findStringValue(jsonNode, "employeeId");
        String avatar = JsonNodeUtils.findStringValue(jsonNode, "avatar");
        HerodotusUser result = new HerodotusUser(userId, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, roles, employeeId, avatar);
        if (passwordNode.asText(null) == null) {
            result.eraseCredentials();
        }
        return result;
    }
}
