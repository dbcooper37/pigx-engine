package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.rest.minio.bo.BucketSettingBusiness;
import com.pigx.engine.oss.rest.minio.service.MinioBucketSettingService;
import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.core.definition.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/oss/minio/bucket/setting")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象存储桶设置接口")
})
public class MinioBucketSettingController implements Controller {

    private final MinioBucketSettingService settingService;

    public MinioBucketSettingController(MinioBucketSettingService settingService) {
        this.settingService = settingService;
    }

    @AccessLimited
    @Operation(summary = "获取存储桶设置信息", description = "获取存储桶设置信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "存储桶设置信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BucketSettingBusiness.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            })
    @Parameters({
            @Parameter(name = "bucketName", required = true, description = "存储桶名称"),
    })
    @GetMapping
    public Result<BucketSettingBusiness> get(@RequestParam(value = "bucketName") String bucketName) {
        BucketSettingBusiness entity = settingService.get(bucketName);
        return result(entity);
    }
}
