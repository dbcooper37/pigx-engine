package com.pigx.engine.data.core.jpa.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import java.io.Serializable;

/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/service/AbstractJpaService.class */
public abstract class AbstractJpaService<E extends BaseEntity, ID extends Serializable> implements BaseJpaWriteableService<E, ID> {
    protected String like(String property) {
        return "%" + property + "%";
    }
}
