package com.pigx.engine.rest.servlet.upms.controller.hr;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.converter.SysOrganizationToTreeNodeConverter;
import com.pigx.engine.logic.upms.entity.hr.SysOrganization;
import com.pigx.engine.logic.upms.enums.OrganizationCategory;
import com.pigx.engine.logic.upms.service.hr.SysOrganizationService;
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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/hr/organization"})
@RestController
@Tag(name = "单位管理接口")
@Validated
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/hr/SysOrganizationController.class */
public class SysOrganizationController extends AbstractJpaWriteableController<SysOrganization, String> {
    private final SysOrganizationService sysOrganizationService;

    public SysOrganizationController(SysOrganizationService sysOrganizationService) {
        this.sysOrganizationService = sysOrganizationService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysOrganization, String> getService() {
        return this.sysOrganizationService;
    }

    private OrganizationCategory parseOrganizationCategory(Integer category) {
        if (ObjectUtils.isEmpty(category)) {
            return null;
        }
        return OrganizationCategory.get(category);
    }

    private List<SysOrganization> getSysOrganizations(Integer category) {
        return this.sysOrganizationService.findAll(parseOrganizationCategory(category));
    }

    @GetMapping({"/condition"})
    @Operation(summary = "条件分页查询单位", description = "根据动态输入的字段查询单位分页信息", responses = {@ApiResponse(description = "单位列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysOrganization.class))})})
    @Parameters({@Parameter(name = "pageNumber", required = true, description = "当前页码"), @Parameter(name = "pageSize", required = true, description = "每页显示数量"), @Parameter(name = "category", description = "机构分类 （索引数字值）")})
    public Result<Map<String, Object>> findByCondition(@RequestParam("pageNumber") @NotNull Integer pageNumber, @RequestParam("pageSize") @NotNull Integer pageSize, @RequestParam(value = "category", required = false) Integer category) {
        Page<SysOrganization> pages = this.sysOrganizationService.findByCondition(pageNumber.intValue(), pageSize.intValue(), parseOrganizationCategory(category));
        return resultFromPage(pages);
    }

    @GetMapping({"/list"})
    @Operation(summary = "获取全部单位", description = "获取全部单位数据", responses = {@ApiResponse(description = "单位列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysOrganization.class))})})
    @Parameters({@Parameter(name = "category", description = "机构分类 （索引数字值）")})
    public Result<List<SysOrganization>> findAll(@RequestParam(value = "category", required = false) Integer category) {
        List<SysOrganization> sysOrganizations = getSysOrganizations(category);
        return result(sysOrganizations);
    }

    @GetMapping({"/tree"})
    @Operation(summary = "获取单位树", description = "获取全部单位数据，转换为树形结构", responses = {@ApiResponse(description = "单位树", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysOrganization.class))})})
    @Parameters({@Parameter(name = "category", description = "机构分类 （索引数字值）")})
    public Result<List<MapTree<String>>> findTree(@RequestParam(value = "category", required = false) Integer category) {
        List<SysOrganization> sysOrganizations = getSysOrganizations(category);
        return result(sysOrganizations, new SysOrganizationToTreeNodeConverter());
    }

    @Override // com.pigx.engine.web.api.servlet.AbstractWriteableController, com.pigx.engine.web.api.servlet.BindingController
    @DeleteMapping({"/{id}"})
    public Result<String> delete(@PathVariable String id) {
        boolean isInUse = this.sysOrganizationService.isInUse(id);
        if (isInUse) {
            return Result.failure("该单位被部分部门引用，请删除关联关系后再删除！");
        }
        this.sysOrganizationService.deleteById(id);
        return Result.success("删除成功！");
    }
}
