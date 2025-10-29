package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.dialect.minio.service.MinioAdminService;
import com.pigx.engine.web.core.annotation.AccessLimited;
import io.minio.admin.messages.DataUsageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/oss/minio/admin")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 统计信息接口")
})
public class MinioAdminController {

    private final MinioAdminService minioAdminService;

    public MinioAdminController(MinioAdminService minioAdminService) {
        this.minioAdminService = minioAdminService;
    }

    @AccessLimited
    @Operation(summary = "获取 Minio 统计信息", description = "获取 Minio 统计信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "统计信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataUsageInfo.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @GetMapping("/usage")
    public Result<DataUsageInfo> dataUsageInfo() {
        DataUsageInfo info = minioAdminService.getDataUsageInfo();
        if (ObjectUtils.isNotEmpty(info)) {
            return Result.success("查询成功", info);
        } else {
            return Result.empty();
        }
    }
}
