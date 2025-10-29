package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.converter.GroupInfoToDomainConverter;
import com.pigx.engine.oss.dialect.minio.domain.GroupDomain;
import com.pigx.engine.oss.dialect.minio.domain.UserDomain;
import com.pigx.engine.oss.dialect.minio.service.MinioAdminGroupService;
import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.core.annotation.Idempotent;
import com.pigx.engine.web.core.definition.Controller;
import io.minio.admin.GroupInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/oss/minio/admin/group")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 组管理接口")
})
public class MinioAdminGroupController implements Controller {

    private final MinioAdminGroupService minioAdminGroupService;
    private final Converter<GroupInfo, GroupDomain> toDomain;

    public MinioAdminGroupController(MinioAdminGroupService minioAdminGroupService) {
        this.minioAdminGroupService = minioAdminGroupService;
        this.toDomain = new GroupInfoToDomainConverter();
    }

    @AccessLimited
    @Operation(summary = "获取全部组信息", description = "获取全部组信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有Buckets", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @GetMapping("/list")
    public Result<List<String>> list() {
        List<String> groups = minioAdminGroupService.listGroups();
        if (CollectionUtils.isNotEmpty(groups)) {
            return Result.success("查询成功", groups);
        } else {
            return Result.empty();
        }
    }

    @AccessLimited
    @Operation(summary = "获取组信息", description = "获取 Minio 组信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "组信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDomain.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "accessKey", required = true, description = "用户对应 AccessKey 标识")
    })
    @GetMapping
    public Result<GroupDomain> get(String group) {
        GroupInfo groupInfo = minioAdminGroupService.getGroupInfo(group);
        GroupDomain groupDomain = toDomain.convert(groupInfo);
        return result(groupDomain);
    }

    @Idempotent
    @Operation(summary = "创建或更新组", description = "创建或更新组",
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
    public Result<Boolean> add(@Validated @RequestBody GroupDomain domain) {
        minioAdminGroupService.addUpdateGroup(domain.getName(), domain.getStatus(), domain.getMembers());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除组", description = "删除组信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "group", required = true, description = "组对应标识")
    })
    @DeleteMapping
    public Result<Boolean> remove(String group) {
        minioAdminGroupService.removeGroup(group);
        return result(true);
    }
}
