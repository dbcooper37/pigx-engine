package com.pigx.engine.logic.upms.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.pigx.engine.core.definition.enums.BaseUiEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Schema(name = "人员身份")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Identity implements BaseUiEnum<Integer> {
    /**
     * enum
     */
    STAFF(0, "员工"),
    SECTION_LEADER(1, "部门负责人"),
    LEADERSHIP(2, "领导");

    private static final Map<Integer, Identity> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (Identity identity : Identity.values()) {
            INDEX_MAP.put(identity.getValue(), identity);
            JSON_STRUCTURE.add(identity.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", identity.getValue())
                            .put("key", identity.name())
                            .put("text", identity.getDescription())
                            .put("index", identity.getValue())
                            .build());
        }
    }

    @Schema(name = "索引")
    private final Integer value;
    @Schema(name = "文字")
    private String description;

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

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum索引
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
