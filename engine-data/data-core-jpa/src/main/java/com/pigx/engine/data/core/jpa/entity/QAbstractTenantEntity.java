package com.pigx.engine.data.core.jpa.entity;

import com.querydsl.core.annotations.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

@Generated({"com.querydsl.codegen.DefaultSupertypeSerializer"})
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/QAbstractTenantEntity.class */
public class QAbstractTenantEntity extends EntityPathBase<AbstractTenantEntity> {
    private static final long serialVersionUID = -900846028;
    public static final QAbstractTenantEntity abstractTenantEntity = new QAbstractTenantEntity("abstractTenantEntity");
    public final StringPath tenantId;

    public QAbstractTenantEntity(String variable) {
        super(AbstractTenantEntity.class, PathMetadataFactory.forVariable(variable));
        this.tenantId = createString("tenantId");
    }

    public QAbstractTenantEntity(Path<? extends AbstractTenantEntity> path) {
        super(path.getType(), path.getMetadata());
        this.tenantId = createString("tenantId");
    }

    public QAbstractTenantEntity(PathMetadata metadata) {
        super(AbstractTenantEntity.class, metadata);
        this.tenantId = createString("tenantId");
    }
}
