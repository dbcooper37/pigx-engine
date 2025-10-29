package com.pigx.engine.oss.rest.specification.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.rest.specification.arguments.CompleteMultipartUploadArguments;
import com.pigx.engine.oss.rest.specification.arguments.CreateMultipartUploadArguments;
import com.pigx.engine.oss.solution.business.CreateMultipartUploadBusiness;
import com.pigx.engine.oss.solution.constants.OssSolutionConstants;
import com.pigx.engine.oss.solution.proxy.OssPresignedUrlProxy;
import com.pigx.engine.oss.solution.service.OssMultipartUploadService;
import com.pigx.engine.oss.specification.domain.base.ObjectWriteDomain;
import com.pigx.engine.oss.specification.domain.multipart.CompleteMultipartUploadDomain;
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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(OssSolutionConstants.OSS_MULTIPART_UPLOAD_REQUEST_MAPPING)
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "OSS统一管理接口"),
        @Tag(name = "OSS统一大文件分片接口")
})
public class OssMultipartUploadController implements Controller {

    private final OssMultipartUploadService ossMultipartUploadService;
    private final OssPresignedUrlProxy ossPresignedUrlProxy;

    public OssMultipartUploadController(OssMultipartUploadService ossMultipartUploadService, OssPresignedUrlProxy ossPresignedUrlProxy) {
        this.ossMultipartUploadService = ossMultipartUploadService;
        this.ossPresignedUrlProxy = ossPresignedUrlProxy;
    }


    @Idempotent
    @Operation(summary = "创建分片上传信息", description = "创建分片上传信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "uploadId 和 预下载地址", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateMultipartUploadBusiness.class))),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "204", description = "无结果"),
                    @ApiResponse(responseCode = "500", description = "操作失败")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "CreateMultipartUploadArguments参数实体", schema = @Schema(implementation = CreateMultipartUploadArguments.class))
    })
    @PostMapping("/create")
    public Result<CreateMultipartUploadBusiness> createMultipartUpload(@Validated @RequestBody CreateMultipartUploadArguments arguments) {
        CreateMultipartUploadBusiness result = ossMultipartUploadService.createMultipartUpload(arguments.getBucketName(), arguments.getObjectName(), arguments.getPartNumber());
        return result(result);
    }

    @Idempotent
    @Operation(summary = "完成分片上传", description = "完成分片上传，Minio将上传完成的分片信息进行合并",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "操作结果", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectWriteDomain.class))),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "204", description = "无结果"),
                    @ApiResponse(responseCode = "500", description = "操作失败")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "CompleteMultipartUploadArguments参数实体", schema = @Schema(implementation = CompleteMultipartUploadArguments.class))
    })
    @PostMapping("/complete")
    public Result<CompleteMultipartUploadDomain> completeMultipartUpload(@Validated @RequestBody CompleteMultipartUploadArguments arguments) {
        CompleteMultipartUploadDomain entity = ossMultipartUploadService.completeMultipartUpload(arguments.getBucketName(), arguments.getObjectName(), arguments.getUploadId());
        return result(entity);
    }

    @Operation(summary = "预下载代理地址", description = "预下载代理地址，避免前端直接访问OSS，同时导致微服务寻址错误",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "操作结果", content = @Content(mediaType = "application/json")),
            })
    @PutMapping(value = OssSolutionConstants.OSS_PRESIGNED_OBJECT_PROXY_REQUEST_MAPPING)
    public ResponseEntity<String> presignedUrlProxy(HttpServletRequest request) {
        return ossPresignedUrlProxy.delegate(request);
    }
}
