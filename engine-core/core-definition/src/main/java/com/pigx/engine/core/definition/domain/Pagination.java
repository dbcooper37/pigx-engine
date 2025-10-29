package com.pigx.engine.core.definition.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public non-sealed interface Pagination extends BaseDomain {

    /**
     * Page 对象转换为 Map
     *
     * @param content       数据实体 List
     * @param totalPages    分页总页数
     * @param totalElements 总的数据条目
     * @param <D>           {@link BaseDomain} 子类型
     * @return Map
     */
    default <D extends BaseDomain> Map<String, Object> with(List<D> content, int totalPages, long totalElements) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("totalPages", totalPages);
        result.put("totalElements", totalElements);
        return result;
    }

    /**
     * Slice 对象信息转换为 Map
     *
     * @param content     数据实体 List
     * @param hasNext     是否有上一页
     * @param hasPrevious 是否有下一页
     * @param <D>         {@link BaseDomain} 子类型
     * @return Map
     */
    default <D extends BaseDomain> Map<String, Object> with(List<D> content, boolean hasNext, boolean hasPrevious) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("hasNext", hasNext);
        result.put("hasPrevious", hasPrevious);
        return result;
    }
}
