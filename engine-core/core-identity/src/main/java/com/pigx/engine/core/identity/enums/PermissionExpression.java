package com.pigx.engine.core.identity.enums;

import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(name = "Security 权限表达式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/enums/PermissionExpression.class */
public enum PermissionExpression implements BaseUiEnum<String> {
    PERMIT_ALL("permitAll", "permitAll"),
    ANONYMOUS("isAnonymous", "isAnonymous"),
    REMEMBER_ME("isRememberMe", "isRememberMe"),
    DENY_ALL("denyAll", "denyAll"),
    AUTHENTICATED("isAuthenticated", "isAuthenticated"),
    FULLY_AUTHENTICATED("isFullyAuthenticated", "isFullyAuthenticated");

    private static final Map<String, PermissionExpression> INDEX_MAP = new HashMap();
    private static final List<ImmutableMap<Object, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "索引")
    private final String value;

    @Schema(name = "说明")
    private final String description;

    static {
        for (PermissionExpression permissionExpression : values()) {
            INDEX_MAP.put(permissionExpression.getValue(), permissionExpression);
            JSON_STRUCTURE.add(permissionExpression.ordinal(), ImmutableMap.builder().put("value", permissionExpression.getValue()).put("key", permissionExpression.name()).put("text", permissionExpression.getDescription()).put("index", Integer.valueOf(permissionExpression.ordinal())).build());
        }
    }

    PermissionExpression(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PermissionExpression get(String value) {
        return INDEX_MAP.get(value);
    }

    public static List<ImmutableMap<Object, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumValue
    @JsonValue
    public String getValue() {
        return this.value;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumDescription
    public String getDescription() {
        return this.description;
    }
}
