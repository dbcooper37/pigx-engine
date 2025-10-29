package com.pigx.engine.data.core.jpa.entity;

import com.google.common.base.MoreObjects;
import com.pigx.engine.data.core.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractSysEntity extends AbstractAuditEntity {

    @Schema(name = "数据状态", title = "可用于控制数据的状态")
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(name = "是否为保留数据", title = "早期设计用于实现逻辑删除，现在主要用于控制前端删除按钮的显示。逻辑删除可以直接使用 Hibernate 提供的逻辑删除注解 @SoftDelete", description = "True 为不能删，False为可以删除")
    @Column(name = "is_reserved")
    private Boolean reserved = Boolean.FALSE;

    @Schema(name = "备注")
    @Column(name = "description", length = 512)
    private String description;

    @Schema(name = "排序值")
    @Column(name = "ranking")
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
                .addValue(super.toString())
                .add("status", status)
                .add("reserved", reserved)
                .add("description", description)
                .add("ranking", ranking)
                .addValue(super.toString())
                .toString();
    }
}
