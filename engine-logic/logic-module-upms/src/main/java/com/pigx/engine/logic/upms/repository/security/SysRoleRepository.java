package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/security/SysRoleRepository.class */
public interface SysRoleRepository extends BaseJpaRepository<SysRole, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysRole findByRoleCode(String roleCode);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysRole findByRoleId(String roleId);
}
