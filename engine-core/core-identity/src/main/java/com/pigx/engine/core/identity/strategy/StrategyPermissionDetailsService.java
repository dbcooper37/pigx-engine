package com.pigx.engine.core.identity.strategy;

import com.pigx.engine.core.identity.domain.HerodotusPermission;

import java.util.List;


public interface StrategyPermissionDetailsService {

    /**
     * 获取全部权限
     *
     * @return 权限集合
     */
    List<HerodotusPermission> findAll();
}
