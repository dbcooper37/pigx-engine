package com.pigx.engine.logic.identity.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;
import com.pigx.engine.logic.identity.repository.OAuth2PermissionRepository;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/service/OAuth2PermissionService.class */
public class OAuth2PermissionService extends AbstractJpaService<OAuth2Permission, String> {
    private final OAuth2PermissionRepository authorityRepository;

    public OAuth2PermissionService(OAuth2PermissionRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<OAuth2Permission, String> getRepository() {
        return this.authorityRepository;
    }
}
