package com.pigx.engine.logic.identity.enums;

import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(name = "应用类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/enums/ApplicationType.class */
public enum ApplicationType implements BaseUiEnum<Integer> {
    WEB(0, "PC网页应用"),
    SERVICE(1, "服务应用"),
    APP(2, "手机APP应用"),
    WAP(3, "手机网页应用"),
    MINI(4, "小程序应用"),
    IOT(5, "物联网应用");

    private static final Map<Integer, ApplicationType> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCT = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (ApplicationType applicationType : values()) {
            INDEX_MAP.put(applicationType.getValue(), applicationType);
            JSON_STRUCT.add(applicationType.getValue().intValue(), ImmutableMap.builder().put("value", applicationType.getValue()).put("key", applicationType.name()).put("text", applicationType.getDescription()).put("index", applicationType.getValue()).build());
        }
    }

    ApplicationType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ApplicationType get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCT;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumDescription
    public String getDescription() {
        return this.description;
    }

    @Override // com.pigx.engine.core.definition.enums.EnumValue
    @JsonValue
    public Integer getValue() {
        return this.value;
    }
}
