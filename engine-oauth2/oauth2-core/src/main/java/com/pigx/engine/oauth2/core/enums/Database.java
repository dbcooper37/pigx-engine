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

@Schema(name = "数据库类别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/enums/Database.class */
public enum Database implements BaseUiEnum<Integer> {
    ORACLE(0, "Oracle"),
    POSTGRESQL(1, "PostgreSQL"),
    MYSQL(2, "Mysql"),
    MARIADB(3, "MariaDB"),
    SQLSERVER(4, "SQLServer"),
    SYBASE(5, "SyBase"),
    SAPDB(6, "SAPDB"),
    DB2(7, "DB2"),
    H2(8, "H2"),
    REDIS(9, "Redis");

    private static final Map<Integer, Database> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (Database database : values()) {
            INDEX_MAP.put(database.getValue(), database);
            JSON_STRUCTURE.add(database.getValue().intValue(), ImmutableMap.builder().put("value", database.getValue()).put("key", database.name()).put("text", database.getDescription()).put("index", database.getValue()).build());
        }
    }

    Database(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Database get(Integer index) {
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
