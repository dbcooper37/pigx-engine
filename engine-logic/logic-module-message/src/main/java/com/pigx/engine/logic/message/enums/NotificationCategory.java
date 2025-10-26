package com.pigx.engine.logic.message.enums;

import com.pigx.engine.core.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(name = "通知类别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/enums/NotificationCategory.class */
public enum NotificationCategory implements BaseUiEnum<Integer> {
    ANNOUNCEMENT(0, "系统公告"),
    DIALOGUE(1, "私信");

    private static final Map<Integer, NotificationCategory> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "说明")
    private final String description;

    static {
        for (NotificationCategory notificationCategory : values()) {
            INDEX_MAP.put(notificationCategory.getValue(), notificationCategory);
            JSON_STRUCTURE.add(notificationCategory.getValue().intValue(), ImmutableMap.builder().put("value", notificationCategory.getValue()).put("key", notificationCategory.name()).put("text", notificationCategory.getDescription()).put("index", notificationCategory.getValue()).build());
        }
    }

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
