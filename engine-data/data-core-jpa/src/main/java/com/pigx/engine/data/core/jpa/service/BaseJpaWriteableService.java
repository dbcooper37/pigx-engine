package com.pigx.engine.data.core.jpa.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import java.io.Serializable;
import java.util.List;

/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/service/BaseJpaWriteableService.class */
public interface BaseJpaWriteableService<E extends BaseEntity, ID extends Serializable> extends BaseJpaReadableService<E, ID> {
    @Override // com.pigx.engine.data.core.service.BaseService
    default E save(E domain) {
        return (E) getRepository().save(domain);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default List<E> saveAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default void delete(E domain) {
        getRepository().delete(domain);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default void deleteById(ID id) {
        getRepository().deleteById((BaseJpaRepository<E, ID>) id);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default void deleteAll() {
        getRepository().deleteAll();
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default void deleteAllById(Iterable<? extends ID> ids) {
        getRepository().deleteAllById(ids);
    }

    default void deleteAllByIdInBatch(Iterable<ID> ids) {
        getRepository().deleteAllByIdInBatch(ids);
    }

    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    default void deleteAllInBatch(Iterable<E> entities) {
        getRepository().deleteAllInBatch(entities);
    }

    default E saveAndFlush(E entity) {
        return (E) getRepository().saveAndFlush(entity);
    }

    default List<E> saveAllAndFlush(List<E> entities) {
        return getRepository().saveAllAndFlush(entities);
    }

    default void flush() {
        getRepository().flush();
    }
}
