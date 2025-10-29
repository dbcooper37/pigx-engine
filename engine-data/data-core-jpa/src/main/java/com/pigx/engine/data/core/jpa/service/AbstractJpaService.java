package com.pigx.engine.data.core.jpa.service;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;

import java.io.Serializable;


public abstract class AbstractJpaService<E extends BaseEntity, ID extends Serializable> implements BaseJpaWriteableService<E, ID> {

    protected String like(String property) {
        return SymbolConstants.PERCENT + property + SymbolConstants.PERCENT;
    }
}
