package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.web.core.definition.Controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/MybatisPlusController.class */
public interface MybatisPlusController extends Controller {
    default <E extends BaseEntity> Result<Map<String, Object>> result(IPage<E> pages) {
        if (ObjectUtils.isNotEmpty(pages)) {
            if (CollectionUtils.isNotEmpty(pages.getRecords())) {
                return Result.success("查询数据成功！", getPageInfoMap(pages));
            }
            return Result.empty("未查询到数据！");
        }
        return Result.failure("查询数据失败！");
    }

    default <E extends BaseEntity> Map<String, Object> getPageInfoMap(IPage<E> page) {
        return with(page.getRecords(), (int) page.getPages(), page.getTotal());
    }
}
