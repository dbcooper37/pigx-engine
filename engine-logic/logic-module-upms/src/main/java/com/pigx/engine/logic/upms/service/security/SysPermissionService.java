package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import com.pigx.engine.logic.upms.repository.security.SysPermissionRepository;
import org.springframework.stereotype.Service;


@Service
public class SysPermissionService extends AbstractJpaService<SysPermission, String> {

    private final SysPermissionRepository sysPermissionRepository;

    public SysPermissionService(SysPermissionRepository sysPermissionRepository) {
        this.sysPermissionRepository = sysPermissionRepository;
    }

    @Override
    public BaseJpaRepository<SysPermission, String> getRepository() {
        return this.sysPermissionRepository;
    }
}
