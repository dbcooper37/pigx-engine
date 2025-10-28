package com.pigx.engine.rest.servlet.upms.controller.security;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import com.pigx.engine.logic.upms.service.security.SysRoleService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import com.pigx.engine.web.core.annotation.AccessLimited;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/security/role"})
@Tags({@Tag(name = "用户安全管理接口"), @Tag(name = "系统角色管理接口")})
@RestController
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/security/SysRoleController.class */
public class SysRoleController extends AbstractJpaWriteableController<SysRole, String> {
    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysRole, String> getService() {
        return this.sysRoleService;
    }

    @Operation(summary = "根据角色代码查询角色", description = "根据输入的角色代码，查询对应的角色", responses = {@ApiResponse(description = "查询到的角色", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysRole.class))}), @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"), @ApiResponse(responseCode = "500", description = "查询失败")})
    @Parameters({@Parameter(name = "roleCode", in = ParameterIn.PATH, required = true, description = "角色代码")})
    @AccessLimited
    @GetMapping({"/{roleCode}"})
    public Result<SysRole> findByRoleCode(@PathVariable("roleCode") String roleCode) {
        SysRole sysRole = this.sysRoleService.findByRoleCode(roleCode);
        return result((SysRoleController) sysRole);
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    @AccessLimited
    @GetMapping({"/list"})
    @Operation(summary = "获取全部角色", description = "获取全部角色数据列表", responses = {@ApiResponse(description = "全部数据列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))}), @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"), @ApiResponse(responseCode = "500", description = "查询失败")})
    public Result<List<SysRole>> findAll() {
        return result((List) this.sysRoleService.findAll());
    }

    @PutMapping
    @Operation(summary = "给角色赋予权限", description = "为角色赋予权限", requestBody = @RequestBody(content = {@Content(mediaType = "application/x-www-form-urlencoded")}), responses = {@ApiResponse(description = "已分配权限的角色数据", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "roleId", required = true, description = "角色ID"), @Parameter(name = "permissions[]", required = true, description = "权限对象组成的数组")})
    public Result<SysRole> assign(@RequestParam(name = "roleId") String roleId, @RequestParam(name = "permissions[]") String[] permissions) {
        SysRole sysRole = this.sysRoleService.assign(roleId, permissions);
        return result((SysRoleController) sysRole);
    }
}
