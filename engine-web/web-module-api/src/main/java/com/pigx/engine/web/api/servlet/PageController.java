package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.service.BasePageService;
import java.io.Serializable;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/PageController.class */
public interface PageController<E extends BaseEntity, ID extends Serializable, S extends BasePageService<E, ID>> extends BindingController<E, ID, S> {
    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> data = getService().findByPage(pageNumber.intValue(), pageSize.intValue());
        return resultFromPage(data);
    }

    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize, Sort.Direction direction, String... properties) {
        Page<E> data = getService().findByPage(pageNumber.intValue(), pageSize.intValue(), direction, properties);
        return resultFromPage(data);
    }
}
