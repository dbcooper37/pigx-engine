package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.service.MinioObjectLockConfigurationService;
import com.pigx.engine.oss.rest.minio.request.bucket.DeleteObjectLockConfigurationRequest;
import com.pigx.engine.oss.rest.minio.request.bucket.SetObjectLockConfigurationRequest;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/oss/minio/bucket/object-lock")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 存储桶对象锁定管理接口")
})
public class MinioObjectLockConfigurationController implements Controller {

    private final MinioObjectLockConfigurationService minioObjectLockConfigurationService;

    public MinioObjectLockConfigurationController(MinioObjectLockConfigurationService minioObjectLockConfigurationService) {
        this.minioObjectLockConfigurationService = minioObjectLockConfigurationService;
    }

    @Idempotent
    @Operation(summary = "设置存储桶对象锁定配置", description = "设置存储桶对象锁定配置",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "SetObjectLockConfigurationRequest请求参数实体", schema = @Schema(implementation = SetObjectLockConfigurationRequest.class))
    })
    @PutMapping
    public Result<Boolean> set(@Validated @RequestBody SetObjectLockConfigurationRequest request) {
        minioObjectLockConfigurationService.setObjectLockConfiguration(request.build());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除存储桶对象锁定配置", description = "删除存储桶对象锁定配置",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "DeleteObjectLockConfigurationRequest请求参数实体", schema = @Schema(implementation = DeleteObjectLockConfigurationRequest.class))
    })
    @DeleteMapping
    public Result<Boolean> delete(@Validated @RequestBody DeleteObjectLockConfigurationRequest request) {
        minioObjectLockConfigurationService.deleteObjectLockConfiguration(request.build());
        return result(true);
    }
}
