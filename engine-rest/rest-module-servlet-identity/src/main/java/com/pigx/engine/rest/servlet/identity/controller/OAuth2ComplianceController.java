package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;
import com.pigx.engine.oauth2.extension.service.OAuth2UserLoggingService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/authorize/compliance")
@Tags({
        @Tag(name = "OAuth2 认证服务接口"),
        @Tag(name = "OAuth2 应用安全合规接口"),
        @Tag(name = "OAuth2 审计管理接口")
})
public class OAuth2ComplianceController extends AbstractJpaWriteableController<OAuth2UserLogging, String> {

    private final OAuth2UserLoggingService complianceService;

    @Autowired
    public OAuth2ComplianceController(OAuth2UserLoggingService complianceService) {
        this.complianceService = complianceService;
    }

    @Override
    public BaseJpaWriteableService<OAuth2UserLogging, String> getService() {
        return complianceService;
    }

    @Operation(summary = "模糊条件查询合规信息", description = "根据动态输入的字段模糊查询合规信息",
            responses = {@ApiResponse(description = "人员分页列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "principalName", description = "用户账号"),
            @Parameter(name = "clientId", description = "客户端ID"),
            @Parameter(name = "ip", description = "IP地址"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "principalName", required = false) String principalName,
            @RequestParam(value = "clientId", required = false) String clientId,
            @RequestParam(value = "ip", required = false) String ip) {
        Page<OAuth2UserLogging> pages = complianceService.findByCondition(pageNumber, pageSize, principalName, clientId, ip);
        return resultFromPage(pages);
    }
}
