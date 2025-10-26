package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import java.io.Serializable;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/AbstractJpaWriteableController.class */
public abstract class AbstractJpaWriteableController<E extends BaseEntity, ID extends Serializable> extends AbstractWriteableController<E, ID, BaseJpaWriteableService<E, ID>> {
    @Override // com.pigx.engine.web.api.servlet.AbstractWriteableController, com.pigx.engine.web.api.servlet.BindingController
    public Result<E> save(E e) {
        return (Result<E>) result((AbstractJpaWriteableController<E, ID>) ((BaseJpaWriteableService) getService()).saveAndFlush(e));
    }
}
