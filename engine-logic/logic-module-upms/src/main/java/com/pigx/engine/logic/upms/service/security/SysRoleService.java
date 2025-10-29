package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import com.pigx.engine.logic.upms.repository.security.SysRoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class SysRoleService extends AbstractJpaService<SysRole, String> {

    private final SysRoleRepository sysRoleRepository;

    public SysRoleService(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    @Override
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

        Optional<SysRole> sysRole = findById(roleId);

        return sysRole.map(data -> {
                    data.setPermissions(sysPermissions);
                    return data;
                })
                .map(this::saveAndFlush)
                .orElse(null);
    }

    public SysRole findByRoleCode(String roleCode) {
        return sysRoleRepository.findByRoleCode(roleCode);
    }

    public SysRole findByRoleId(String roleId) {
        return sysRoleRepository.findByRoleId(roleId);
    }
}
