package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysOwnership;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface SysOwnershipRepository extends BaseJpaRepository<SysOwnership, String> {

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param organizationId 单位ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.organizationId = :organizationId")
    void deleteByOrganizationId(String organizationId);

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param departmentId 部门ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.departmentId = :departmentId")
    void deleteByDepartmentId(String departmentId);

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param employeeId 人员ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.employeeId = :employeeId")
    void deleteByEmployeeId(String employeeId);

    /**
     * 删除人事归属
     *
     * @param organizationId 单位ID
     * @param departmentId   部门ID
     * @param employeeId     人员ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.organizationId = :organizationId and o.departmentId = :departmentId and o.employeeId = :employeeId")
    void deleteByOrganizationIdAndDepartmentIdAndEmployeeId(String organizationId, String departmentId, String employeeId);

}
