package com.pigx.engine.rest.servlet.upms.controller.hr;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.converter.SysDepartmentToTreeNodeConverter;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import com.pigx.engine.logic.upms.service.hr.SysDepartmentService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import cn.hutool.v7.core.tree.MapTree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/hr/department"})
@RestController
@Tag(name = "部门管理接口")
@Validated
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/hr/SysDepartmentController.class */
public class SysDepartmentController extends AbstractJpaWriteableController<SysDepartment, String> {
    private final SysDepartmentService sysDepartmentService;

    public SysDepartmentController(SysDepartmentService sysDepartmentService) {
        this.sysDepartmentService = sysDepartmentService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysDepartment, String> getService() {
        return this.sysDepartmentService;
    }

    private List<SysDepartment> getSysDepartments(String organizationId) {
        return this.sysDepartmentService.findAll(organizationId);
    }

    @GetMapping({"/condition"})
    @Operation(summary = "条件查询部门分页数据", description = "根据输入的字段条件查询部门信息", responses = {@ApiResponse(description = "单位列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class))})})
    @Parameters({@Parameter(name = "pageNumber", required = true, description = "当前页码"), @Parameter(name = "pageSize", required = true, description = "每页显示数量"), @Parameter(name = "organizationId", description = "单位ID")})
    public Result<Map<String, Object>> findByCondition(@RequestParam("pageNumber") @NotNull Integer pageNumber, @RequestParam("pageSize") @NotNull Integer pageSize, @RequestParam(value = "organizationId", required = false) String organizationId) {
        Page<SysDepartment> pages = this.sysDepartmentService.findByCondition(pageNumber.intValue(), pageSize.intValue(), organizationId);
        return resultFromPage(pages);
    }

    @GetMapping({"/list"})
    @Operation(summary = "获取部门列表", description = "根据单位ID获取部门信息列表", responses = {@ApiResponse(description = "单位列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class))})})
    @Parameters({@Parameter(name = "organizationId", required = true, description = "单位ID")})
    public Result<List<SysDepartment>> findAllByOrganizationId(@RequestParam(value = "organizationId", required = false) String organizationId) {
        List<SysDepartment> sysDepartments = getSysDepartments(organizationId);
        return result(sysDepartments);
    }

    @GetMapping({"/tree"})
    @Operation(summary = "获取部门树", description = "根据单位ID获取部门数据，转换为树形结构", responses = {@ApiResponse(description = "单位列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class))})})
    @Parameters({@Parameter(name = "organizationId", required = true, description = "单位ID")})
    public Result<List<MapTree<String>>> findTree(@RequestParam(value = "organizationId", required = false) String organizationId) {
        List<SysDepartment> sysDepartments = getSysDepartments(organizationId);
        return result(sysDepartments, new SysDepartmentToTreeNodeConverter());
    }
}
