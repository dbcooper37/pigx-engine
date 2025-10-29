package com.pigx.engine.data.core.jpa.entity;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <p> Description : 实体通用属性 </p>
 * <p>
 * 继承改类可以实现向数据表中统一添加数据库审计字段，系统会自动填充相关信息。
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 16:09
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity extends AbstractEntity {

    @Schema(name = "创建人", title = "数据库审计通用字段", description = "由 JPA 自动填充无需手动设置")
    @Column(name = "create_by")
    @CreatedBy
    private String createBy;

    @Schema(name = "最后修改人", title = "数据库审计通用字段", description = "由 JPA 自动填充无需手动设置")
    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

    @Schema(name = "版本号", title = "可以与@Version注解配合实现乐观锁")
    @Column(name = "reversion")
    private Integer reversion = 0;

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
