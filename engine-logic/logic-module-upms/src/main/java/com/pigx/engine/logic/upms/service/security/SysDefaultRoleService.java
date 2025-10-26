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
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/security/SysDefaultRoleService.class */
public class SysDefaultRoleService extends AbstractJpaService<SysDefaultRole, String> {
    private final SysDefaultRoleRepository sysDefaultRoleRepository;
    private final SysRoleService sysRoleService;

    public SysDefaultRoleService(SysDefaultRoleRepository sysDefaultRoleRepository, SysRoleService sysRoleService) {
        this.sysDefaultRoleRepository = sysDefaultRoleRepository;
        this.sysRoleService = sysRoleService;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysDefaultRole, String> getRepository() {
        return this.sysDefaultRoleRepository;
    }

    public SysDefaultRole findByScene(AccountCategory scene) {
        return this.sysDefaultRoleRepository.findByScene(scene);
    }

    public SysDefaultRole assign(String defaultId, String roleId) {
        SysRole sysRole = this.sysRoleService.findByRoleId(roleId);
        SysDefaultRole sysDefaultRole = this.sysDefaultRoleRepository.findByDefaultId(defaultId);
        if (ObjectUtils.isNotEmpty(sysDefaultRole) && ObjectUtils.isNotEmpty(sysRole)) {
            sysDefaultRole.setRole(sysRole);
            return (SysDefaultRole) this.sysDefaultRoleRepository.saveAndFlush(sysDefaultRole);
        }
        return null;
    }
}
