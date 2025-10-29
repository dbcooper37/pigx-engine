package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.service.MinioObjectLegalHoldService;
import com.pigx.engine.oss.rest.minio.request.object.DisableObjectLegalHoldRequest;
import com.pigx.engine.oss.rest.minio.request.object.EnableObjectLegalHoldRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/oss/minio/object/legal-hold")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象LegalHold管理接口")
})
public class MinioObjectLegalHoldController implements Controller {

    private final MinioObjectLegalHoldService minioObjectLegalHoldService;

    public MinioObjectLegalHoldController(MinioObjectLegalHoldService minioObjectLegalHoldService) {
        this.minioObjectLegalHoldService = minioObjectLegalHoldService;
    }

    @Idempotent
    @Operation(summary = "设置开启对象持有配置", description = "设置开启对象持有配置",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "EnableObjectLegalHoldRequest请求参数实体", schema = @Schema(implementation = EnableObjectLegalHoldRequest.class))
    })
    @PutMapping("/enable")
    public Result<Boolean> enable(@Validated @RequestBody EnableObjectLegalHoldRequest request) {
        minioObjectLegalHoldService.enableObjectLegalHold(request.build());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "设置关闭对象持有配置", description = "设置关闭对象持有配置",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "DisableObjectLegalHoldRequest请求参数实体", schema = @Schema(implementation = DisableObjectLegalHoldRequest.class))
    })
    @PutMapping("/disable")
    public Result<Boolean> disable(@Validated @RequestBody DisableObjectLegalHoldRequest request) {
        minioObjectLegalHoldService.disableObjectLegalHold(request.build());
        return result(true);
    }
}
