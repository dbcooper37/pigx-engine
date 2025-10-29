package com.pigx.engine.oss.specification.arguments.multipart;

import com.pigx.engine.oss.specification.arguments.base.BasePartArguments;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "分片列表请求参数实体", title = "分片列表请求参数实体")
public class ListPartsArguments extends BasePartArguments {

    /**
     * 分片列表中要返回的最大分片数
     */
    @Schema(name = "最大分片数")
    private Integer maxParts;

    /**
     * 可选的分片号标记，指示要列出分片的结果中的位置
     */
    @Schema(name = "分片号标记")
    private Integer partNumberMarker;

    public Integer getMaxParts() {
        return maxParts;
    }

    public void setMaxParts(Integer maxParts) {
        this.maxParts = maxParts;
    }

    public Integer getPartNumberMarker() {
        return partNumberMarker;
    }

    public void setPartNumberMarker(Integer partNumberMarker) {
        this.partNumberMarker = partNumberMarker;
    }
}
