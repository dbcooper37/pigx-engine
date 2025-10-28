package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.identity.entity.OAuth2Application;
import com.pigx.engine.logic.identity.service.OAuth2ApplicationService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/authorize/application"})
@Tags({@Tag(name = "OAuth2 认证服务接口"), @Tag(name = "OAuth2 应用管理接口")})
@RestController
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OAuth2ApplicationController.class */
public class OAuth2ApplicationController extends AbstractJpaWriteableController<OAuth2Application, String> {
    private final OAuth2ApplicationService applicationService;

    public OAuth2ApplicationController(OAuth2ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<OAuth2Application, String> getService() {
        return this.applicationService;
    }

    @PutMapping
    @Operation(summary = "给应用分配Scope", description = "给应用分配Scope")
    @Parameters({@Parameter(name = "appKey", required = true, description = "appKey"), @Parameter(name = "scopes[]", required = true, description = "Scope对象组成的数组")})
    public Result<OAuth2Application> authorize(@RequestParam(name = "applicationId") String scopeId, @RequestParam(name = "scopes[]") String[] scopes) {
        OAuth2Application application = this.applicationService.authorize(scopeId, scopes);
        return result((OAuth2ApplicationController) application);
    }
}
