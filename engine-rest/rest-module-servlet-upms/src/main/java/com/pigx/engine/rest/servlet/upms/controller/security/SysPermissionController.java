package com.pigx.engine.rest.servlet.upms.controller.security;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import com.pigx.engine.logic.upms.service.security.SysPermissionService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import com.pigx.engine.web.core.annotation.AccessLimited;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/security/permission")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统权限管理接口")
})
public class SysPermissionController extends AbstractJpaWriteableController<SysPermission, String> {

    private final SysPermissionService sysPermissionService;

    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @Override
    public BaseJpaWriteableService<SysPermission, String> getService() {
        return this.sysPermissionService;
    }

    @AccessLimited
    @Operation(summary = "获取全部权限", description = "获取全部权限数据列表",
            responses = {
                    @ApiResponse(description = "全部数据列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            })
    @GetMapping("/list")
    public Result<List<SysPermission>> findAll() {
        List<SysPermission> sysAuthorities = sysPermissionService.findAll();
        return result(sysAuthorities);
    }
}
