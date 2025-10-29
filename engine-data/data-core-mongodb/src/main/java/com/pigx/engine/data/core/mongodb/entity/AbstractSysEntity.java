package com.pigx.engine.data.core.mongodb.entity;

import com.google.common.base.MoreObjects;
import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.data.core.mongodb.converter.DataItemStatusConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.convert.ValueConverter;


public abstract class AbstractSysEntity extends AbstractAuditEntity {

    @Schema(name = "数据状态")
    @ValueConverter(DataItemStatusConverter.class)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(name = "是否为保留数据", description = "True 为不能删，False为可以删除")
    private Boolean reserved = Boolean.FALSE;

    @Schema(name = "备注")
    private String description;

    @Schema(name = "排序值")
    private Integer ranking = 0;

    public DataItemStatus getStatus() {
        return status;
    }

    public void setStatus(DataItemStatus status) {
        this.status = status;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("reserved", reserved)
                .add("description", description)
                .add("ranking", ranking)
                .addValue(super.toString())
                .toString();
    }
}
