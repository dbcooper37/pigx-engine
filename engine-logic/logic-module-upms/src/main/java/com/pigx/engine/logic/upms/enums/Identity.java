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

@Schema(name = "人员身份")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/enums/Identity.class */
public enum Identity implements BaseUiEnum<Integer> {
    STAFF(0, "员工"),
    SECTION_LEADER(1, "部门负责人"),
    LEADERSHIP(2, "领导");

    private static final Map<Integer, Identity> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "索引")
    private final Integer value;

    @Schema(name = "文字")
    private String description;

    static {
        for (Identity identity : values()) {
            INDEX_MAP.put(identity.getValue(), identity);
            JSON_STRUCTURE.add(identity.getValue().intValue(), ImmutableMap.builder().put("value", identity.getValue()).put("key", identity.name()).put("text", identity.getDescription()).put("index", identity.getValue()).build());
        }
    }

    Identity(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Identity get(Integer index) {
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
