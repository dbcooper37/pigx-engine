package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.service.BaseService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public interface BindingController<E extends BaseEntity, ID extends Serializable, S extends BaseService<E, ID>> extends PaginationController {

    /**
     * 获取 Service
     *
     * @return Service
     */
    S getService();

    /**
     * 查询所有数据
     *
     * @return 包装成 {@link Result} 的 {@link List} 类型查询结果
     */
    default Result<List<E>> findAll() {
        List<E> domains = getService().findAll();
        return result(domains);
    }

    /**
     * 根据实体 ID 查询指定实体数据
     *
     * @param id 实体Id
     * @return 装成 {@link Result} 的查询结果
     */
    default Result<E> findById(ID id) {
        Optional<E> domain = getService().findById(id);
        return result(domain.orElse(null));
    }

    /**
     * 保存或更新实体
     *
     * @param domain 实体参数
     * @return 用Result包装的实体
     */
    default Result<E> save(E domain) {
        E savedDomain = getService().save(domain);
        return result(savedDomain);
    }

    /**
     * 删除数据
     *
     * @param id 实体ID
     * @return 包装成 {@link Result} 的 String 类型查询结果。JPA 删除操作没有返回值，所以无法判断操作成功与否。
     */
    default Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getService().deleteById(id);
        return result;
    }
}
