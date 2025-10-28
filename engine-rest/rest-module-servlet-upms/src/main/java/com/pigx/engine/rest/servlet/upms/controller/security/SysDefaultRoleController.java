package com.pigx.engine.rest.servlet.upms.controller.security;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.security.SysDefaultRole;
import com.pigx.engine.logic.upms.service.security.SysDefaultRoleService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/security/default-role"})
@Tags({@Tag(name = "用户安全管理接口"), @Tag(name = "系统默认角色管理接口")})
@RestController
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/security/SysDefaultRoleController.class */
public class SysDefaultRoleController extends AbstractJpaWriteableController<SysDefaultRole, String> {
    private final SysDefaultRoleService sysDefaultRoleService;

    public SysDefaultRoleController(SysDefaultRoleService sysDefaultRoleService) {
        this.sysDefaultRoleService = sysDefaultRoleService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysDefaultRole, String> getService() {
        return this.sysDefaultRoleService;
    }

    @PutMapping
    @Operation(summary = "设置默认角色", description = "给不同的登录场景设置不同的默认角色", requestBody = @RequestBody(content = {@Content(mediaType = "application/x-www-form-urlencoded")}), responses = {@ApiResponse(description = "已保存数据", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "defaultId", required = true, description = "默认角色类型ID"), @Parameter(name = "roleId", required = true, description = "设置的角色ID")})
    public Result<SysDefaultRole> assign(@RequestParam(name = "defaultId") @NotBlank String defaultId, @RequestParam(name = "roleId") @NotBlank String roleId) {
        SysDefaultRole sysDefaultRole = this.sysDefaultRoleService.assign(defaultId, roleId);
        return result((SysDefaultRoleController) sysDefaultRole);
    }
}
