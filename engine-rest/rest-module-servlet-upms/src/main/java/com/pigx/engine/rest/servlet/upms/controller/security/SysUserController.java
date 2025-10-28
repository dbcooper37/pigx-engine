package com.pigx.engine.rest.servlet.upms.controller.security;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import com.pigx.engine.web.core.annotation.Crypto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/security/user"})
@Tags({@Tag(name = "用户安全管理接口"), @Tag(name = "系统用户管理接口")})
@RestController
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/security/SysUserController.class */
public class SysUserController extends AbstractJpaWriteableController<SysUser, String> {
    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysUser, String> getService() {
        return this.sysUserService;
    }

    @PutMapping
    @Operation(summary = "给用户分配角色", description = "给用户分配角色", requestBody = @RequestBody(content = {@Content(mediaType = "application/x-www-form-urlencoded")}), responses = {@ApiResponse(description = "已分配角色的用户数据", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "userId", required = true, description = "userId"), @Parameter(name = "roles[]", required = true, description = "角色对象组成的数组")})
    public Result<SysUser> assign(@RequestParam(name = "userId") String userId, @RequestParam(name = "roles[]") String[] roles) {
        SysUser sysUser = this.sysUserService.assign(userId, roles);
        return result((SysUserController) sysUser);
    }

    @PutMapping({"/change-password"})
    @Operation(summary = "修改密码", description = "修改用户使用密码，默认使用加密请求传输", requestBody = @RequestBody(content = {@Content(mediaType = "application/x-www-form-urlencoded")}), responses = {@ApiResponse(description = "修改密码后的用户信息", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "userId", required = true, description = "userId"), @Parameter(name = SystemConstants.PASSWORD, required = true, description = "角色对象组成的数组")})
    @Crypto(responseEncrypt = false)
    public Result<SysUser> changePassword(@RequestParam(name = "userId") String userId, @RequestParam(name = SystemConstants.PASSWORD) String password) {
        SysUser sysUser = this.sysUserService.changePassword(userId, password);
        return result((SysUserController) sysUser);
    }

    @GetMapping({"/sign-in/{username}"})
    @Operation(summary = "根据用户名查询系统用户", description = "通过username查询系统用户数据", responses = {@ApiResponse(description = "查询到的用户", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysUser.class))}), @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"), @ApiResponse(responseCode = "500", description = "查询失败")})
    @Parameters({@Parameter(name = SystemConstants.USERNAME, required = true, in = ParameterIn.PATH, description = "用户名")})
    public Result<SysUser> findByUsername(@PathVariable(SystemConstants.USERNAME) String username) {
        SysUser sysUser = this.sysUserService.findByUsername(username);
        return result((SysUserController) sysUser);
    }
}
