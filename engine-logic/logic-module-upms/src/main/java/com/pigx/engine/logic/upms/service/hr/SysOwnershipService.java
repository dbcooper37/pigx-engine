package com.pigx.engine.logic.upms.service.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.hr.SysOwnership;
import com.pigx.engine.logic.upms.repository.hr.SysOwnershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class SysOwnershipService extends AbstractJpaService<SysOwnership, String> {

    private static final Logger log = LoggerFactory.getLogger(SysOwnershipService.class);

    private final SysOwnershipRepository sysOwnershipRepository;

    public SysOwnershipService(SysOwnershipRepository sysOwnershipRepository) {
        this.sysOwnershipRepository = sysOwnershipRepository;
    }

    @Override
    public BaseJpaRepository<SysOwnership, String> getRepository() {
        return this.sysOwnershipRepository;
    }

    public void deleteByOrganizationId(String organizationId) {
        sysOwnershipRepository.deleteByOrganizationId(organizationId);
    }

    public void deleteByDepartmentId(String departmentId) {
        sysOwnershipRepository.deleteByDepartmentId(departmentId);
    }

    public void deleteByEmployeeId(String employeeId) {
        sysOwnershipRepository.deleteByEmployeeId(employeeId);
    }

    public void delete(String organizationId, String departmentId, String employeeId) {
        sysOwnershipRepository.deleteByOrganizationIdAndDepartmentIdAndEmployeeId(organizationId, departmentId, employeeId);
    }
}
