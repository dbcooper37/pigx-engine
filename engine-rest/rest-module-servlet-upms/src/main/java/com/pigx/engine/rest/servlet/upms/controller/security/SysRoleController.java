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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/security/role")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统角色管理接口")
})
public class SysRoleController extends AbstractJpaWriteableController<SysRole, String> {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    public BaseJpaWriteableService<SysRole, String> getService() {
        return this.sysRoleService;
    }

    @AccessLimited
    @Operation(summary = "根据角色代码查询角色", description = "根据输入的角色代码，查询对应的角色",
            responses = {
                    @ApiResponse(description = "查询到的角色", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SysRole.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @Parameters({
            @Parameter(name = "roleCode", in = ParameterIn.PATH, required = true, description = "角色代码"),
    })
    @GetMapping("/{roleCode}")
    public Result<SysRole> findByRoleCode(@PathVariable("roleCode") String roleCode) {
        SysRole sysRole = sysRoleService.findByRoleCode(roleCode);
        return result(sysRole);
    }

    @AccessLimited
    @Operation(summary = "获取全部角色", description = "获取全部角色数据列表",
            responses = {
                    @ApiResponse(description = "全部数据列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            })
    @GetMapping("/list")
    public Result<List<SysRole>> findAll() {
        List<SysRole> sysAuthorities = sysRoleService.findAll();
        return result(sysAuthorities);
    }

    @Operation(summary = "给角色赋予权限", description = "为角色赋予权限",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)),
            responses = {@ApiResponse(description = "已分配权限的角色数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "roleId", required = true, description = "角色ID"),
            @Parameter(name = "permissions[]", required = true, description = "权限对象组成的数组")
    })
    @PutMapping
    public Result<SysRole> assign(@RequestParam(name = "roleId") String roleId, @RequestParam(name = "permissions[]") String[] permissions) {
        SysRole sysRole = sysRoleService.assign(roleId, permissions);
        return result(sysRole);
    }
}
