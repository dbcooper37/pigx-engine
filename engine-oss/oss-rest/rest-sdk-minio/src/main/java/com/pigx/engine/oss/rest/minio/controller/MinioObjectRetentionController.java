package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.service.MinioObjectRetentionService;
import com.pigx.engine.oss.rest.minio.request.object.SetObjectRetentionRequest;
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
@RequestMapping("/oss/minio/object/retention")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Object Retention管理接口")
})
public class MinioObjectRetentionController implements Controller {

    private final MinioObjectRetentionService minioObjectRetentionService;

    public MinioObjectRetentionController(MinioObjectRetentionService minioObjectRetentionService) {
        this.minioObjectRetentionService = minioObjectRetentionService;
    }

    @Idempotent
    @Operation(summary = "设置对象的保留配置", description = "设置对象的保留配置",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "request", required = true, description = "设置对象保留配置请求参数实体", schema = @Schema(implementation = SetObjectRetentionRequest.class))
    })
    @PutMapping
    public Result<Boolean> set(@Validated @RequestBody SetObjectRetentionRequest request) {
        minioObjectRetentionService.setObjectRetention(request.build());
        return result(true);
    }
}
