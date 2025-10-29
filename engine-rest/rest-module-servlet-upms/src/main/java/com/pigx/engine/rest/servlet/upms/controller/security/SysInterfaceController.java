package com.pigx.engine.rest.servlet.upms.controller.security;

import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.security.SysInterface;
import com.pigx.engine.logic.upms.service.security.SysInterfaceService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/security/interface")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统接口管理接口")
})
public class SysInterfaceController extends AbstractJpaWriteableController<SysInterface, String> {

    private final SysInterfaceService sysInterfaceService;

    public SysInterfaceController(SysInterfaceService sysInterfaceService) {
        this.sysInterfaceService = sysInterfaceService;
    }

    @Override
    public BaseJpaWriteableService<SysInterface, String> getService() {
        return sysInterfaceService;
    }
}
