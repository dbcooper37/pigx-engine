package com.pigx.engine.data.mongo.entity;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/* loaded from: data-core-mongodb-3.5.7.0.jar:cn/herodotus/engine/data/core/mongodb/entity/AbstractEntity.class */
public abstract class AbstractEntity implements BaseEntity {

    @CreatedDate
    @Schema(name = "数据创建时间")
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT)
    private Date createTime = new Date();

    @Schema(name = "数据更新时间")
    @LastModifiedDate
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
