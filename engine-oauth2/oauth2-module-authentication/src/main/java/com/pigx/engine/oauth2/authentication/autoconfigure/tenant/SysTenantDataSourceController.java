package com.pigx.engine.oauth2.authentication.autoconfigure.tenant;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.data.tenant.entity.SysTenantDataSource;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import com.pigx.engine.web.core.annotation.AccessLimited;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/security/tenant/datasource"})
@Tags({@Tag(name = "系统安全管理接口"), @Tag(name = "多租户数据源接口")})
@RestController
/* loaded from: oauth2-authentication-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/autoconfigure/tenant/SysTenantDataSourceController.class */
public class SysTenantDataSourceController extends AbstractJpaWriteableController<SysTenantDataSource, String> {
    private final SysTenantDataSourceService sysTenantDataSourceService;

    public SysTenantDataSourceController(SysTenantDataSourceService sysTenantDataSourceService) {
        this.sysTenantDataSourceService = sysTenantDataSourceService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysTenantDataSource, String> getService() {
        return this.sysTenantDataSourceService;
    }

    @Operation(summary = "根据租户代码查询数据源", description = "根据输入的租户代码，查询对应的数据源", responses = {@ApiResponse(description = "查询到的数据源", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SysTenantDataSource.class))}), @ApiResponse(responseCode = "500", description = "查询失败")})
    @Parameters({@Parameter(name = "tenantId", in = ParameterIn.PATH, required = true, description = "租户代码")})
    @AccessLimited
    @GetMapping({"/{tenantId}"})
    public Result<SysTenantDataSource> findByRoleCode(@PathVariable("tenantId") String tenantId) {
        SysTenantDataSource sysTenantDataSource = this.sysTenantDataSourceService.findByTenantId(tenantId);
        return result((SysTenantDataSourceController) sysTenantDataSource);
    }
}
