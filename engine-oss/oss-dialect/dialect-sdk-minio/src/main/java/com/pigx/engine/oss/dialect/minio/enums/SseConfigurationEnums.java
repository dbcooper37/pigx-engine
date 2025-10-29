package com.pigx.engine.oss.dialect.minio.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.pigx.engine.core.definition.enums.BaseUiEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum SseConfigurationEnums implements BaseUiEnum<Integer> {

    /**
     * enum
     */
    DISABLED(0, "DISABLED"),
    AWS_KMS(1, "SSE_KMS"),
    AES256(2, "SSE_S3");

    private static final Map<Integer, SseConfigurationEnums> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (SseConfigurationEnums sseConfiguration : SseConfigurationEnums.values()) {
            INDEX_MAP.put(sseConfiguration.getValue(), sseConfiguration);
            JSON_STRUCTURE.add(sseConfiguration.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", sseConfiguration.getValue())
                            .put("key", sseConfiguration.name())
                            .put("text", sseConfiguration.getDescription())
                            .put("index", sseConfiguration.getValue())
                            .build());
        }
    }

    @Schema(name = "枚举值")
    private final Integer value;
    @Schema(name = "文字")
    private final String description;

    SseConfigurationEnums(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static SseConfigurationEnums get(Integer index) {
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
