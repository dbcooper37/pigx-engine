package com.pigx.engine.rest.servlet.upms.controller.assistant;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.logic.upms.service.assistant.ConstantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/system/constant"})
@RestController
@Tag(name = "系统常量接口")
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/assistant/ConstantsController.class */
public class ConstantsController {
    private final ConstantsService constantsService;

    @Autowired
    public ConstantsController(ConstantsService constantsService) {
        this.constantsService = constantsService;
    }

    @GetMapping({"/enums"})
    @Operation(summary = "获取服务使用常量", description = "获取服务涉及的常量以及信息")
    public Result<Map<String, Object>> findAllEnums() {
        new Result();
        Map<String, Object> allEnums = this.constantsService.getAllEnums();
        if (MapUtils.isNotEmpty(allEnums)) {
            return Result.success("获取服务常量成功", allEnums);
        }
        return Result.failure("获取服务常量失败");
    }
}
