package com.pigx.engine.logic.upms.entity.hr;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_DEPARTMENT)
@Schema(name = "部门")
@Cacheable
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "departmentId")
@Table(name = "sys_department", indexes = {@Index(name = "sys_department_id_idx", columnList = "department_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/hr/SysDepartment.class */
public class SysDepartment extends AbstractSysEntity {

    @Id
    @Schema(name = "部门ID")
    @UuidGenerator
    @Column(name = "department_id", length = 64)
    private String departmentId;

    @Column(name = "department_name", length = 200)
    @Schema(name = "部门名称")
    private String departmentName;

    @Column(name = "a4_biz_dept_id", length = 64)
    @Schema(name = "4A标准部门ID")
    private String a4BizDeptId;

    @Column(name = "biz_dept_code", length = ErrorCodeMapperBuilderOrdered.ACCESS)
    @Schema(name = "标准部门代码")
    private String bizDeptCode;

    @Column(name = "biz_dept_desc", length = 256)
    @Schema(name = "标准部门说明")
    private String bizDeptDesc;

    @Column(name = "biz_dept_id", length = 64)
    @Schema(name = "标准部门ID")
    private String bizDeptId;

    @Column(name = "biz_dept_name", length = 200)
    @Schema(name = "标准部门名称")
    private String bizDeptName;

    @Column(name = "biz_dept_type", length = ErrorCodeMapperBuilderOrdered.OAUTH2)
    @Schema(name = "标准部门类型")
    private String bizDeptType;

    @Column(name = "partition_code", length = 256)
    @Schema(name = "分区代码")
    private String partitionCode;

    @Column(name = "short_name", length = 200)
    @Schema(name = "部门简称")
    private String shortName;

    @Column(name = "parent_id", length = 64)
    @Schema(name = "上级部门ID")
    private String parentId;

    @Column(name = "organization_id", length = 64)
    @Schema(name = "所属单位ID")
    private String organizationId;

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getA4BizDeptId() {
        return this.a4BizDeptId;
    }

    public void setA4BizDeptId(String a4BizDeptId) {
        this.a4BizDeptId = a4BizDeptId;
    }

    public String getBizDeptCode() {
        return this.bizDeptCode;
    }

    public void setBizDeptCode(String bizDeptCode) {
        this.bizDeptCode = bizDeptCode;
    }

    public String getBizDeptDesc() {
        return this.bizDeptDesc;
    }

    public void setBizDeptDesc(String bizDeptDesc) {
        this.bizDeptDesc = bizDeptDesc;
    }

    public String getBizDeptId() {
        return this.bizDeptId;
    }

    public void setBizDeptId(String bizDeptId) {
        this.bizDeptId = bizDeptId;
    }

    public String getBizDeptName() {
        return this.bizDeptName;
    }

    public void setBizDeptName(String bizDeptName) {
        this.bizDeptName = bizDeptName;
    }

    public String getBizDeptType() {
        return this.bizDeptType;
    }

    public void setBizDeptType(String bizDeptType) {
        this.bizDeptType = bizDeptType;
    }

    public String getPartitionCode() {
        return this.partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysDepartment that = (SysDepartment) o;
        return Objects.equal(this.departmentId, that.departmentId);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.departmentId});
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("departmentId", this.departmentId).add("departmentName", this.departmentName).add("a4BizDeptId", this.a4BizDeptId).add("bizDeptCode", this.bizDeptCode).add("bizDeptDesc", this.bizDeptDesc).add("bizDeptId", this.bizDeptId).add("bizDeptName", this.bizDeptName).add("bizDeptType", this.bizDeptType).add("partitionCode", this.partitionCode).add("shortName", this.shortName).add("parentId", this.parentId).add("organizationId", this.organizationId).toString();
    }
}
