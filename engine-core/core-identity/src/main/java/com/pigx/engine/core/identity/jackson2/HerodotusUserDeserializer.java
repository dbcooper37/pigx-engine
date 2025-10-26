package com.pigx.engine.core.identity.jackson2;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public class HerodotusUserDeserializer extends JsonDeserializer<HerodotusUser> {
    private static final TypeReference<Set<HerodotusGrantedAuthority>> HERODOTUS_GRANTED_AUTHORITY_SET = new TypeReference<Set<HerodotusGrantedAuthority>>() {
    };
    private static final TypeReference<Set<String>> HERODOTUS_ROLE_SET = new TypeReference<Set<String>>() {
    };

    public HerodotusUser deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)jp.getCodec();
        JsonNode jsonNode = (JsonNode)mapper.readTree(jp);
        Set<? extends GrantedAuthority> authorities = (Set)mapper.convertValue(jsonNode.get("authorities"), HERODOTUS_GRANTED_AUTHORITY_SET);
        Set<String> roles = (Set)mapper.convertValue(jsonNode.get("roles"), HERODOTUS_ROLE_SET);
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
        if (passwordNode.asText((String)null) == null) {
            result.eraseCredentials();
        }

        return result;
    }
}
