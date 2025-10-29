package com.pigx.engine.oss.rest.specification.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.specification.arguments.bucket.CreateBucketArguments;
import com.pigx.engine.oss.specification.arguments.bucket.DeleteBucketArguments;
import com.pigx.engine.oss.specification.core.repository.OssBucketRepository;
import com.pigx.engine.oss.specification.domain.bucket.BucketDomain;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/oss/bucket")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "OSS统一管理接口"),
        @Tag(name = "OSS统一存储桶管理接口")
})
public class OssBucketController implements Controller {

    private final OssBucketRepository ossBucketHandler;

    public OssBucketController(OssBucketRepository ossBucketHandler) {
        this.ossBucketHandler = ossBucketHandler;
    }

    @AccessLimited
    @Operation(summary = "查询存储桶是否存在", description = "根据BucketName查询存储桶是否存在",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "是否Bucket存在", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "查询成功"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "OSS Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "bucketName", required = true, description = "存储桶名称")
    })
    @GetMapping("/exists")
    public Result<Boolean> doesBucketExist(String bucketName) {
        boolean isExists = ossBucketHandler.doesBucketExist(bucketName);
        return result(isExists);
    }

    @AccessLimited
    @Operation(summary = "获取全部存储桶(Bucket)", description = "获取全部存储桶(Bucket)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有Buckets", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @GetMapping("/list")
    public Result<List<BucketDomain>> listBuckets() {
        List<BucketDomain> domains = ossBucketHandler.listBuckets();
        return result(domains);
    }

    @Idempotent
    @Operation(summary = "创建存储桶", description = "创建存储桶接口，该接口仅是创建，不包含是否已存在检查",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "CreateBucketArguments请求参数实体", schema = @Schema(implementation = CreateBucketArguments.class))
    })
    @PostMapping
    public Result<Boolean> createBucket(@Validated @RequestBody CreateBucketArguments arguments) {
        // Minio 的 MakeBucket 没有返回值
        ossBucketHandler.createBucket(arguments);
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除存储桶", description = "根据存储桶名称删除数据，可指定 Region",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "DeleteBucketArguments请求参数实体", schema = @Schema(implementation = DeleteBucketArguments.class))
    })
    @DeleteMapping
    public Result<Boolean> deleteBucket(@Validated @RequestBody DeleteBucketArguments arguments) {
        ossBucketHandler.deleteBucket(arguments);
        return result(true);
    }
}
