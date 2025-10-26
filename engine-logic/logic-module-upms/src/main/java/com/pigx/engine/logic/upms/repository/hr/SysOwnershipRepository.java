package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysOwnership;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/hr/SysOwnershipRepository.class */
public interface SysOwnershipRepository extends BaseJpaRepository<SysOwnership, String> {
    @Modifying
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    @Query("delete from SysOwnership o where o.organizationId = :organizationId")
    void deleteByOrganizationId(String organizationId);

    @Modifying
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    @Query("delete from SysOwnership o where o.departmentId = :departmentId")
    void deleteByDepartmentId(String departmentId);

    @Modifying
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    @Query("delete from SysOwnership o where o.employeeId = :employeeId")
    void deleteByEmployeeId(String employeeId);

    @Modifying
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    @Query("delete from SysOwnership o where o.organizationId = :organizationId and o.departmentId = :departmentId and o.employeeId = :employeeId")
    void deleteByOrganizationIdAndDepartmentIdAndEmployeeId(String organizationId, String departmentId, String employeeId);
}
