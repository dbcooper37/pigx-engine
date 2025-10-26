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

@Schema(name = "机构类别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/enums/OrganizationCategory.class */
public enum OrganizationCategory implements BaseUiEnum<Integer> {
    ENTERPRISE(0, "企业机构"),
    PARTY(1, "党组机构"),
    LEAGUE(2, "团青机构");

    private static final Map<Integer, OrganizationCategory> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (OrganizationCategory organizationCategory : values()) {
            INDEX_MAP.put(organizationCategory.getValue(), organizationCategory);
            JSON_STRUCTURE.add(organizationCategory.getValue().intValue(), ImmutableMap.builder().put("value", organizationCategory.getValue()).put("key", organizationCategory.name()).put("text", organizationCategory.getDescription()).put("index", organizationCategory.getValue()).build());
        }
    }

    OrganizationCategory(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static OrganizationCategory get(Integer index) {
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
