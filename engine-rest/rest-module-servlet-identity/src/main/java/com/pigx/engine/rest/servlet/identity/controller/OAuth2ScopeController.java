package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.logic.identity.service.OAuth2ScopeService;
import com.pigx.engine.rest.servlet.identity.dto.OAuth2PermissionDto;
import com.pigx.engine.rest.servlet.identity.dto.OAuth2ScopeDto;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import com.pigx.engine.web.core.annotation.AccessLimited;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/authorize/scope"})
@Tags({@Tag(name = "OAuth2 认证服务接口"), @Tag(name = "OAuth2 权限范围管理接口")})
@RestController
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OAuth2ScopeController.class */
public class OAuth2ScopeController extends AbstractJpaWriteableController<OAuth2Scope, String> {
    private final OAuth2ScopeService scopeService;

    @Autowired
    public OAuth2ScopeController(OAuth2ScopeService scopeService) {
        this.scopeService = scopeService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<OAuth2Scope, String> getService() {
        return this.scopeService;
    }

    @PostMapping({"/assigned"})
    @Operation(summary = "给Scope分配权限", description = "给Scope分配权限", responses = {@ApiResponse(description = "查询到的角色", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OAuth2ScopeDto.class))})})
    @Parameters({@Parameter(name = "scope", required = true, description = "范围请求参数")})
    public Result<OAuth2Scope> assigned(@RequestBody OAuth2ScopeDto scope) {
        Set<OAuth2Permission> permissions = new HashSet<>();
        if (CollectionUtils.isNotEmpty(scope.getPermissions())) {
            permissions = (Set) scope.getPermissions().stream().map(this::toEntity).collect(Collectors.toSet());
        }
        OAuth2Scope result = this.scopeService.assigned(scope.getScopeId(), permissions);
        return result((OAuth2ScopeController) result);
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    @AccessLimited
    @GetMapping({"/list"})
    @Operation(summary = "获取全部范围", description = "获取全部范围", responses = {@ApiResponse(description = "全部数据列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))}), @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"), @ApiResponse(responseCode = "500", description = "查询失败")})
    public Result<List<OAuth2Scope>> findAll() {
        return result((List) this.scopeService.findAll());
    }

    @AccessLimited
    @GetMapping({"/{scopeCode}"})
    @Operation(summary = "根据范围代码查询应用范围", description = "根据范围代码查询应用范围", responses = {@ApiResponse(description = "查询到的应用范围", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OAuth2Scope.class))}), @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"), @ApiResponse(responseCode = "500", description = "查询失败")})
    public Result<OAuth2Scope> findByScopeCode(@PathVariable("scopeCode") String scopeCode) {
        OAuth2Scope scope = this.scopeService.findByScopeCode(scopeCode);
        return result((OAuth2ScopeController) scope);
    }

    private OAuth2Permission toEntity(OAuth2PermissionDto dto) {
        OAuth2Permission entity = new OAuth2Permission();
        entity.setPermissionId(dto.getPermissionId());
        entity.setPermissionCode(dto.getPermissionCode());
        entity.setPermissionName(dto.getPermissionName());
        return entity;
    }
}
