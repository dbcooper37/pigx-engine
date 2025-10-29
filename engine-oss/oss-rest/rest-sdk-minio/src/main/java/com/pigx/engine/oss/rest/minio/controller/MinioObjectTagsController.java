package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.service.MinioObjectTagsService;
import com.pigx.engine.oss.rest.minio.request.object.DeleteObjectTagsRequest;
import com.pigx.engine.oss.rest.minio.request.object.SetObjectTagsRequest;
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
@RequestMapping("/oss/minio/object/tags")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象桶标签管理接口")
})
public class MinioObjectTagsController implements Controller {

    private final MinioObjectTagsService minioObjectTagsService;

    public MinioObjectTagsController(MinioObjectTagsService minioObjectTagsService) {
        this.minioObjectTagsService = minioObjectTagsService;
    }

    @Idempotent
    @Operation(summary = "设置对象标签", description = "设置对象标签",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "SetObjectTagsRequest请求参数实体", schema = @Schema(implementation = SetObjectTagsRequest.class))
    })
    @PutMapping
    public Result<Boolean> set(@Validated @RequestBody SetObjectTagsRequest request) {
        minioObjectTagsService.setObjectTags(request.build());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "清空对象标签", description = "利用Tags的增减就可以实现Tags的删除，所以这个删除应该理解成清空",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "DeleteObjectTagsRequest请求参数实体", schema = @Schema(implementation = DeleteObjectTagsRequest.class))
    })
    @DeleteMapping
    public Result<Boolean> delete(@Validated @RequestBody DeleteObjectTagsRequest request) {
        minioObjectTagsService.deleteObjectTags(request.build());
        return result(true);
    }
}
