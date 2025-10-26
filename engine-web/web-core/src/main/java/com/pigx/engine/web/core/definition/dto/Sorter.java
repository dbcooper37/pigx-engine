package com.pigx.engine.web.core.definition.dto;

import com.pigx.engine.core.definition.domain.AbstractDto;
import com.pigx.engine.web.core.annotation.EnumeratedValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "排序参数BO对象")
/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/definition/dto/Sorter.class */
public class Sorter extends AbstractDto {

    @EnumeratedValue(names = {"ASC", "DESC"}, message = "排序方式的值只能是大写 ASC 或者 DESC")
    @Schema(name = "排序方向", title = "排序方向的值只能是大写 ASC 或者 DESC, 默认值：DESC", defaultValue = "DESC")
    private String direction = "DESC";

    @Schema(name = "属性值", title = "指定排序的字段名称")
    private String[] properties;

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getProperties() {
        return this.properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }
}
