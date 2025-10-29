package com.pigx.engine.oss.dialect.minio.domain;

import com.pigx.engine.oss.dialect.minio.domain.base.BaseRetentionDomain;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "对象保留设置域对象")
public class RetentionDomain extends BaseRetentionDomain {

    @Schema(name = "保留截止日期")
    private String retainUntilDate;

    public String getRetainUntilDate() {
        return retainUntilDate;
    }

    public void setRetainUntilDate(String retainUntilDate) {
        this.retainUntilDate = retainUntilDate;
    }
}
