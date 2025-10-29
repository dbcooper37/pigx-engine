package com.pigx.engine.rest.servlet.upms.controller.security;

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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/security/user")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统用户管理接口")
})
public class SysUserController extends AbstractJpaWriteableController<SysUser, String> {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public BaseJpaWriteableService<SysUser, String> getService() {
        return this.sysUserService;
    }

    /**
     * 给用户分配角色
     *
     * @param userId 用户Id
     * @param roles  角色Id数组
     * @return {@link Result}
     */
    @Operation(summary = "给用户分配角色", description = "给用户分配角色",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)),
            responses = {@ApiResponse(description = "已分配角色的用户数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "userId", required = true, description = "userId"),
            @Parameter(name = "roles[]", required = true, description = "角色对象组成的数组")
    })
    @PutMapping
    public Result<SysUser> assign(@RequestParam(name = "userId") String userId, @RequestParam(name = "roles[]") String[] roles) {
        SysUser sysUser = sysUserService.assign(userId, roles);
        return result(sysUser);
    }

    @Operation(summary = "修改密码", description = "修改用户使用密码，默认使用加密请求传输",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)),
            responses = {@ApiResponse(description = "修改密码后的用户信息", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "userId", required = true, description = "userId"),
            @Parameter(name = "password", required = true, description = "角色对象组成的数组")
    })
    @Crypto(responseEncrypt = false)
    @PutMapping("/change-password")
    public Result<SysUser> changePassword(@RequestParam(name = "userId") String userId, @RequestParam(name = "password") String password) {
        SysUser sysUser = sysUserService.changePassword(userId, password);
        return result(sysUser);
    }

    @Operation(summary = "根据用户名查询系统用户", description = "通过username查询系统用户数据",
            responses = {
                    @ApiResponse(description = "查询到的用户", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SysUser.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @Parameters({
            @Parameter(name = "username", required = true, in = ParameterIn.PATH, description = "用户名"),
    })
    @GetMapping("/sign-in/{username}")
    public Result<SysUser> findByUsername(@PathVariable("username") String username) {
        SysUser sysUser = sysUserService.findByUsername(username);
        return result(sysUser);
    }
}
