package com.pigx.engine.oss.specification.arguments.base;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;

import java.util.Date;
import java.util.List;


public abstract class ObjectConditionalReadArguments extends ObjectReadArguments {
    @Schema(name = "offset")
    @DecimalMin(value = "0", message = "offset 参数不能小于 0")
    private Long offset;

    @Schema(name = "length")
    @DecimalMin(value = "0", message = "length 参数不能小于 0")
    private Long length;

    @Schema(name = "ETag值反向匹配约束列表")
    private List<String> notMatchEtag;

    @Schema(name = "ETag值匹配约束列表")
    private List<String> matchETag;

    @Schema(name = "修改时间匹配约束")
    private Date modifiedSince;

    @Schema(name = "修改时间反向匹配约束")
    private Date unmodifiedSince;

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public List<String> getNotMatchEtag() {
        return notMatchEtag;
    }

    public void setNotMatchEtag(List<String> notMatchEtag) {
        this.notMatchEtag = notMatchEtag;
    }

    public List<String> getMatchETag() {
        return matchETag;
    }

    public void setMatchETag(List<String> matchETag) {
        this.matchETag = matchETag;
    }

    public Date getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(Date modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    public Date getUnmodifiedSince() {
        return unmodifiedSince;
    }

    public void setUnmodifiedSince(Date unmodifiedSince) {
        this.unmodifiedSince = unmodifiedSince;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("offset", offset)
                .add("length", length)
                .add("modifiedSince", modifiedSince)
                .add("unmodifiedSince", unmodifiedSince)
                .toString();
    }
}
