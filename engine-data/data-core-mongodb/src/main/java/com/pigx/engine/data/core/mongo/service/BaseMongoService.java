package com.pigx.engine.data.mongo.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.data.core.mongodb.repository.BaseMongoRepository;
import com.pigx.engine.data.core.service.BasePageService;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/* loaded from: data-core-mongodb-3.5.7.0.jar:cn/herodotus/engine/data/core/mongodb/service/BaseMongoService.class */
public interface BaseMongoService<E extends BaseEntity, ID extends Serializable> extends BasePageService<E, ID> {
    BaseMongoRepository<E, ID> getRepository();

    @Override // com.pigx.engine.data.core.service.BaseService
    default List<E> findAll() {
        return getRepository().findAll();
    }

    @Override // com.pigx.engine.data.core.service.BasePageService
    default Page<E> findByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default Optional<E> findById(ID id) {
        return getRepository().findById(id);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default List<E> findAllById(Iterable<ID> ids) {
        return getRepository().findAllById(ids);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

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
        getRepository().deleteById(id);
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

    @Override // com.pigx.engine.data.core.service.BasePageService
    default List<E> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }
}
