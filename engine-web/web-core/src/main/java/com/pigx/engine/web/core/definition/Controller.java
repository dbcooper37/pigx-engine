package com.pigx.engine.web.core.definition;

import com.pigx.engine.core.definition.domain.BaseDomain;
import com.pigx.engine.core.definition.domain.Pagination;
import com.pigx.engine.core.definition.domain.Result;
import cn.hutool.v7.core.tree.MapTree;
import cn.hutool.v7.core.tree.TreeNode;
import cn.hutool.v7.core.tree.TreeUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/definition/Controller.class */
public interface Controller extends Pagination {
    default <D extends BaseDomain> Result<D> result(D domain) {
        if (ObjectUtils.isNotEmpty(domain)) {
            return Result.content(domain);
        }
        return Result.empty();
    }

    default <D extends BaseDomain> Result<List<D>> result(List<D> domains) {
        if (null == domains) {
            return Result.failure("查询数据失败！");
        }
        if (CollectionUtils.isNotEmpty(domains)) {
            return Result.success("查询数据成功！", domains);
        }
        return Result.empty("未查询到数据！");
    }

    default <T> Result<T[]> result(T[] domains) {
        if (null == domains) {
            return Result.failure("查询数据失败！");
        }
        if (ArrayUtils.isNotEmpty(domains)) {
            return Result.success("查询数据成功！", domains);
        }
        return Result.empty("未查询到数据！");
    }

    default Result<Map<String, Object>> result(Map<String, Object> map) {
        if (null == map) {
            return Result.failure("查询数据失败！");
        }
        if (MapUtils.isNotEmpty(map)) {
            return Result.success("查询数据成功！", map);
        }
        return Result.empty("未查询到数据！");
    }

    default Result<String> result(String parameter) {
        if (ObjectUtils.isNotEmpty(parameter)) {
            return Result.success("操作成功!", parameter);
        }
        return Result.failure("操作失败!", parameter);
    }

    default Result<Boolean> result(boolean status) {
        if (status) {
            return Result.success("操作成功!", true);
        }
        return Result.failure("操作失败!", false);
    }

    default <D extends BaseDomain> Result<List<MapTree<String>>> result(List<D> domains, Converter<D, TreeNode<String>> toTreeNode) {
        if (ObjectUtils.isNotEmpty(domains)) {
            Stream<D> stream = domains.stream();
            Objects.requireNonNull(toTreeNode);
            List<TreeNode<String>> treeNodes = (List) stream.map((v1) -> {
                return r1.convert(v1);
            }).collect(Collectors.toList());
            return Result.success("查询数据成功", TreeUtil.build(treeNodes, "0"));
        }
        return Result.empty("未查询到数据！");
    }
}
