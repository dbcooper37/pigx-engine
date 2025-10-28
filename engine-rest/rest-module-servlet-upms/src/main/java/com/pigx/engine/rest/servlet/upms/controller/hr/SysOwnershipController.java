package com.pigx.engine.rest.servlet.upms.controller.hr;

import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.hr.SysOwnership;
import com.pigx.engine.logic.upms.service.hr.SysOwnershipService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/hr/ownership"})
@RestController
@Tag(name = "人事归属管理接口")
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/hr/SysOwnershipController.class */
public class SysOwnershipController extends AbstractJpaWriteableController<SysOwnership, String> {
    private final SysOwnershipService sysOwnershipService;

    public SysOwnershipController(SysOwnershipService sysOwnershipService) {
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysOwnership, String> getService() {
        return this.sysOwnershipService;
    }
}
