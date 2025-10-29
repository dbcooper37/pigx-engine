package com.pigx.engine.oss.rest.minio.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.oss.rest.minio.service.MinioConstantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oss/minio/constant")
@Tag(name = "系统常量接口")
public class MinioConstantsController {

    private final MinioConstantsService minioConstantsService;

    public MinioConstantsController(MinioConstantsService minioConstantsService) {
        this.minioConstantsService = minioConstantsService;
    }

    @Operation(summary = "获取服务使用常量", description = "获取服务涉及的常量以及信息")
    @GetMapping(value = "/enums")
    public Result<Map<String, Object>> findAllEnums() {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> allEnums = minioConstantsService.getAllEnums();
        if (MapUtils.isNotEmpty(allEnums)) {
            return Result.success("获取服务常量成功", allEnums);
        } else {
            return Result.failure("获取服务常量失败");
        }
    }
}
