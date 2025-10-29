package com.pigx.engine.core.identity.service;

import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;

import java.util.Set;


public interface ClientDetailsService {

    /**
     * 根据客户端ID获取客户端权限
     *
     * @param clientId 客户端ID
     * @return 客户端权限集合
     */
    Set<HerodotusGrantedAuthority> findAuthoritiesById(String clientId);
}
