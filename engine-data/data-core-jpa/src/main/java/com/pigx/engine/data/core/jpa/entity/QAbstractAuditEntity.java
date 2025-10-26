package com.pigx.engine.data.core.jpa.entity;

import com.querydsl.core.annotations.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.util.Date;

@Generated({"com.querydsl.codegen.DefaultSupertypeSerializer"})
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/QAbstractAuditEntity.class */
public class QAbstractAuditEntity extends EntityPathBase<AbstractAuditEntity> {
    private static final long serialVersionUID = 639140279;
    public static final QAbstractAuditEntity abstractAuditEntity = new QAbstractAuditEntity("abstractAuditEntity");
    public final QAbstractEntity _super;
    public final StringPath createBy;
    public final DateTimePath<Date> createTime;
    public final NumberPath<Integer> reversion;
    public final StringPath updateBy;
    public final DateTimePath<Date> updateTime;

    public QAbstractAuditEntity(String variable) {
        super(AbstractAuditEntity.class, PathMetadataFactory.forVariable(variable));
        this._super = new QAbstractEntity((Path<? extends AbstractEntity>) this);
        this.createBy = createString("createBy");
        this.createTime = this._super.createTime;
        this.reversion = createNumber("reversion", Integer.class);
        this.updateBy = createString("updateBy");
        this.updateTime = this._super.updateTime;
    }

    public QAbstractAuditEntity(Path<? extends AbstractAuditEntity> path) {
        super(path.getType(), path.getMetadata());
        this._super = new QAbstractEntity((Path<? extends AbstractEntity>) this);
        this.createBy = createString("createBy");
        this.createTime = this._super.createTime;
        this.reversion = createNumber("reversion", Integer.class);
        this.updateBy = createString("updateBy");
        this.updateTime = this._super.updateTime;
    }

    public QAbstractAuditEntity(PathMetadata metadata) {
        super(AbstractAuditEntity.class, metadata);
        this._super = new QAbstractEntity((Path<? extends AbstractEntity>) this);
        this.createBy = createString("createBy");
        this.createTime = this._super.createTime;
        this.reversion = createNumber("reversion", Integer.class);
        this.updateBy = createString("updateBy");
        this.updateTime = this._super.updateTime;
    }
}
