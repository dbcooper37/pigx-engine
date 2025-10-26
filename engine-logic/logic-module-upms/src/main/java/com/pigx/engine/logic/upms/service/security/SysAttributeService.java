package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import com.pigx.engine.logic.upms.repository.security.SysAttributeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/security/SysAttributeService.class */
public class SysAttributeService extends AbstractJpaService<SysAttribute, String> {
    private final SysAttributeRepository sysAttributeRepository;

    public SysAttributeService(SysAttributeRepository sysAttributeRepository) {
        this.sysAttributeRepository = sysAttributeRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysAttribute, String> getRepository() {
        return this.sysAttributeRepository;
    }

    public SysAttribute assign(String attributeId, String[] permissionIds) {
        Set<SysPermission> sysPermissions = new HashSet<>();
        for (String permissionId : permissionIds) {
            SysPermission sysPermission = new SysPermission();
            sysPermission.setPermissionId(permissionId);
            sysPermissions.add(sysPermission);
        }
        return (SysAttribute) findById(attributeId).map(data -> {
            data.setPermissions(sysPermissions);
            return data;
        }).map((v1) -> {
            return saveAndFlush(v1);
        }).orElse(null);
    }

    public List<SysAttribute> findAllByServiceId(String serviceId) {
        return this.sysAttributeRepository.findAllByServiceId(serviceId);
    }

    public List<SysAttribute> findByAttributeIdIn(List<String> ids) {
        return this.sysAttributeRepository.findByAttributeIdIn(ids);
    }
}
