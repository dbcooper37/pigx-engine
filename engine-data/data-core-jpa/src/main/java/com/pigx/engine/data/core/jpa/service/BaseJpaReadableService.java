package com.pigx.engine.data.core.jpa.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.service.BasePageService;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/service/BaseJpaReadableService.class */
public interface BaseJpaReadableService<E extends BaseEntity, ID extends Serializable> extends BasePageService<E, ID> {
    BaseJpaRepository<E, ID> getRepository();

    @Override // com.pigx.engine.data.core.service.BaseService
    default List<E> findAll() {
        return getRepository().m105findAll();
    }

    @Override // com.pigx.engine.data.core.service.BasePageService
    default Page<E> findByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default Optional<E> findById(ID id) {
        return getRepository().findById((BaseJpaRepository<E, ID>) id);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default List<E> findAllById(Iterable<ID> ids) {
        return getRepository().m104findAllById((Iterable) ids);
    }

    @Override // com.pigx.engine.data.core.service.BaseService
    default boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    default long count() {
        return getRepository().count();
    }

    default long count(Specification<E> specification) {
        return getRepository().count(specification);
    }

    @Override // com.pigx.engine.data.core.service.BasePageService
    default List<E> findAll(Sort sort) {
        return getRepository().m106findAll(sort);
    }

    default List<E> findAll(Specification<E> specification) {
        return getRepository().findAll(specification);
    }

    default List<E> findAll(Specification<E> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    default Page<E> findByPage(Specification<E> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }

    default Page<E> findByPage(Specification<E> specification, int pageNumber, int pageSize) {
        return getRepository().findAll((Specification) specification, (Pageable) PageRequest.of(pageNumber, pageSize));
    }

    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction) {
        return findByPage(PageRequest.of(pageNumber, pageSize, direction, new String[0]));
    }

    default E getReferenceById(ID id) {
        return (E) getRepository().getReferenceById((BaseJpaRepository<E, ID>) id);
    }

    default boolean exists(Specification<E> specification) {
        return getRepository().exists(specification);
    }
}
