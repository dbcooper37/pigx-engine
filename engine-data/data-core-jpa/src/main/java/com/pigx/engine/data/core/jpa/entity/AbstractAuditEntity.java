package com.pigx.engine.data.core.jpa.entity;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/AbstractAuditEntity.class */
public abstract class AbstractAuditEntity extends AbstractEntity {

    @Column(name = "create_by")
    @Schema(name = "创建人", title = "数据库审计通用字段", description = "由 JPA 自动填充无需手动设置")
    @CreatedBy
    private String createBy;

    @Column(name = "update_by")
    @LastModifiedBy
    @Schema(name = "最后修改人", title = "数据库审计通用字段", description = "由 JPA 自动填充无需手动设置")
    private String updateBy;

    @Column(name = "reversion")
    @Schema(name = "版本号", title = "可以与@Version注解配合实现乐观锁")
    private Integer reversion = 0;

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

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("createBy", this.createBy).add("updateBy", this.updateBy).add("reversion", this.reversion).addValue(super.toString()).toString();
    }
}
