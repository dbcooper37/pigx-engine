package com.pigx.engine.data.core.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/* loaded from: data-core-3.5.7.0.jar:cn/herodotus/engine/data/core/service/BasePageService.class */
public interface BasePageService<E extends BaseEntity, ID extends Serializable> extends BaseService<E, ID> {
    default List<E> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    default Page<E> findByPage(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    default Page<E> findByPage(int pageNumber, int pageSize) {
        return findByPage(PageRequest.of(pageNumber, pageSize));
    }

    default Page<E> findByPage(int pageNumber, int pageSize, Sort sort) {
        return findByPage(PageRequest.of(pageNumber, pageSize, sort));
    }

    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return findByPage(PageRequest.of(pageNumber, pageSize, direction, properties));
    }
}
