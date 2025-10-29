package com.pigx.engine.web.api.servlet;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.web.core.definition.Controller;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;


public interface MybatisPlusController extends Controller {

    default <E extends BaseEntity> Result<Map<String, Object>> result(IPage<E> pages) {
        if (ObjectUtils.isNotEmpty(pages)) {
            if (CollectionUtils.isNotEmpty(pages.getRecords())) {
                return Result.success("查询数据成功！", getPageInfoMap(pages));
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("查询数据失败！");
        }
    }

    default <E extends BaseEntity> Map<String, Object> getPageInfoMap(IPage<E> page) {
        return with(page.getRecords(), (int) page.getPages(), page.getTotal());
    }
}
