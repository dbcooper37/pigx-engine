package com.pigx.engine.logic.identity.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;

import java.util.List;

/**
 * <p> Description : OauthScopeRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:57
 */
public interface OAuth2ScopeRepository extends BaseJpaRepository<OAuth2Scope, String> {

    /**
     * 根据范围代码查询应用范围
     *
     * @param scopeCode 范围代码
     * @return 应用范围 {@link OAuth2Scope}
     */
    OAuth2Scope findByScopeCode(String scopeCode);

    /**
     * 根据 scope codes 查询对应的对象列表
     *
     * @param scopeCodes 范围代码
     * @return 对象列表
     */
    List<OAuth2Scope> findByScopeCodeIn(List<String> scopeCodes);
}
