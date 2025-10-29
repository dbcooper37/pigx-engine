package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysDefaultRole;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;


public interface SysDefaultRoleRepository extends BaseJpaRepository<SysDefaultRole, String> {

    /**
     * 根据场景查询当前场景对应的默认角色
     *
     * @param scene 场景 {@link AccountCategory}
     * @return {@link SysDefaultRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysDefaultRole findByScene(AccountCategory scene);

    /**
     * 根据默认角色ID查询默认角色
     *
     * @param defaultId 默认角色ID
     * @return {@link SysDefaultRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysDefaultRole findByDefaultId(String defaultId);
}
