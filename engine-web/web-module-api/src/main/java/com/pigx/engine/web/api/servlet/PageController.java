package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.service.BasePageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Map;


public interface PageController<E extends BaseEntity, ID extends Serializable, S extends BasePageService<E, ID>> extends BindingController<E, ID, S> {

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码，起始页码 0
     * @param pageSize   每页显示数据条数
     * @return 包装成 {@link Result} 的查询结果
     */
    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> data = getService().findByPage(pageNumber, pageSize);
        return resultFromPage(data);
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param direction  排序方向 {@link Sort.Direction}
     * @param properties 需要排序的字段
     * @return 包装成 {@link Result} 的 {@link Map} 类型查询结果
     */
    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize, Sort.Direction direction, String... properties) {
        Page<E> data = getService().findByPage(pageNumber, pageSize, direction, properties);
        return resultFromPage(data);
    }
}
