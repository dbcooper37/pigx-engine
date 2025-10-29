package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.domain.UserDomain;
import com.pigx.engine.oss.dialect.minio.service.MinioAdminPolicyService;
import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.core.annotation.Idempotent;
import com.pigx.engine.web.core.definition.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/oss/minio/admin/policy")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 屏蔽策略管理接口")
})
public class MinioAdminPolicyController implements Controller {

    private final MinioAdminPolicyService minioAdminPolicyService;

    public MinioAdminPolicyController(MinioAdminPolicyService minioAdminPolicyService) {
        this.minioAdminPolicyService = minioAdminPolicyService;
    }

    @AccessLimited
    @Operation(summary = "获取全部屏蔽策略信息", description = "获取全部屏蔽策略信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有屏蔽策略信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @GetMapping("/list")
    public Result<Map<String, String>> list() {
        Map<String, String> policies = minioAdminPolicyService.listCannedPolicies();
        if (MapUtils.isNotEmpty(policies)) {
            return Result.success("查询成功", policies);
        } else {
            return Result.empty();
        }
    }

    @Idempotent
    @Operation(summary = "添加屏蔽策略信息", description = "添加屏蔽策略信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "domain", required = true, description = "UserDomain实体", schema = @Schema(implementation = UserDomain.class))
    })
    @PostMapping
    public Result<Boolean> add(@RequestParam(value = "name") String name, @RequestParam(value = "policy") String policy) {
        minioAdminPolicyService.addCannedPolicy(name, policy);
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除屏蔽策略信息", description = "删除屏蔽策略信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "name", required = true, description = "屏蔽策略名称")
    })
    @DeleteMapping
    public Result<Boolean> remove(String name) {
        minioAdminPolicyService.removeCannedPolicy(name);
        return result(true);
    }
}
