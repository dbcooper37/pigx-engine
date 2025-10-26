package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.service.BasePageService;
import com.pigx.engine.web.core.annotation.AccessLimited;
import com.pigx.engine.web.core.definition.dto.Pager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

@SecurityRequirement(name = SystemConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME)
/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/AbstractReadableController.class */
public abstract class AbstractReadableController<E extends BaseEntity, ID extends Serializable, S extends BasePageService<E, ID>> implements PageController<E, ID, S> {
    @Operation(summary = "分页查询数据", description = "通过pageNumber和pageSize获取分页数据", responses = {@ApiResponse(description = "单位列表", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})})
    @Parameters({@Parameter(name = "pager", required = true, in = ParameterIn.QUERY, description = "分页Bo对象", schema = @Schema(implementation = Pager.class))})
    @AccessLimited
    @GetMapping
    public Result<Map<String, Object>> findByPage(@Validated Pager pager) {
        if (ArrayUtils.isNotEmpty(pager.getProperties())) {
            Sort.Direction direction = Sort.Direction.valueOf(pager.getDirection());
            return super.findByPage(pager.getPageNumber(), pager.getPageSize(), direction, pager.getProperties());
        }
        return super.findByPage(pager.getPageNumber(), pager.getPageSize());
    }
}
