package com.pigx.engine.logic.identity.enums;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Schema(name = "OAuth2 认证模式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/enums/GrantType.class */
public enum GrantType implements BaseUiEnum<String> {
    AUTHORIZATION_CODE(AuthorizationGrantType.AUTHORIZATION_CODE.getValue(), "Authorization Code 模式"),
    CLIENT_CREDENTIALS(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue(), "Client Credentials 模式"),
    DEVICE_CODE(AuthorizationGrantType.DEVICE_CODE.getValue(), "Device Code 模式"),
    REFRESH_TOKEN(AuthorizationGrantType.REFRESH_TOKEN.getValue(), "Refresh Token 模式"),
    JWT_BEARER(AuthorizationGrantType.JWT_BEARER.getValue(), "JWT Token 模式"),
    TOKEN_EXCHANGE(AuthorizationGrantType.TOKEN_EXCHANGE.getValue(), "Token Exchange 模式"),
    PASSWORD(SystemConstants.PASSWORD, "Password 模式"),
    SOCIAL_CREDENTIALS(SystemConstants.SOCIAL_CREDENTIALS, "Social Credentials 模式");

    private static final Map<Integer, GrantType> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "认证模式")
    private final String value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (GrantType grantType : values()) {
            INDEX_MAP.put(Integer.valueOf(grantType.ordinal()), grantType);
            JSON_STRUCTURE.add(grantType.ordinal(), ImmutableMap.builder().put("value", grantType.getValue()).put("key", grantType.name()).put("text", grantType.getDescription()).put("index", Integer.valueOf(grantType.ordinal())).build());
        }
    }

    GrantType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static GrantType get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumValue
    public String getValue() {
        return this.value;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumDescription
    public String getDescription() {
        return this.description;
    }
}
