package com.pigx.engine.data.mongo.entity;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/* loaded from: data-core-mongodb-3.5.7.0.jar:cn/herodotus/engine/data/core/mongodb/entity/AbstractAuditEntity.class */
public abstract class AbstractAuditEntity extends AbstractEntity {

    @Schema(name = "创建人")
    @CreatedBy
    private String createBy;

    @LastModifiedBy
    @Schema(name = "最后修改人")
    private String updateBy;

    @Schema(name = "版本号")
    private Integer reversion = 0;

    public abstract String getId();

    public abstract void setId(String id);

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getReversion() {
        return this.reversion;
    }

    public void setReversion(Integer reversion) {
        this.reversion = reversion;
    }

    @Override // com.pigx.engine.data.core.mongodb.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("createBy", this.createBy).add("updateBy", this.updateBy).add("reversion", this.reversion).addValue(super.toString()).toString();
    }
}
