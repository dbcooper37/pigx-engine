package com.pigx.engine.data.core.enums;

import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(name = "数据状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: data-core-3.5.7.0.jar:cn/herodotus/engine/data/core/enums/DataItemStatus.class */
public enum DataItemStatus implements BaseUiEnum<Integer> {
    ENABLE(0, "启用"),
    FORBIDDEN(1, "禁用"),
    LOCKING(2, "锁定"),
    EXPIRED(3, "过期");

    private static final Map<Integer, DataItemStatus> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (DataItemStatus dataItemStatus : values()) {
            INDEX_MAP.put(dataItemStatus.getValue(), dataItemStatus);
            JSON_STRUCTURE.add(dataItemStatus.getValue().intValue(), ImmutableMap.builder().put("value", dataItemStatus.getValue()).put("key", dataItemStatus.name()).put("text", dataItemStatus.getDescription()).build());
        }
    }

    DataItemStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static DataItemStatus get(Integer index) {
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
