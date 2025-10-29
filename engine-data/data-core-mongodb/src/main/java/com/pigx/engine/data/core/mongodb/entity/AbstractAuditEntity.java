package com.pigx.engine.data.core.mongodb.entity;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;


public abstract class AbstractAuditEntity extends AbstractEntity {

    @Schema(name = "创建人")
    @CreatedBy
    private String createBy;
    @Schema(name = "最后修改人")
    @LastModifiedBy
    private String updateBy;
    @Schema(name = "版本号")
    private Integer reversion = 0;

    public abstract String getId();

    public abstract void setId(String id);

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getReversion() {
        return reversion;
    }

    public void setReversion(Integer reversion) {
        this.reversion = reversion;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("createBy", createBy)
                .add("updateBy", updateBy)
                .add("reversion", reversion)
                .addValue(super.toString())
                .toString();
    }
}
