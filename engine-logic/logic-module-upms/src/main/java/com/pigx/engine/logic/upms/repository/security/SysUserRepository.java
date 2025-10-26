package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/security/SysUserRepository.class */
public interface SysUserRepository extends BaseJpaRepository<SysUser, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysUser findByUsername(String username);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysUser findByUserId(String userId);
}
