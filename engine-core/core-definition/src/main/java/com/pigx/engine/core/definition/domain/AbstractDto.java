package com.pigx.engine.core.definition.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/AbstractDto.class */
public abstract class AbstractDto implements BaseDto {

    @Schema(name = "排序值")
    private Integer ranking = 0;

    public Integer getRanking() {
        return this.ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
