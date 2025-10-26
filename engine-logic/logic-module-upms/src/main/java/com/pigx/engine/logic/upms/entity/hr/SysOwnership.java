package com.pigx.engine.logic.upms.entity.hr;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_OWNERSHIP)
@Schema(name = "人事归属")
@Cacheable
@Entity
@Table(name = "sys_ownership", indexes = {@Index(name = "sys_ownership_id_idx", columnList = "ownership_id"), @Index(name = "sys_ownership_oid_idx", columnList = "organization_id"), @Index(name = "sys_ownership_did_idx", columnList = "department_id"), @Index(name = "sys_ownership_eid_idx", columnList = "employee_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/hr/SysOwnership.class */
public class SysOwnership extends AbstractSysEntity {

    @Id
    @Schema(name = "人员ID")
    @UuidGenerator
    @Column(name = "ownership_id", length = 64)
    private String ownershipId;

    @Column(name = "organization_id", length = 64)
    @Schema(name = "所属单位ID")
    private String organizationId;

    @Column(name = "department_id", length = 64)
    @Schema(name = "所属部门ID")
    private String departmentId;

    @Column(name = "employee_id", length = 64)
    private String employeeId;

    public String getOwnershipId() {
        return this.ownershipId;
    }

    public void setOwnershipId(String ownershipId) {
        this.ownershipId = ownershipId;
    }

    public String getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("ownershipId", this.ownershipId).add("organizationId", this.organizationId).add("departmentId", this.departmentId).add(SystemConstants.EMPLOYEE_ID, this.employeeId).toString();
    }
}
