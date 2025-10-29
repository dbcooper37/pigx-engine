package com.pigx.engine.logic.identity.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;


public interface OAuth2PermissionRepository extends BaseJpaRepository<OAuth2Permission, String> {
}
