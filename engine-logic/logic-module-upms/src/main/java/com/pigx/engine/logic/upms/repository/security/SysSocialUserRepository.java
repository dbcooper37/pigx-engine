package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysSocialUser;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/security/SysSocialUserRepository.class */
public interface SysSocialUserRepository extends BaseJpaRepository<SysSocialUser, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysSocialUser findSysSocialUserByUuidAndSource(String uuid, String source);
}
