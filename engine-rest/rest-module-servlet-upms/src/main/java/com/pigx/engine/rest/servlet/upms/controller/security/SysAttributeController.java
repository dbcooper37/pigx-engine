package com.pigx.engine.rest.servlet.upms.controller.security;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.service.security.SysAttributeService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/security/attribute")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统属性管理接口")
})
public class SysAttributeController extends AbstractJpaWriteableController<SysAttribute, String> {

    private final SysAttributeService sysAttributeService;

    public SysAttributeController(SysAttributeService sysAttributeService) {
        this.sysAttributeService = sysAttributeService;
    }

    @Override
    public BaseJpaWriteableService<SysAttribute, String> getService() {
        return this.sysAttributeService;
    }

    @Operation(summary = "给属性分配权限", description = "给属性分配权限，方便权限数据操作",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "attributeId", required = true, description = "attributeId"),
            @Parameter(name = "permissions[]", required = true, description = "角色对象组成的数组")
    })
    @PutMapping
    public Result<SysAttribute> assign(@RequestParam(name = "attributeId") String attributeId, @RequestParam(name = "permissions[]") String[] permissions) {
        SysAttribute sysAttribute = sysAttributeService.assign(attributeId, permissions);
        return result(sysAttribute);
    }
}