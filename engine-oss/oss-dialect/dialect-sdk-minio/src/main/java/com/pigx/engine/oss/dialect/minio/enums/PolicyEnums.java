package com.pigx.engine.oss.dialect.minio.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.pigx.engine.core.definition.enums.BaseUiEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum PolicyEnums implements BaseUiEnum<Integer> {

    /**
     * enum
     */
    PRIVATE(0, "私有"),
    PUBLIC(1, "公开"),
    CUSTOM(2, "自定义");

    private static final Map<Integer, PolicyEnums> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (PolicyEnums policyEnums : PolicyEnums.values()) {
            INDEX_MAP.put(policyEnums.getValue(), policyEnums);
            JSON_STRUCTURE.add(policyEnums.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", policyEnums.getValue())
                            .put("key", policyEnums.name())
                            .put("text", policyEnums.getDescription())
                            .put("index", policyEnums.getValue())
                            .build());
        }
    }

    @Schema(name = "枚举值")
    private final Integer value;
    @Schema(name = "文字")
    private final String description;

    PolicyEnums(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PolicyEnums get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum枚举值
     */
    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
