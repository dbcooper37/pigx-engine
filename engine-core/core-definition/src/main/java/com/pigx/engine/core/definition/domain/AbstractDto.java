package com.pigx.engine.core.definition.domain;

import io.swagger.v3.oas.annotations.media.Schema;


public abstract class AbstractDto implements BaseDto {

    @Schema(name = "排序值")
    private Integer ranking = 0;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
