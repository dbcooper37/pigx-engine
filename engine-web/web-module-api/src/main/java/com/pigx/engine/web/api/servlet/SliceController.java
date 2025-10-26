package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.service.BaseSliceService;
import java.io.Serializable;
import java.util.Map;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/SliceController.class */
public interface SliceController<E extends BaseEntity, ID extends Serializable, S extends BaseSliceService<E, ID>> extends BindingController<E, ID, S> {
    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Slice<E> data = getService().findByPage(pageNumber.intValue(), pageSize.intValue());
        return resultFromSlice(data);
    }

    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize, Sort.Direction direction, String... properties) {
        Slice<E> data = getService().findByPage(pageNumber.intValue(), pageSize.intValue(), direction, properties);
        return resultFromSlice(data);
    }
}
