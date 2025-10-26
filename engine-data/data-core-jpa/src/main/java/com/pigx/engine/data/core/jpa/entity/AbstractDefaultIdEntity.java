package com.pigx.engine.data.core.jpa.entity;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/AbstractDefaultIdEntity.class */
public abstract class AbstractDefaultIdEntity extends AbstractSysEntity {

    @Id
    @Schema(name = "ID", title = "实体主键")
    @UuidGenerator
    @Column(name = "id", length = 64)
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", this.id).addValue(super.toString()).toString();
    }
}
