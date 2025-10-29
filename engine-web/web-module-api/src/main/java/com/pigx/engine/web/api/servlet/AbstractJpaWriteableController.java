package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;

import java.io.Serializable;

/**
 * <p> Description : Jpa 可读可写基础 Controller 定义 </p>
 * <p>
 * 多定义一层抽象类，用于指定 {@link BaseJpaWriteableService} 类型，方便子类更加精确的定位类型。
 *
 * @param <E>  实体
 * @param <ID> 实体 ID
 * @author : gengwei.zheng
 * @date : 2020/2/29 15:28
 */
public abstract class AbstractJpaWriteableController<E extends BaseEntity, ID extends Serializable> extends AbstractWriteableController<E, ID, BaseJpaWriteableService<E, ID>> {

    @Override
    public Result<E> save(E domain) {
        E savedDomain = getService().saveAndFlush(domain);
        return result(savedDomain);
    }
}
