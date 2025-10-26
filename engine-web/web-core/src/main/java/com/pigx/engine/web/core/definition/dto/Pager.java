package com.pigx.engine.web.core.definition.dto;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(name = "分页参数BO对象")
/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/definition/dto/Pager.class */
public class Pager extends Sorter {

    @NotNull(message = "页码不能为空")
    @Schema(name = "页码", type = "integer", minimum = "0", defaultValue = "0")
    @Min(value = 0, message = "页码不能为负")
    private Integer pageNumber = 0;

    @NotNull(message = "每页条数不能为空")
    @Schema(name = "每页数据条数", type = "integer", minimum = "0", maximum = "1000", defaultValue = "10")
    @Max(value = 1000, message = "每页条数不能超过1000")
    @Min(value = 1, message = "每页条数至少为1条")
    private Integer pageSize = 10;

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("pageNumber", this.pageNumber).add("pageSize", this.pageSize).toString();
    }
}
