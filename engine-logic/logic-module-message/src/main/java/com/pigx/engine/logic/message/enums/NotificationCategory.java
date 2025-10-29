package com.pigx.engine.logic.message.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.pigx.engine.core.definition.enums.BaseUiEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Schema(name = "通知类别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NotificationCategory implements BaseUiEnum<Integer> {

    /**
     * enum
     */
    ANNOUNCEMENT(0, "系统公告"),
    DIALOGUE(1, "私信");

    private static final Map<Integer, NotificationCategory> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (NotificationCategory notificationCategory : NotificationCategory.values()) {
            INDEX_MAP.put(notificationCategory.getValue(), notificationCategory);
            JSON_STRUCTURE.add(notificationCategory.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", notificationCategory.getValue())
                            .put("key", notificationCategory.name())
                            .put("text", notificationCategory.getDescription())
                            .put("index", notificationCategory.getValue())
                            .build());
        }
    }

    @Schema(name = "枚举值")
    private final Integer value;
    @Schema(name = "说明")
    private final String description;

    NotificationCategory(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static NotificationCategory get(Integer index) {
        return INDEX_MAP.getOrDefault(index, null);
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
