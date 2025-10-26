package com.pigx.engine.data.core.jpa.entity;

import com.querydsl.core.annotations.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import java.util.Date;

@Generated({"com.querydsl.codegen.DefaultSupertypeSerializer"})
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/QAbstractEntity.class */
public class QAbstractEntity extends EntityPathBase<AbstractEntity> {
    private static final long serialVersionUID = -1344335318;
    public static final QAbstractEntity abstractEntity = new QAbstractEntity("abstractEntity");
    public final DateTimePath<Date> createTime;
    public final DateTimePath<Date> updateTime;

    public QAbstractEntity(String variable) {
        super(AbstractEntity.class, PathMetadataFactory.forVariable(variable));
        this.createTime = createDateTime("createTime", Date.class);
        this.updateTime = createDateTime("updateTime", Date.class);
    }

    public QAbstractEntity(Path<? extends AbstractEntity> path) {
        super(path.getType(), path.getMetadata());
        this.createTime = createDateTime("createTime", Date.class);
        this.updateTime = createDateTime("updateTime", Date.class);
    }

    public QAbstractEntity(PathMetadata metadata) {
        super(AbstractEntity.class, metadata);
        this.createTime = createDateTime("createTime", Date.class);
        this.updateTime = createDateTime("updateTime", Date.class);
    }
}
