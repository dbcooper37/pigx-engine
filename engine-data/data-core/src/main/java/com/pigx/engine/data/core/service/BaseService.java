package com.pigx.engine.data.core.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/* loaded from: data-core-3.5.7.0.jar:cn/herodotus/engine/data/core/service/BaseService.class */
public interface BaseService<E extends BaseEntity, ID extends Serializable> {
    default List<E> findAll() {
        throw new UnsupportedOperationException();
    }

    default Optional<E> findById(ID id) {
        throw new UnsupportedOperationException();
    }

    default List<E> findAllById(Iterable<ID> ids) {
        throw new UnsupportedOperationException();
    }

    default boolean existsById(ID id) {
        throw new UnsupportedOperationException();
    }

    default E save(E domain) {
        throw new UnsupportedOperationException();
    }

    default List<E> saveAll(Iterable<E> entities) {
        throw new UnsupportedOperationException();
    }

    default void delete(E domain) {
        throw new UnsupportedOperationException();
    }

    default void deleteById(ID id) {
        throw new UnsupportedOperationException();
    }

    default void deleteAll() {
        throw new UnsupportedOperationException();
    }

    default void deleteAll(Iterable<E> entities) {
        throw new UnsupportedOperationException();
    }

    default void deleteAllById(Iterable<? extends ID> ids) {
        throw new UnsupportedOperationException();
    }
}
