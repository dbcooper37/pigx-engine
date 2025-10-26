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
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/QAbstractDefaultIdEntity.class */
public class QAbstractDefaultIdEntity extends EntityPathBase<AbstractDefaultIdEntity> {
    private static final long serialVersionUID = -2030651432;
    public static final QAbstractDefaultIdEntity abstractDefaultIdEntity = new QAbstractDefaultIdEntity("abstractDefaultIdEntity");
    public final QAbstractSysEntity _super;
    public final StringPath createBy;
    public final DateTimePath<Date> createTime;
    public final StringPath description;
    public final StringPath id;
    public final NumberPath<Integer> ranking;
    public final BooleanPath reserved;
    public final NumberPath<Integer> reversion;
    public final EnumPath<DataItemStatus> status;
    public final StringPath updateBy;
    public final DateTimePath<Date> updateTime;

    public QAbstractDefaultIdEntity(String variable) {
        super(AbstractDefaultIdEntity.class, PathMetadataFactory.forVariable(variable));
        this._super = new QAbstractSysEntity((Path<? extends AbstractSysEntity>) this);
        this.createBy = this._super.createBy;
        this.createTime = this._super.createTime;
        this.description = this._super.description;
        this.id = createString("id");
        this.ranking = this._super.ranking;
        this.reserved = this._super.reserved;
        this.reversion = this._super.reversion;
        this.status = this._super.status;
        this.updateBy = this._super.updateBy;
        this.updateTime = this._super.updateTime;
    }

    public QAbstractDefaultIdEntity(Path<? extends AbstractDefaultIdEntity> path) {
        super(path.getType(), path.getMetadata());
        this._super = new QAbstractSysEntity((Path<? extends AbstractSysEntity>) this);
        this.createBy = this._super.createBy;
        this.createTime = this._super.createTime;
        this.description = this._super.description;
        this.id = createString("id");
        this.ranking = this._super.ranking;
        this.reserved = this._super.reserved;
        this.reversion = this._super.reversion;
        this.status = this._super.status;
        this.updateBy = this._super.updateBy;
        this.updateTime = this._super.updateTime;
    }

    public QAbstractDefaultIdEntity(PathMetadata metadata) {
        super(AbstractDefaultIdEntity.class, metadata);
        this._super = new QAbstractSysEntity((Path<? extends AbstractSysEntity>) this);
        this.createBy = this._super.createBy;
        this.createTime = this._super.createTime;
        this.description = this._super.description;
        this.id = createString("id");
        this.ranking = this._super.ranking;
        this.reserved = this._super.reserved;
        this.reversion = this._super.reversion;
        this.status = this._super.status;
        this.updateBy = this._super.updateBy;
        this.updateTime = this._super.updateTime;
    }
}
