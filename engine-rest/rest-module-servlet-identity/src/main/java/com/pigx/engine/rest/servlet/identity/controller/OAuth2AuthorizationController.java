package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorization;
import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusAuthorizationService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/authorize/authorization")
@Tags({
        @Tag(name = "OAuth2 认证服务接口"),
        @Tag(name = "OAuth2 认证管理接口")
})
public class OAuth2AuthorizationController extends AbstractJpaWriteableController<HerodotusAuthorization, String> {

    private final HerodotusAuthorizationService herodotusAuthorizationService;

    @Autowired
    public OAuth2AuthorizationController(HerodotusAuthorizationService herodotusAuthorizationService) {
        this.herodotusAuthorizationService = herodotusAuthorizationService;
    }

    @Override
    public BaseJpaWriteableService<HerodotusAuthorization, String> getService() {
        return this.herodotusAuthorizationService;
    }
}
