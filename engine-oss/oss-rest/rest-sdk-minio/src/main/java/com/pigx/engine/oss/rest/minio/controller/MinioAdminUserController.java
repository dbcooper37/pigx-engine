package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.converter.UserInfoToDomainConverter;
import com.pigx.engine.oss.dialect.minio.converter.UsersToDomainsConverter;
import com.pigx.engine.oss.dialect.minio.domain.UserDomain;
import com.pigx.engine.oss.dialect.minio.service.MinioAdminUserService;
import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.core.annotation.Idempotent;
import com.pigx.engine.web.core.definition.Controller;
import io.minio.admin.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/oss/minio/admin/user")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 用户管理接口")
})
public class MinioAdminUserController implements Controller {

    private final MinioAdminUserService minioAdminUserService;
    private final Converter<Map<String, UserInfo>, List<UserDomain>> toDomains;
    private final Converter<UserInfo, UserDomain> toDomain;

    public MinioAdminUserController(MinioAdminUserService minioAdminUserService) {
        this.minioAdminUserService = minioAdminUserService;
        this.toDomains = new UsersToDomainsConverter();
        this.toDomain = new UserInfoToDomainConverter();
    }

    @AccessLimited
    @Operation(summary = "获取全部用户信息", description = "获取全部用户信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有Buckets", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @GetMapping("/list")
    public Result<List<UserDomain>> list() {
        Map<String, UserInfo> users = minioAdminUserService.listUsers();
        List<UserDomain> domains = toDomains.convert(users);
        return result(domains);
    }

    @AccessLimited
    @Operation(summary = "获取用户信息", description = "根据用户 AccessKey 获取 Minio 用户信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "用户信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDomain.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "accessKey", required = true, description = "用户对应 AccessKey 标识")
    })
    @GetMapping
    public Result<UserDomain> get(String accessKey) {
        UserInfo userInfo = minioAdminUserService.getUserInfo(accessKey);
        UserDomain userDomain = toDomain.convert(userInfo);
        return result(userDomain);
    }

    @Idempotent
    @Operation(summary = "创建用户", description = "创建 Minio 用户",
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
    public Result<Boolean> add(@Validated @RequestBody UserDomain domain) {
        minioAdminUserService.addUser(domain.getAccessKey(), UserInfo.Status.fromString(domain.getStatus().name()), domain.getSecretKey(), domain.getPolicyName(), domain.getMemberOf());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除用户", description = "根据用户 AccessKey 删除用户信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "accessKey", required = true, description = "用户对应 AccessKey 标识")
    })
    @DeleteMapping
    public Result<Boolean> remove(String accessKey) {
        minioAdminUserService.deleteUser(accessKey);
        return result(true);
    }
}
