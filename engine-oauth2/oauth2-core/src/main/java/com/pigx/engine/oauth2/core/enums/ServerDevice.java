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
/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/enums/ServerDevice.class */
public enum ServerDevice implements BaseUiEnum<Integer> {
    PHYSICAL_MACHINE(0, "实体机"),
    VIRTUAL_MACHINE(1, "虚拟机");

    private static final Map<Integer, ServerDevice> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (ServerDevice serverDevice : values()) {
            INDEX_MAP.put(serverDevice.getValue(), serverDevice);
            JSON_STRUCTURE.add(serverDevice.getValue().intValue(), ImmutableMap.builder().put("value", serverDevice.getValue()).put("key", serverDevice.name()).put("text", serverDevice.getDescription()).put("index", serverDevice.getValue()).build());
        }
    }

    ServerDevice(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ServerDevice get(Integer index) {
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
