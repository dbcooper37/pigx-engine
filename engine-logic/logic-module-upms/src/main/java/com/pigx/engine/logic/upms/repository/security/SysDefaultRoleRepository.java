package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysDefaultRole;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/security/SysDefaultRoleRepository.class */
public interface SysDefaultRoleRepository extends BaseJpaRepository<SysDefaultRole, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysDefaultRole findByScene(AccountCategory scene);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysDefaultRole findByDefaultId(String defaultId);
}
