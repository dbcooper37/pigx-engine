package com.pigx.engine.rest.servlet.upms.controller.hr;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.hr.SysEmployee;
import com.pigx.engine.logic.upms.enums.Gender;
import com.pigx.engine.logic.upms.enums.Identity;
import com.pigx.engine.logic.upms.service.hr.SysEmployeeService;
import com.pigx.engine.rest.servlet.upms.dto.AllocatableDeploy;
import com.pigx.engine.rest.servlet.upms.dto.AllocatableRemove;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/hr/employee"})
@RestController
@Tag(name = "人员管理接口")
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/hr/SysEmployeeController.class */
public class SysEmployeeController extends AbstractJpaWriteableController<SysEmployee, String> {
    private final SysEmployeeService sysEmployeeService;

    public SysEmployeeController(SysEmployeeService sysEmployeeService) {
        this.sysEmployeeService = sysEmployeeService;
    }

    private Gender parseGender(Integer gender) {
        if (ObjectUtils.isNotEmpty(gender)) {
            return Gender.get(gender);
        }
        return null;
    }

    private Identity parseIdentity(Integer identity) {
        if (ObjectUtils.isNotEmpty(identity)) {
            return Identity.get(identity);
        }
        return null;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysEmployee, String> getService() {
        return this.sysEmployeeService;
    }

    @GetMapping({"/condition"})
    @Operation(summary = "模糊条件查询人员", description = "根据动态输入的字段模糊查询人员信息", responses = {@ApiResponse(description = "人员分页列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})})
    @Parameters({@Parameter(name = "pageNumber", required = true, description = "当前页码"), @Parameter(name = "pageSize", required = true, description = "每页显示数量"), @Parameter(name = "employeeName", description = "人员姓名"), @Parameter(name = "mobilePhoneNumber", description = "手机号码"), @Parameter(name = "officePhoneNumber", description = "办公电话"), @Parameter(name = SystemConstants.SCOPE_EMAIL, description = "电子邮件"), @Parameter(name = "pkiEmail", description = "证书标识"), @Parameter(name = "gender", description = "性别 （索引数字值）"), @Parameter(name = "identity", description = "身份（索引数字值）")})
    public Result<Map<String, Object>> findByCondition(@RequestParam("pageNumber") @NotNull Integer pageNumber, @RequestParam("pageSize") @NotNull Integer pageSize, @RequestParam(value = "employeeName", required = false) String employeeName, @RequestParam(value = "mobilePhoneNumber", required = false) String mobilePhoneNumber, @RequestParam(value = "officePhoneNumber", required = false) String officePhoneNumber, @RequestParam(value = SystemConstants.SCOPE_EMAIL, required = false) String email, @RequestParam(value = "pkiEmail", required = false) String pkiEmail, @RequestParam(value = "gender", required = false) Integer gender, @RequestParam(value = "identity", required = false) Integer identity) {
        Page<SysEmployee> pages = this.sysEmployeeService.findByCondition(pageNumber.intValue(), pageSize.intValue(), employeeName, mobilePhoneNumber, officePhoneNumber, email, pkiEmail, parseGender(gender), parseIdentity(identity));
        return resultFromPage(pages);
    }

    @PutMapping
    @Operation(summary = "给人员分配用户", description = "为人员创建用户，生成默认用户信息，让人员可以进入系统", responses = {@ApiResponse(description = "已分配用户的人员信息", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysEmployee.class))})})
    @Parameters({@Parameter(name = SystemConstants.EMPLOYEE_ID, description = "人员ID")})
    public Result<SysEmployee> authorize(@RequestParam(SystemConstants.EMPLOYEE_ID) String employeeId) {
        SysEmployee sysEmployee = this.sysEmployeeService.authorize(employeeId);
        return result((SysEmployeeController) sysEmployee);
    }

    @GetMapping({"/allocatable"})
    @Operation(summary = "查询可设置人事归属的人员", description = "根据输入的单位和部门，分页查询当前部门下未设置人事归属的人员信息，排除了已经设置的人员信息", responses = {@ApiResponse(description = "可配置人员分页列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})})
    @Parameters({@Parameter(name = "pageNumber", required = true, description = "当前页码"), @Parameter(name = "pageSize", required = true, description = "每页显示数量"), @Parameter(name = "organizationId", required = true, description = "单位ID"), @Parameter(name = "departmentId", required = true, description = "部门ID"), @Parameter(name = "employeeName", description = "人员姓名"), @Parameter(name = "mobilePhoneNumber", description = "手机号码"), @Parameter(name = SystemConstants.SCOPE_EMAIL, description = "电子邮件"), @Parameter(name = "gender", description = "性别 （索引数字值）"), @Parameter(name = "identity", description = "身份（索引数字值）")})
    public Result<Map<String, Object>> findAllocatable(@RequestParam("pageNumber") @NotNull Integer pageNumber, @RequestParam("pageSize") @NotNull Integer pageSize, @RequestParam("organizationId") @NotBlank String organizationId, @RequestParam("departmentId") @NotBlank String departmentId, @RequestParam(value = "employeeName", required = false) String employeeName, @RequestParam(value = "mobilePhoneNumber", required = false) String mobilePhoneNumber, @RequestParam(value = SystemConstants.SCOPE_EMAIL, required = false) String email, @RequestParam(value = "gender", required = false) Integer gender, @RequestParam(value = "identity", required = false) Integer identity) {
        Page<SysEmployee> pages = this.sysEmployeeService.findAllocatable(pageNumber.intValue(), pageSize.intValue(), organizationId, departmentId, employeeName, mobilePhoneNumber, email, parseGender(gender), parseIdentity(identity));
        return resultFromPage(pages);
    }

    @GetMapping({"/assigned"})
    @Operation(summary = "查询已设置归属关系的人员", description = "根据输入的部门，分页查询当前部门下已设置人事归属的人员信息", responses = {@ApiResponse(description = "可配置人员分页列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})})
    @Parameters({@Parameter(name = "pageNumber", required = true, description = "当前页码"), @Parameter(name = "pageSize", required = true, description = "每页显示数量"), @Parameter(name = "departmentId", required = true, description = "部门ID")})
    public Result<Map<String, Object>> findAssigned(@RequestParam("pageNumber") @NotNull Integer pageNumber, @RequestParam("pageSize") @NotNull Integer pageSize, @RequestParam("departmentId") @NotBlank String departmentId) {
        Page<SysEmployee> pages = this.sysEmployeeService.findByDepartmentId(pageNumber.intValue(), pageSize.intValue(), departmentId);
        return resultFromPage(pages);
    }

    @PostMapping({"/allocatable"})
    @Operation(summary = "设置人事归属", description = "根据输入的单位和部门，设置当前部门下未设置人事归属的人员信息，排除了已经设置的人员信息", requestBody = @RequestBody(content = {@Content(mediaType = "application/json")}), responses = {@ApiResponse(description = "是否设置成功", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "allocatableDeploy", required = true, description = "当前页码", schema = @Schema(implementation = AllocatableDeploy.class))})
    public Result<Boolean> saveAllocatable(@org.springframework.web.bind.annotation.RequestBody AllocatableDeploy allocatableDeploy) {
        boolean isSuccess;
        if (ObjectUtils.isNotEmpty(allocatableDeploy)) {
            isSuccess = this.sysEmployeeService.deployAllocatable(allocatableDeploy.getEmployees(), allocatableDeploy.getOwnerships());
        } else {
            isSuccess = false;
        }
        return result(isSuccess);
    }

    @DeleteMapping({"/allocatable"})
    @Operation(summary = "删除人员归属关系", description = "根据归属关系、部门和人员的ID，删除归属关系以及人员与部门之间的关联关系", requestBody = @RequestBody(content = {@Content(mediaType = "application/json")}), responses = {@ApiResponse(description = "是否删除成功", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "allocatableRemove", required = true, description = "增加人员归属参数BO对象", schema = @Schema(implementation = AllocatableRemove.class))})
    public Result<Boolean> deleteAllocatable(@org.springframework.web.bind.annotation.RequestBody AllocatableRemove allocatableRemove) {
        boolean isSuccess;
        if (ObjectUtils.isNotEmpty(allocatableRemove)) {
            isSuccess = this.sysEmployeeService.removeAllocatable(allocatableRemove.getOrganizationId(), allocatableRemove.getDepartmentId(), allocatableRemove.getEmployeeId());
        } else {
            isSuccess = false;
        }
        return result(isSuccess);
    }

    @GetMapping({"/{employeeName}"})
    @Operation(summary = "根据姓名查询人员", description = "根据输入的人员姓名，分页查询当前部门下已设置人事归属的人员信息", responses = {@ApiResponse(description = "查询到的人员", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysEmployee.class))}), @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"), @ApiResponse(responseCode = "500", description = "查询失败")})
    @Parameters({@Parameter(name = "employeeName", in = ParameterIn.PATH, required = true, description = "当前页码")})
    public Result<SysEmployee> findByEmployeeName(@PathVariable("employeeName") String employeeName) {
        SysEmployee sysEmployee = this.sysEmployeeService.findByEmployeeName(employeeName);
        return result((SysEmployeeController) sysEmployee);
    }
}
