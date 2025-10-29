package com.pigx.engine.rest.servlet.upms.dto;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.domain.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(description = "删除人员归属参数BO对象")
public class AllocatableRemove extends AbstractDto {

    @NotNull(message = "单位ID不能为空")
    @Schema(description = "单位ID")
    private String organizationId;

    @NotNull(message = "部门ID不能为空")
    @Schema(description = "部门ID")
    private String departmentId;

    @NotNull(message = "人员ID不能为空")
    @Schema(description = "人员ID")
    private String employeeId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("organizationId", organizationId)
                .add("departmentId", departmentId)
                .add("employeeId", employeeId)
                .toString();
    }
}
