package com.pigx.engine.logic.identity.enums;

import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Schema(name = "OAuth2 Client 认证方式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/enums/AuthenticationMethod.class */
public enum AuthenticationMethod implements BaseUiEnum<String> {
    CLIENT_SECRET_BASIC(ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue(), "基于Client Secret的Basic验证模式"),
    CLIENT_SECRET_POST(ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue(), "基于Client Secret的Post验证模式"),
    CLIENT_SECRET_JWT(ClientAuthenticationMethod.CLIENT_SECRET_JWT.getValue(), "基于Client Secret的JWT验证模式"),
    PRIVATE_KEY_JWT(ClientAuthenticationMethod.PRIVATE_KEY_JWT.getValue(), "基于私钥的JWT验证模式"),
    NONE(ClientAuthenticationMethod.NONE.getValue(), "不设置任何模式"),
    TLS_CLIENT_AUTH(ClientAuthenticationMethod.TLS_CLIENT_AUTH.getValue(), "TSL 客户端认证"),
    SELF_SIGNED_TLS_CLIENT_AUTH(ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH.getValue(), "自签名 TSL 客户端认证");

    private static final Map<Integer, AuthenticationMethod> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "认证方法")
    private final String value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (AuthenticationMethod authenticationMethod : values()) {
            INDEX_MAP.put(Integer.valueOf(authenticationMethod.ordinal()), authenticationMethod);
            JSON_STRUCTURE.add(authenticationMethod.ordinal(), ImmutableMap.builder().put("value", authenticationMethod.getValue()).put("key", authenticationMethod.name()).put("text", authenticationMethod.getDescription()).put("index", Integer.valueOf(authenticationMethod.ordinal())).build());
        }
    }

    AuthenticationMethod(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static AuthenticationMethod get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumDescription
    public String getDescription() {
        return this.description;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumValue
    public String getValue() {
        return this.value;
    }
}
