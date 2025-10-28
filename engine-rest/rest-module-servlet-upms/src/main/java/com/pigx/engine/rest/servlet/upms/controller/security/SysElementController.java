package com.pigx.engine.rest.servlet.upms.controller.security;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.upms.converter.SysElementToTreeNodeConverter;
import com.pigx.engine.logic.upms.entity.security.SysElement;
import com.pigx.engine.logic.upms.service.security.SysElementService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import com.pigx.engine.web.core.annotation.AccessLimited;
import cn.hutool.v7.core.tree.MapTree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/security/element"})
@Tags({@Tag(name = "用户安全管理接口"), @Tag(name = "系统菜单管理接口")})
@RestController
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/security/SysElementController.class */
public class SysElementController extends AbstractJpaWriteableController<SysElement, String> {
    private final SysElementService sysElementService;

    public SysElementController(SysElementService sysElementService) {
        this.sysElementService = sysElementService;
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    public BaseJpaWriteableService<SysElement, String> getService() {
        return this.sysElementService;
    }

    @GetMapping({"/tree"})
    @Operation(summary = "获取菜单树", description = "获取系统菜单树", responses = {@ApiResponse(description = "单位列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})})
    public Result<List<MapTree<String>>> findTree() {
        return result(this.sysElementService.findAll(), new SysElementToTreeNodeConverter());
    }

    @GetMapping({"/condition"})
    @Operation(summary = "模糊条件查询前端元素", description = "根据动态输入的字段模糊查询前端元素", responses = {@ApiResponse(description = "前端元素列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})})
    @Parameters({@Parameter(name = "pageNumber", required = true, description = "当前页码"), @Parameter(name = "pageSize", required = true, description = "每页显示数量"), @Parameter(name = "path", description = "组件路径"), @Parameter(name = "title", description = "组件标题")})
    public Result<Map<String, Object>> findByCondition(@RequestParam("pageNumber") @NotNull Integer pageNumber, @RequestParam("pageSize") @NotNull Integer pageSize, @RequestParam(value = "path", required = false) String path, @RequestParam(value = "title", required = false) String title) {
        Page<SysElement> pages = this.sysElementService.findByCondition(pageNumber.intValue(), pageSize.intValue(), path, title);
        return resultFromPage(pages);
    }

    @PutMapping
    @Operation(summary = "给前端元素分配角色", description = "给前端元素分配角色")
    @Parameters({@Parameter(name = "elementId", required = true, description = "元素ID"), @Parameter(name = "roles[]", required = true, description = "角色对象组成的数组")})
    public Result<SysElement> assign(@RequestParam(name = "elementId") String elementId, @RequestParam(name = "roles[]") String[] roles) {
        SysElement sysElement = this.sysElementService.assign(elementId, roles);
        return result((SysElementController) sysElement);
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    @AccessLimited
    @GetMapping({"/list"})
    @Operation(summary = "获取全部前端元素接口", description = "获取全部前端元素接口", responses = {@ApiResponse(description = "元素列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))})})
    public Result<List<SysElement>> findAll() {
        return result((List) this.sysElementService.findAll());
    }

    @Override // com.pigx.engine.web.api.servlet.BindingController
    @Operation(summary = "根据ID查询元素", description = "根据ID查询元素", requestBody = @RequestBody(content = {@Content(mediaType = "application/json")}), responses = {@ApiResponse(description = "操作消息", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "实体ID，@Id注解对应的实体属性")})
    @AccessLimited
    @GetMapping({"/{id}"})
    public Result<SysElement> findById(@PathVariable String id) {
        return super.findById((SysElementController) id);
    }
}
