package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.web.core.definition.Controller;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.Map;

/**
 * <p> Description : 可分页基础 Controller </p>
 * <p>
 * 提供 {@link Page} 和 {@link Slice} 分页结果返回处理
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 18:56
 */
public interface PaginationController extends Controller {

    /**
     * 数据分页对象转换为统一响应实体
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link BaseEntity} 子类型
     * @return {@link Result} Map
     */
    default <E extends BaseEntity> Result<Map<String, Object>> resultFromPage(Page<E> pages) {
        if (null == pages) {
            return Result.failure("查询数据失败！");
        }

        if (CollectionUtils.isNotEmpty(pages.getContent())) {
            return Result.success("查询数据成功！", fromPage(pages));
        } else {
            return Result.empty("未查询到数据！");
        }
    }

    /**
     * 数据切片对象转换为统一响应实体
     *
     * @param slices 分页查询结果 {@link Slice}
     * @param <E>    {@link BaseEntity} 子类型
     * @return {@link Result} Map
     */
    default <E extends BaseEntity> Result<Map<String, Object>> resultFromSlice(Slice<E> slices) {
        if (null == slices) {
            return Result.failure("查询数据失败！");
        }

        if (CollectionUtils.isNotEmpty(slices.getContent())) {
            return Result.success("查询数据成功！", fromSlice(slices));
        } else {
            return Result.empty("未查询到数据！");
        }
    }

    /**
     * Page 对象转换为 Map
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link BaseEntity} 子类型
     * @return Map
     */
    default <E extends BaseEntity> Map<String, Object> fromPage(Page<E> pages) {
        return with(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }

    /**
     * Slice 对象转换为 Map
     *
     * @param slices 分页查询结果 {@link Slice}
     * @param <E>    {@link BaseEntity} 子类型
     * @return Map
     */
    default <E extends BaseEntity> Map<String, Object> fromSlice(Slice<E> slices) {
        return with(slices.getContent(), slices.hasNext(), slices.hasPrevious());
    }
}
