package com.pigx.engine.oss.specification.arguments.multipart;

import com.pigx.engine.oss.specification.arguments.base.BasePartArguments;
import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


@Schema(name = "完成分片上传请求参数实体", title = "完成分片上传请求参数实体")
public class CompleteMultipartUploadArguments extends BasePartArguments {

    @Schema(name = "分片列表不能为空")
    @NotEmpty(message = "分片列表不能为空")
    private List<PartSummaryDomain> parts;

    public List<PartSummaryDomain> getParts() {
        return parts;
    }

    public void setParts(List<PartSummaryDomain> parts) {
        this.parts = parts;
    }
}
