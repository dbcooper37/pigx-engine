package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.logic.identity.service.OAuth2ConstantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/authorize/constant"})
@Tags({@Tag(name = "OAuth2 认证服务接口"), @Tag(name = "常量接口")})
@RestController
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OAuth2ConstantController.class */
public class OAuth2ConstantController {
    private final OAuth2ConstantService constantService;

    @Autowired
    public OAuth2ConstantController(OAuth2ConstantService constantService) {
        this.constantService = constantService;
    }

    @GetMapping({"/enums"})
    @Operation(summary = "获取服务常量", description = "获取服务涉及的常量以及信息")
    public Result<Map<String, Object>> findAllEnums() {
        Map<String, Object> allEnums = this.constantService.getAllEnums();
        if (MapUtils.isNotEmpty(allEnums)) {
            return Result.success("获取服务常量成功", allEnums);
        }
        return Result.failure("获取服务常量失败");
    }
}
