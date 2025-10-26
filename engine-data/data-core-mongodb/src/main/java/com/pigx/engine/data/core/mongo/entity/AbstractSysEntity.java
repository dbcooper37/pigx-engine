package com.pigx.engine.data.mongo.entity;

import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.data.core.mongodb.converter.DataItemStatusConverter;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.convert.ValueConverter;

/* loaded from: data-core-mongodb-3.5.7.0.jar:cn/herodotus/engine/data/core/mongodb/entity/AbstractSysEntity.class */
public abstract class AbstractSysEntity extends AbstractAuditEntity {

    @Schema(name = "备注")
    private String description;

    @ValueConverter(DataItemStatusConverter.class)
    @Schema(name = "数据状态")
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(name = "是否为保留数据", description = "True 为不能删，False为可以删除")
    private Boolean reserved = Boolean.FALSE;

    @Schema(name = "排序值")
    private Integer ranking = 0;

    public DataItemStatus getStatus() {
        return this.status;
    }

    public void setStatus(DataItemStatus status) {
        this.status = status;
    }

    public Boolean getReserved() {
        return this.reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRanking() {
        return this.ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override // com.pigx.engine.data.core.mongodb.entity.AbstractAuditEntity, com.pigx.engine.data.core.mongodb.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("status", this.status).add("reserved", this.reserved).add("description", this.description).add("ranking", this.ranking).addValue(super.toString()).toString();
    }
}
