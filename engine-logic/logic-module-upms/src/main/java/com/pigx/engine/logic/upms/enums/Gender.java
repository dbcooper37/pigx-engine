package com.pigx.engine.logic.upms.enums;

import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(name = "性别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/enums/Gender.class */
public enum Gender implements BaseUiEnum<Integer> {
    MAN(0, "男"),
    WOMAN(1, "女"),
    OTHERS(2, "其它");

    private static final Map<Integer, Gender> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (Gender gender : values()) {
            INDEX_MAP.put(gender.getValue(), gender);
            JSON_STRUCTURE.add(gender.getValue().intValue(), ImmutableMap.builder().put("value", gender.getValue()).put("key", gender.name()).put("text", gender.getDescription()).put("index", gender.getValue()).build());
        }
    }

    Gender(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Gender get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumValue
    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumDescription
    public String getDescription() {
        return this.description;
    }
}
