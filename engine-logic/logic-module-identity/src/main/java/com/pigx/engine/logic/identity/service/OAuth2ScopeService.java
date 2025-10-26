package com.pigx.engine.logic.identity.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.logic.identity.repository.OAuth2ScopeRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/service/OAuth2ScopeService.class */
public class OAuth2ScopeService extends AbstractJpaService<OAuth2Scope, String> {
    private final OAuth2ScopeRepository oauthScopesRepository;

    public OAuth2ScopeService(OAuth2ScopeRepository oauthScopesRepository) {
        this.oauthScopesRepository = oauthScopesRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<OAuth2Scope, String> getRepository() {
        return this.oauthScopesRepository;
    }

    public OAuth2Scope assigned(String scopeId, Set<OAuth2Permission> permissions) {
        return (OAuth2Scope) findById(scopeId).map(entity -> {
            entity.setPermissions(permissions);
            return entity;
        }).map((v1) -> {
            return saveAndFlush(v1);
        }).orElse(null);
    }

    public OAuth2Scope findByScopeCode(String scopeCode) {
        return this.oauthScopesRepository.findByScopeCode(scopeCode);
    }

    public List<OAuth2Scope> findByScopeCodeIn(List<String> scopeCodes) {
        return this.oauthScopesRepository.findByScopeCodeIn(scopeCodes);
    }
}
