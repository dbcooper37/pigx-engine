package com.pigx.engine.data.core.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.io.Serializable;


public interface BaseSliceService<E extends BaseEntity, ID extends Serializable> extends BaseService<E, ID> {

    /**
     * 分页查询
     *
     * @param pageable {@link Pageable}
     * @return 分页数据
     */
    default Slice<E> findByPage(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @return 分页数据
     */
    default Slice<E> findByPage(int pageNumber, int pageSize) {
        return findByPage(PageRequest.of(pageNumber, pageSize));
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param sort       {@link Sort}
     * @return 分页数据
     */
    default Slice<E> findByPage(int pageNumber, int pageSize, Sort sort) {
        return findByPage(PageRequest.of(pageNumber, pageSize, sort));
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param direction  {@link Sort.Direction}
     * @param properties 排序的属性名称, 可以多个
     * @return 分页数据
     */
    default Slice<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return findByPage(PageRequest.of(pageNumber, pageSize, direction, properties));
    }
}
