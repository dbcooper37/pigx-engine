package com.pigx.engine.data.core.jpa.entity;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/AbstractEntity.class */
public abstract class AbstractEntity implements BaseEntity {

    @Schema(name = "数据创建时间", title = "数据库审计通用字段", description = "由 JPA 自动填充无需手动设置。改字段不允许更新")
    @Column(name = "create_time", updatable = false)
    @CreatedDate
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT)
    private Date createTime = new Date();

    @Schema(name = "数据更新时间", title = "数据库审计通用字段", description = "由 JPA 自动填充无需手动设置")
    @LastModifiedDate
    @Column(name = "update_time")
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT)
    private Date updateTime = new Date();

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("createTime", this.createTime).add("updateTime", this.updateTime).toString();
    }
}
