package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.service.BaseService;
import java.io.Serializable;
import java.util.List;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/BindingController.class */
public interface BindingController<E extends BaseEntity, ID extends Serializable, S extends BaseService<E, ID>> extends PaginationController {
    S getService();

    default Result<List<E>> findAll() {
        return (Result<List<E>>) result(getService().findAll());
    }

    default Result<E> findById(ID id) {
        return (Result<E>) result((BindingController<E, ID, S>) getService().findById(id).orElse(null));
    }

    default Result<E> save(E e) {
        return (Result<E>) result((BindingController<E, ID, S>) getService().save(e));
    }

    default Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getService().deleteById(id);
        return result;
    }
}
