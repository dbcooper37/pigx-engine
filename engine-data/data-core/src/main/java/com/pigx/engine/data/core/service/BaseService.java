package com.pigx.engine.data.core.service;

import com.pigx.engine.core.definition.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public interface BaseService<E extends BaseEntity, ID extends Serializable> {

    /**
     * 查询全部
     *
     * @return 全部数据列表
     */
    default List<E> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据 ID 查询
     *
     * @param id ID
     * @return 数据对象
     */
    default Optional<E> findById(ID id) {
        throw new UnsupportedOperationException();
    }


    /**
     * 查询多个 ID 对应的数据
     *
     * @param ids {@link Iterable}
     * @return 数据对象 {@link List}
     */
    default List<E> findAllById(Iterable<ID> ids) {
        throw new UnsupportedOperationException();
    }

    /**
     * 数据是否存在
     *
     * @param id 数据ID
     * @return true 存在，false 不存在
     */
    default boolean existsById(ID id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 保存或更新数据
     *
     * @param domain 对应的实体
     * @return 保存后的实体
     */
    default E save(E domain) {
        throw new UnsupportedOperationException();
    }

    /**
     * 批量保存数据
     *
     * @param entities 数据实体 {@link Iterable}
     * @return 保存后的实体集合 {@link List}
     */
    default List<E> saveAll(Iterable<E> entities) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除实体对应的数据
     *
     * @param domain 数据对象实体
     */
    default void delete(E domain) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据 ID 删除
     *
     * @param id ID
     */
    default void deleteById(ID id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 清空全部数据
     */
    default void deleteAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * 批量删除指定的数据
     *
     * @param entities 数据实体 {@link Iterable}
     */
    default void deleteAll(Iterable<E> entities) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据指定的 ID 批量删除数据。
     *
     * @param ids {@link Iterable}
     */
    default void deleteAllById(Iterable<? extends ID> ids) {
        throw new UnsupportedOperationException();
    }
}
