package com.pigx.engine.data.core.jpa.service;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.service.BasePageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * <p> Description : 只读Service，可以提供基于视图实体的操作 </p>
 * <p>
 * JPA 支持视图（View） 的映射，视图无法进行增、删、改操作，所以将度操作单独提取出来，以支持视图同时避免增、删、改误操作
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 11:56
 */
public interface BaseJpaReadableService<E extends BaseEntity, ID extends Serializable> extends BasePageService<E, ID> {

    /**
     * 获取Repository
     *
     * @return {@link BaseJpaRepository}
     */
    BaseJpaRepository<E, ID> getRepository();

    /**
     * 查询全部数据
     *
     * @return 全部数据列表
     */
    @Override
    default List<E> findAll() {
        return getRepository().findAll();
    }

    /**
     * 查询分页数据
     *
     * @param pageable {@link Pageable}
     * @return 分页数据
     */
    @Override
    default Page<E> findByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * 根据ID查询数据
     *
     * @param id 数据ID
     * @return 与ID对应的数据，如果不存在则返回空
     */
    @Override
    default Optional<E> findById(ID id) {
        return getRepository().findById(id);
    }

    /**
     * 查询多个 ID 对应的数据
     *
     * @param ids {@link Iterable}
     * @return 数据对象 {@link List}
     */
    @Override
    default List<E> findAllById(Iterable<ID> ids) {
        return getRepository().findAllById(ids);
    }

    /**
     * 数据是否存在
     *
     * @param id 数据ID
     * @return true 存在，false 不存在
     */
    @Override
    default boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    /**
     * 查询数量
     *
     * @return 数据数量
     */
    default long count() {
        return getRepository().count();
    }

    /**
     * 查询数量
     *
     * @param specification {@link Specification}
     * @return 数据数量
     */
    default long count(Specification<E> specification) {
        return getRepository().count(specification);
    }

    /**
     * 查询全部数据
     *
     * @param sort {@link Sort}
     * @return 已排序的全部数据列表
     */
    default List<E> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    /**
     * 查询全部数据
     *
     * @param specification {@link Specification}
     * @return 全部数据列表
     */
    default List<E> findAll(Specification<E> specification) {
        return getRepository().findAll(specification);
    }

    /**
     * 查询全部数据
     *
     * @param specification {@link Specification}
     * @param sort          {@link Sort}
     * @return 全部数据列表
     */
    default List<E> findAll(Specification<E> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    /**
     * 查询分页数据
     *
     * @param specification {@link Specification}
     * @param pageable      {@link Pageable}
     * @return 分页数据
     */
    default Page<E> findByPage(Specification<E> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }

    /**
     * 查询分页数据
     *
     * @param specification {@link Specification}
     * @param pageNumber    当前页码, 起始页码 0
     * @param pageSize      每页显示的数据条数
     * @return 分页数据
     */
    default Page<E> findByPage(Specification<E> specification, int pageNumber, int pageSize) {
        return getRepository().findAll(specification, PageRequest.of(pageNumber, pageSize));
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param direction  {@link Sort.Direction}
     * @return 分页数据
     */
    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction) {
        return findByPage(PageRequest.of(pageNumber, pageSize, direction));
    }

    /**
     * 根据ID查询数据
     * <p>
     * 该方法更类似于懒加载，与事务绑定紧密。
     * <p>
     * 参见 <a href="https://springdoc.cn/spring-data-jpa-getreferencebyid-findbyid-methods/">...</a>
     *
     * @param id 数据ID
     * @return 与ID对应的数据，如果不存在则返回空
     */
    default E getReferenceById(ID id) {
        return getRepository().getReferenceById(id);
    }

    /**
     * 数据是否存在
     *
     * @param specification {@link Specification}
     * @return true 存在，false 不存在
     */
    default boolean exists(Specification<E> specification) {
        return getRepository().exists(specification);
    }
}
