package com.pigx.engine.logic.identity.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import java.util.List;

/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/repository/OAuth2ScopeRepository.class */
public interface OAuth2ScopeRepository extends BaseJpaRepository<OAuth2Scope, String> {
    OAuth2Scope findByScopeCode(String scopeCode);

    List<OAuth2Scope> findByScopeCodeIn(List<String> scopeCodes);
}
