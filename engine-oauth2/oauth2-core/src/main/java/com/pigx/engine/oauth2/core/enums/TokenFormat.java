package com.pigx.engine.oauth2.core.enums;

import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(name = "令牌格式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/enums/TokenFormat.class */
public enum TokenFormat implements BaseUiEnum<Integer> {
    SELF_CONTAINED(0, "self-contained", "自包含格式令牌"),
    REFERENCE(1, "reference", "引用（不透明）令牌");

    private static final Map<String, TokenFormat> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "格式")
    private final String format;

    @Schema(name = "文字")
    private final String description;

    static {
        for (TokenFormat tokenFormat : values()) {
            INDEX_MAP.put(tokenFormat.getFormat(), tokenFormat);
            JSON_STRUCTURE.add(tokenFormat.getValue().intValue(), ImmutableMap.builder().put("value", tokenFormat.getValue()).put("key", tokenFormat.name()).put("text", tokenFormat.getDescription()).put("format", tokenFormat.getFormat()).put("index", Integer.valueOf(tokenFormat.ordinal())).build());
        }
    }

    TokenFormat(Integer value, String method, String description) {
        this.value = value;
        this.format = method;
        this.description = description;
    }

    public static TokenFormat get(String format) {
        return INDEX_MAP.get(format);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumValue
    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getFormat() {
        return this.format;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumDescription
    public String getDescription() {
        return this.description;
    }
}
