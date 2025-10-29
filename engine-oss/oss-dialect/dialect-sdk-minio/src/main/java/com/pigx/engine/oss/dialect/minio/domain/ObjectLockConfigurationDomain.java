package com.pigx.engine.oss.dialect.minio.domain;

import com.pigx.engine.oss.dialect.minio.domain.base.BaseRetentionDomain;
import com.pigx.engine.oss.dialect.minio.enums.RetentionUnitEnums;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "存储桶保留设置域对象")
public class ObjectLockConfigurationDomain extends BaseRetentionDomain {

    @Schema(name = "保留周期")
    private RetentionUnitEnums unit;

    @Schema(name = "保留有效期")
    private Integer validity;

    public RetentionUnitEnums getUnit() {
        return unit;
    }

    public void setUnit(RetentionUnitEnums unit) {
        this.unit = unit;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }
}
