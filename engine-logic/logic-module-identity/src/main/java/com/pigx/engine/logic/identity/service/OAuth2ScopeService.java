package com.pigx.engine.logic.identity.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.logic.identity.repository.OAuth2ScopeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <p> Description : OauthScopeService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 17:00
 */
@Service
public class OAuth2ScopeService extends AbstractJpaService<OAuth2Scope, String> {

    private final OAuth2ScopeRepository oauthScopesRepository;

    public OAuth2ScopeService(OAuth2ScopeRepository oauthScopesRepository) {
        this.oauthScopesRepository = oauthScopesRepository;
    }

    @Override
    public BaseJpaRepository<OAuth2Scope, String> getRepository() {
        return oauthScopesRepository;
    }

    public OAuth2Scope assigned(String scopeId, Set<OAuth2Permission> permissions) {

        Optional<OAuth2Scope> oldScope = findById(scopeId);

        return oldScope.map(entity -> {
                    entity.setPermissions(permissions);
                    return entity;
                }).map(this::saveAndFlush)
                .orElse(null);
    }

    public OAuth2Scope findByScopeCode(String scopeCode) {
        return oauthScopesRepository.findByScopeCode(scopeCode);
    }

    public List<OAuth2Scope> findByScopeCodeIn(List<String> scopeCodes) {
        return oauthScopesRepository.findByScopeCodeIn(scopeCodes);
    }
}
