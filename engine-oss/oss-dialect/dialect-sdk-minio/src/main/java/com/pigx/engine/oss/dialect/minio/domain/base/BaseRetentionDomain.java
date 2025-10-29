package com.pigx.engine.oss.dialect.minio.domain.base;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import io.swagger.v3.oas.annotations.media.Schema;


public abstract class BaseRetentionDomain implements BaseEntity {

    @Schema(name = "保留模式")
    private RetentionModeEnums mode;

    public RetentionModeEnums getMode() {
        return mode;
    }

    public void setMode(RetentionModeEnums mode) {
        this.mode = mode;
    }
}
