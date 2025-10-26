package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.web.core.definition.Controller;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/PaginationController.class */
public interface PaginationController extends Controller {
    default <E extends BaseEntity> Result<Map<String, Object>> resultFromPage(Page<E> pages) {
        if (null == pages) {
            return Result.failure("查询数据失败！");
        }
        if (CollectionUtils.isNotEmpty(pages.getContent())) {
            return Result.success("查询数据成功！", fromPage(pages));
        }
        return Result.empty("未查询到数据！");
    }

    default <E extends BaseEntity> Result<Map<String, Object>> resultFromSlice(Slice<E> slices) {
        if (null == slices) {
            return Result.failure("查询数据失败！");
        }
        if (CollectionUtils.isNotEmpty(slices.getContent())) {
            return Result.success("查询数据成功！", fromSlice(slices));
        }
        return Result.empty("未查询到数据！");
    }

    default <E extends BaseEntity> Map<String, Object> fromPage(Page<E> pages) {
        return with(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }

    default <E extends BaseEntity> Map<String, Object> fromSlice(Slice<E> slices) {
        return with(slices.getContent(), slices.hasNext(), slices.hasPrevious());
    }
}
