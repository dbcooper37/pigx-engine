package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.oauth2.persistence.sas.jpa.event.SignOutComplianceEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/oauth2"})
@Tags({@Tag(name = "OAuth2 认证服务接口"), @Tag(name = "OAuth2 扩展接口")})
@RestController
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OAuth2SignOutController.class */
public class OAuth2SignOutController {
    private final OAuth2AuthorizationService authorizationService;

    public OAuth2SignOutController(OAuth2AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PutMapping({"/sign-out"})
    @Operation(summary = "注销OAuth2应用", description = "根据接收到的AccessToken,删除后端存储的Token信息,起到注销效果", requestBody = @RequestBody(content = {@Content(mediaType = "application/x-www-form-urlencoded")}), responses = {@ApiResponse(description = "是否成功", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "accessToken", required = true, description = "Access Token"), @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, description = "Basic Token")})
    public Result<String> signOut(@RequestParam(name = "accessToken") @NotBlank String accessToken, HttpServletRequest request) {
        OAuth2Authorization authorization = this.authorizationService.findByToken(accessToken, OAuth2TokenType.ACCESS_TOKEN);
        if (ObjectUtils.isNotEmpty(authorization)) {
            this.authorizationService.remove(authorization);
            ServiceContextHolder.publishEvent(new SignOutComplianceEvent(authorization, request));
        }
        return Result.success("注销成功");
    }
}
