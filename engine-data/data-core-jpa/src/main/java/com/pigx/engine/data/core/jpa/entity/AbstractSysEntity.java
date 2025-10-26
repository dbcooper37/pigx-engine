package com.pigx.engine.data.core.jpa.entity;

import com.pigx.engine.data.core.enums.DataItemStatus;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/AbstractSysEntity.class */
public abstract class AbstractSysEntity extends AbstractAuditEntity {

    @Column(name = "description", length = 512)
    @Schema(name = "备注")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "数据状态", title = "可用于控制数据的状态")
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Column(name = "is_reserved")
    @Schema(name = "是否为保留数据", title = "早期设计用于实现逻辑删除，现在主要用于控制前端删除按钮的显示。逻辑删除可以直接使用 Hibernate 提供的逻辑删除注解 @SoftDelete", description = "True 为不能删，False为可以删除")
    private Boolean reserved = Boolean.FALSE;

    @Column(name = "ranking")
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

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(super.toString()).add("status", this.status).add("reserved", this.reserved).add("description", this.description).add("ranking", this.ranking).addValue(super.toString()).toString();
    }
}
