package com.pigx.engine.data.core.jpa.service;

import com.pigx.engine.core.definition.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;


public interface BaseJpaWriteableService<E extends BaseEntity, ID extends Serializable> extends BaseJpaReadableService<E, ID> {

    /**
     * 保存或更新数据
     *
     * @param domain 数据对应实体
     * @return 已保存数据
     */
    @Override
    default E save(E domain) {
        return getRepository().save(domain);
    }

    /**
     * 批量保存或更新数据
     *
     * @param entities 实体集合
     * @return 已经保存的实体集合
     */
    @Override
    default List<E> saveAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 删除实体对应的数据
     *
     * @param domain 数据对象实体
     */
    @Override
    default void delete(E domain) {
        getRepository().delete(domain);
    }

    /**
     * 根据 ID 删除
     *
     * @param id ID
     */
    @Override
    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 清空全部数据
     */
    @Override
    default void deleteAll() {
        getRepository().deleteAll();
    }

    /**
     * 批量删除指定的数据
     *
     * @param entities 数据实体 {@link Iterable}
     */
    @Override
    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 根据指定的 ID 批量删除数据。
     *
     * @param ids {@link Iterable}
     */
    @Override
    default void deleteAllById(Iterable<? extends ID> ids) {
        getRepository().deleteAllById(ids);
    }

    /**
     * 根据ID删除批量全部删除
     *
     * @param ids 数据ID
     */
    default void deleteAllByIdInBatch(Iterable<ID> ids) {
        getRepository().deleteAllByIdInBatch(ids);
    }

    /**
     * 批量全部删除
     */
    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }


    /**
     * 批量删除指定的实体
     *
     * @param entities 数据对应实体集合
     */
    default void deleteAllInBatch(Iterable<E> entities) {
        getRepository().deleteAllInBatch(entities);
    }


    /**
     * 保存或者更新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    default E saveAndFlush(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * 批量保存或者更新
     *
     * @param entities 实体列表
     * @return 保存或更新后的实体
     */
    default List<E> saveAllAndFlush(List<E> entities) {
        return getRepository().saveAllAndFlush(entities);
    }

    /**
     * 刷新实体状态
     */
    default void flush() {
        getRepository().flush();
    }
}
