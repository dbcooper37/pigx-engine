package com.pigx.engine.logic.identity.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;
import com.pigx.engine.logic.identity.repository.OAuth2PermissionRepository;
import org.springframework.stereotype.Service;


@Service
public class OAuth2PermissionService extends AbstractJpaService<OAuth2Permission, String> {

    private final OAuth2PermissionRepository authorityRepository;

    public OAuth2PermissionService(OAuth2PermissionRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public BaseJpaRepository<OAuth2Permission, String> getRepository() {
        return authorityRepository;
    }
}
