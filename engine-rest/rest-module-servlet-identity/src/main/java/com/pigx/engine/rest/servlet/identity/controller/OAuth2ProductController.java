package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.identity.entity.OAuth2Product;
import com.pigx.engine.logic.identity.service.OAuth2ProductService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/authorize/product"})
@Tags({@Tag(name = "OAuth2 认证服务接口"), @Tag(name = "物联网管理接口"), @Tag(name = "物联网产品接口")})
@RestController
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OAuth2ProductController.class */
public class OAuth2ProductController extends AbstractJpaWriteableController<OAuth2Product, String> {
    private final OAuth2ProductService iotProductService;

    public OAuth2ProductController(OAuth2ProductService iotProductService) {
        this.iotProductService = iotProductService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<OAuth2Product, String> getService() {
        return this.iotProductService;
    }
}
