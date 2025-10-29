package com.pigx.engine.oss.rest.specification.arguments;

import com.pigx.engine.oss.specification.arguments.base.ObjectArguments;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;


@Schema(name = "创建分片上传请求参数实体", title = "创建分片上传请求参数实体")
public class CreateMultipartUploadArguments extends ObjectArguments {

    @Min(value = 1, message = "分片数量不能小于等于1")
    @Schema(name = "分片数量")
    private Integer partNumber;

    public Integer getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }
}
