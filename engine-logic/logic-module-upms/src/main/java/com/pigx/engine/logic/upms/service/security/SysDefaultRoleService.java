package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysDefaultRole;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import com.pigx.engine.logic.upms.repository.security.SysDefaultRoleRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;


@Service
public class SysDefaultRoleService extends AbstractJpaService<SysDefaultRole, String> {

    private final SysDefaultRoleRepository sysDefaultRoleRepository;
    private final SysRoleService sysRoleService;

    public SysDefaultRoleService(SysDefaultRoleRepository sysDefaultRoleRepository, SysRoleService sysRoleService) {
        this.sysDefaultRoleRepository = sysDefaultRoleRepository;
        this.sysRoleService = sysRoleService;
    }

    @Override
    public BaseJpaRepository<SysDefaultRole, String> getRepository() {
        return this.sysDefaultRoleRepository;
    }

    public SysDefaultRole findByScene(AccountCategory scene) {
        return this.sysDefaultRoleRepository.findByScene(scene);
    }


    public SysDefaultRole assign(String defaultId, String roleId) {
        SysRole sysRole = sysRoleService.findByRoleId(roleId);
        SysDefaultRole sysDefaultRole = sysDefaultRoleRepository.findByDefaultId(defaultId);
        if (ObjectUtils.isNotEmpty(sysDefaultRole) && ObjectUtils.isNotEmpty(sysRole)) {
            sysDefaultRole.setRole(sysRole);
            return sysDefaultRoleRepository.saveAndFlush(sysDefaultRole);
        }

        return null;
    }
}
