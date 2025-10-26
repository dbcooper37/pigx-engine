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

@Schema(name = "全部Jws加密算法")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/enums/AllJwsAlgorithm.class */
public enum AllJwsAlgorithm implements BaseUiEnum<Integer> {
    RS256(0, "签名算法 RS256"),
    RS384(1, "签名算法 RS384"),
    RS512(2, "签名算法 RS512"),
    ES256(3, "签名算法 ES256"),
    ES384(4, "签名算法 ES384"),
    ES512(5, "签名算法 ES512"),
    PS256(6, "签名算法 PS256"),
    PS384(7, "签名算法 PS384"),
    PS512(8, "签名算法 PS512"),
    HS256(9, "Mac算法 HS256"),
    HS384(10, "Mac算法 HS384"),
    HS512(11, "Mac算法 HS512");

    private static final Map<Integer, AllJwsAlgorithm> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList();

    @Schema(name = "枚举值")
    private final Integer value;

    @Schema(name = "文字")
    private final String description;

    static {
        for (AllJwsAlgorithm allJwsAlgorithm : values()) {
            INDEX_MAP.put(allJwsAlgorithm.getValue(), allJwsAlgorithm);
            JSON_STRUCTURE.add(allJwsAlgorithm.getValue().intValue(), ImmutableMap.builder().put("value", allJwsAlgorithm.getValue()).put("key", allJwsAlgorithm.name()).put("text", allJwsAlgorithm.getDescription()).put("index", allJwsAlgorithm.getValue()).build());
        }
    }

    AllJwsAlgorithm(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static AllJwsAlgorithm get(Integer index) {
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
