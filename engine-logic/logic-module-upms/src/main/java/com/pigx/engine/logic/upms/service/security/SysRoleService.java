package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import com.pigx.engine.logic.upms.repository.security.SysRoleRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/security/SysRoleService.class */
public class SysRoleService extends AbstractJpaService<SysRole, String> {
    private final SysRoleRepository sysRoleRepository;

    public SysRoleService(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysRole, String> getRepository() {
        return this.sysRoleRepository;
    }

    public SysRole assign(String roleId, String[] permissions) {
        Set<SysPermission> sysPermissions = new HashSet<>();
        for (String permission : permissions) {
            SysPermission sysPermission = new SysPermission();
            sysPermission.setPermissionId(permission);
            sysPermissions.add(sysPermission);
        }
        return (SysRole) findById(roleId).map(data -> {
            data.setPermissions(sysPermissions);
            return data;
        }).map((v1) -> {
            return saveAndFlush(v1);
        }).orElse(null);
    }

    public SysRole findByRoleCode(String roleCode) {
        return this.sysRoleRepository.findByRoleCode(roleCode);
    }

    public SysRole findByRoleId(String roleId) {
        return this.sysRoleRepository.findByRoleId(roleId);
    }
}
