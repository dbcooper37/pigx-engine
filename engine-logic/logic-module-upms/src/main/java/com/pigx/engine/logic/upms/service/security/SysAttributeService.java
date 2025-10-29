package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import com.pigx.engine.logic.upms.repository.security.SysAttributeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class SysAttributeService extends AbstractJpaService<SysAttribute, String> {

    private final SysAttributeRepository sysAttributeRepository;

    public SysAttributeService(SysAttributeRepository sysAttributeRepository) {
        this.sysAttributeRepository = sysAttributeRepository;
    }

    @Override
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

        Optional<SysAttribute> sysAttribute = findById(attributeId);
        return sysAttribute.map(data -> {
                    data.setPermissions(sysPermissions);
                    return data;
                })
                .map(this::saveAndFlush)
                .orElse(null);
    }

    public List<SysAttribute> findAllByServiceId(String serviceId) {
        return sysAttributeRepository.findAllByServiceId(serviceId);
    }

    public List<SysAttribute> findByAttributeIdIn(List<String> ids) {
        return sysAttributeRepository.findByAttributeIdIn(ids);
    }
}
