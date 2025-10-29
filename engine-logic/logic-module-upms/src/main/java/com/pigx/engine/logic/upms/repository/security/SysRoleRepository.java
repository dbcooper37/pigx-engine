package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;


public interface SysRoleRepository extends BaseJpaRepository<SysRole, String> {

    /**
     * 根据用户名查找SysUser
     *
     * @param roleCode 角色代码
     * @return {@link SysRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysRole findByRoleCode(String roleCode);

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return {@link SysRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysRole findByRoleId(String roleId);
}
