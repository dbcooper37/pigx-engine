package com.pigx.engine.data.core.jpa.repository;

import com.pigx.engine.core.definition.domain.BaseEntity;
import jakarta.persistence.QueryHint;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/repository/BaseJpaRepository.class */
public interface BaseJpaRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    /* renamed from: findAll, reason: merged with bridge method [inline-methods] */
    List<E> m105findAll();

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    /* renamed from: findAll, reason: merged with bridge method [inline-methods] */
    List<E> m106findAll(Sort sort);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<E> findAll(Pageable pageable);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<E> findAll(Specification<E> specification);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<E> findAll(Specification<E> specification, Sort sort);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<E> findAll(Specification<E> specification, Pageable pageable);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<E> findById(ID id);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<E> findOne(Specification<E> specification);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    /* renamed from: findAllById, reason: merged with bridge method [inline-methods] */
    List<E> m104findAllById(Iterable<ID> ids);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    long count(Specification<E> specification);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    long count();

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    E getReferenceById(ID id);

    @Transactional
    void deleteById(ID id);
}
