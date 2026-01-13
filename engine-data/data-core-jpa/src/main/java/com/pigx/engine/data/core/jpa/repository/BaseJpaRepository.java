package com.pigx.engine.data.core.jpa.repository;

import com.pigx.engine.core.definition.domain.BaseEntity;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base repository interface for JPA entities with built-in query caching.
 *
 * <p>This repository extends both {@link JpaRepository} and {@link JpaSpecificationExecutor}
 * to provide a comprehensive set of CRUD and query operations with automatic
 * Hibernate second-level cache integration.</p>
 *
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>Automatic query caching using {@code @QueryHints(HINT_CACHEABLE)}</li>
 *   <li>Support for {@link Specification}-based dynamic queries</li>
 *   <li>Pagination and sorting out of the box</li>
 *   <li>Transaction management for write operations</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>{@code
 * public interface UserRepository extends BaseJpaRepository<User, String> {
 *     Optional<User> findByUsername(String username);
 *     
 *     @EntityGraph(attributePaths = {"roles"})
 *     Optional<User> findByUsernameWithRoles(String username);
 * }
 * }</pre>
 *
 * <p><b>Best Practices:</b></p>
 * <ul>
 *   <li>Use {@code @EntityGraph} for relationships to avoid N+1 queries</li>
 *   <li>Define custom queries for complex filtering instead of Specification where possible</li>
 *   <li>Consider using projections for read-only operations to improve performance</li>
 * </ul>
 *
 * @param <E>  the entity type, must extend {@link BaseEntity}
 * @param <ID> the entity's ID type, must be {@link Serializable}
 * @author gengwei.zheng
 * @author PigX Engine Team
 * @since 1.0.0
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 * @see BaseEntity
 */
@NoRepositoryBean
public interface BaseJpaRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll();

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll(Sort sort);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    Page<E> findAll(Pageable pageable);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll(Specification<E> specification);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll(Specification<E> specification, Sort sort);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    Page<E> findAll(Specification<E> specification, Pageable pageable);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    Optional<E> findById(ID id);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    Optional<E> findOne(Specification<E> specification);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAllById(Iterable<ID> ids);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    long count(Specification<E> specification);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    long count();

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @Override
    E getReferenceById(ID id);

    @Transactional
    @Override
    void deleteById(ID id);
}
