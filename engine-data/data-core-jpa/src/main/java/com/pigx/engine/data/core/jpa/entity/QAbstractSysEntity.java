package com.pigx.engine.data.core.jpa.entity;

import com.pigx.engine.data.core.enums.DataItemStatus;
import com.querydsl.core.annotations.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.util.Date;

@Generated({"com.querydsl.codegen.DefaultSupertypeSerializer"})
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/QAbstractSysEntity.class */
public class QAbstractSysEntity extends EntityPathBase<AbstractSysEntity> {
    private static final long serialVersionUID = 2009323881;
    public static final QAbstractSysEntity abstractSysEntity = new QAbstractSysEntity("abstractSysEntity");
    public final QAbstractAuditEntity _super;
    public final StringPath createBy;
    public final DateTimePath<Date> createTime;
    public final StringPath description;
    public final NumberPath<Integer> ranking;
    public final BooleanPath reserved;
    public final NumberPath<Integer> reversion;
    public final EnumPath<DataItemStatus> status;
    public final StringPath updateBy;
    public final DateTimePath<Date> updateTime;

    public QAbstractSysEntity(String variable) {
        super(AbstractSysEntity.class, PathMetadataFactory.forVariable(variable));
        this._super = new QAbstractAuditEntity((Path<? extends AbstractAuditEntity>) this);
        this.createBy = this._super.createBy;
        this.createTime = this._super.createTime;
        this.description = createString("description");
        this.ranking = createNumber("ranking", Integer.class);
        this.reserved = createBoolean("reserved");
        this.reversion = this._super.reversion;
        this.status = createEnum("status", DataItemStatus.class);
        this.updateBy = this._super.updateBy;
        this.updateTime = this._super.updateTime;
    }

    public QAbstractSysEntity(Path<? extends AbstractSysEntity> path) {
        super(path.getType(), path.getMetadata());
        this._super = new QAbstractAuditEntity((Path<? extends AbstractAuditEntity>) this);
        this.createBy = this._super.createBy;
        this.createTime = this._super.createTime;
        this.description = createString("description");
        this.ranking = createNumber("ranking", Integer.class);
        this.reserved = createBoolean("reserved");
        this.reversion = this._super.reversion;
        this.status = createEnum("status", DataItemStatus.class);
        this.updateBy = this._super.updateBy;
        this.updateTime = this._super.updateTime;
    }

    public QAbstractSysEntity(PathMetadata metadata) {
        super(AbstractSysEntity.class, metadata);
        this._super = new QAbstractAuditEntity((Path<? extends AbstractAuditEntity>) this);
        this.createBy = this._super.createBy;
        this.createTime = this._super.createTime;
        this.description = createString("description");
        this.ranking = createNumber("ranking", Integer.class);
        this.reserved = createBoolean("reserved");
        this.reversion = this._super.reversion;
        this.status = createEnum("status", DataItemStatus.class);
        this.updateBy = this._super.updateBy;
        this.updateTime = this._super.updateTime;
    }
}
