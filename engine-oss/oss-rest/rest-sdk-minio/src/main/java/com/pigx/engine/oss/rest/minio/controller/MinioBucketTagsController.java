package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.service.MinioBucketTagsService;
import com.pigx.engine.oss.rest.minio.request.bucket.DeleteBucketTagsRequest;
import com.pigx.engine.oss.rest.minio.request.bucket.SetBucketTagsRequest;
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
@RequestMapping("/oss/minio/bucket/tags")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 存储桶标签管理接口")
})
public class MinioBucketTagsController implements Controller {

    private final MinioBucketTagsService minioBucketTagsService;

    public MinioBucketTagsController(MinioBucketTagsService minioBucketTagsService) {
        this.minioBucketTagsService = minioBucketTagsService;
    }

    @Idempotent
    @Operation(summary = "设置存储桶标签", description = "设置存储桶标签",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "SetBucketTagsRequest请求参数实体", schema = @Schema(implementation = SetBucketTagsRequest.class))
    })
    @PutMapping
    public Result<Boolean> set(@Validated @RequestBody SetBucketTagsRequest request) {
        minioBucketTagsService.setBucketTags(request.build());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "清空存储桶标签", description = "利用Tags的增减就可以实现Tags的删除，所以这个删除应该理解成清空",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "DeleteBucketPolicyRequest请求参数实体", schema = @Schema(implementation = DeleteBucketTagsRequest.class))
    })
    @DeleteMapping
    public Result<Boolean> delete(@Validated @RequestBody DeleteBucketTagsRequest request) {
        minioBucketTagsService.deleteBucketTags(request.build());
        return result(true);
    }
}
