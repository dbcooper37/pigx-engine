package com.pigx.engine.logic.upms.entity.hr;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;


@Schema(name = "部门")
@Entity
@Table(name = "sys_department", indexes = {@Index(name = "sys_department_id_idx", columnList = "department_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "departmentId")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_DEPARTMENT)
public class SysDepartment extends AbstractSysEntity {

    @Schema(name = "部门ID")
    @Id
    @UuidGenerator
    @Column(name = "department_id", length = 64)
    private String departmentId;

    @Schema(name = "部门名称")
    @Column(name = "department_name", length = 200)
    private String departmentName;

    @Schema(name = "4A标准部门ID")
    @Column(name = "a4_biz_dept_id", length = 64)
    private String a4BizDeptId;

    @Schema(name = "标准部门代码")
    @Column(name = "biz_dept_code", length = 60)
    private String bizDeptCode;

    @Schema(name = "标准部门说明")
    @Column(name = "biz_dept_desc", length = 256)
    private String bizDeptDesc;

    @Schema(name = "标准部门ID")
    @Column(name = "biz_dept_id", length = 64)
    private String bizDeptId;

    @Schema(name = "标准部门名称")
    @Column(name = "biz_dept_name", length = 200)
    private String bizDeptName;

    @Schema(name = "标准部门类型")
    @Column(name = "biz_dept_type", length = 30)
    private String bizDeptType;

    @Schema(name = "分区代码")
    @Column(name = "partition_code", length = 256)
    private String partitionCode;

    @Schema(name = "部门简称")
    @Column(name = "short_name", length = 200)
    private String shortName;

    @Schema(name = "上级部门ID")
    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Schema(name = "所属单位ID")
    @Column(name = "organization_id", length = 64)
    private String organizationId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getA4BizDeptId() {
        return a4BizDeptId;
    }

    public void setA4BizDeptId(String a4BizDeptId) {
        this.a4BizDeptId = a4BizDeptId;
    }

    public String getBizDeptCode() {
        return bizDeptCode;
    }

    public void setBizDeptCode(String bizDeptCode) {
        this.bizDeptCode = bizDeptCode;
    }

    public String getBizDeptDesc() {
        return bizDeptDesc;
    }

    public void setBizDeptDesc(String bizDeptDesc) {
        this.bizDeptDesc = bizDeptDesc;
    }

    public String getBizDeptId() {
        return bizDeptId;
    }

    public void setBizDeptId(String bizDeptId) {
        this.bizDeptId = bizDeptId;
    }

    public String getBizDeptName() {
        return bizDeptName;
    }

    public void setBizDeptName(String bizDeptName) {
        this.bizDeptName = bizDeptName;
    }

    public String getBizDeptType() {
        return bizDeptType;
    }

    public void setBizDeptType(String bizDeptType) {
        this.bizDeptType = bizDeptType;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysDepartment that = (SysDepartment) o;
        return Objects.equal(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(departmentId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("departmentId", departmentId)
                .add("departmentName", departmentName)
                .add("a4BizDeptId", a4BizDeptId)
                .add("bizDeptCode", bizDeptCode)
                .add("bizDeptDesc", bizDeptDesc)
                .add("bizDeptId", bizDeptId)
                .add("bizDeptName", bizDeptName)
                .add("bizDeptType", bizDeptType)
                .add("partitionCode", partitionCode)
                .add("shortName", shortName)
                .add("parentId", parentId)
                .add("organizationId", organizationId)
                .toString();
    }
}
