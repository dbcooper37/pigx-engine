package com.pigx.engine.oss.rest.minio.definition;

import com.pigx.engine.core.definition.utils.DateTimeUtils;
import io.minio.ObjectConditionalReadArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


public abstract class ObjectConditionalReadRequest<B extends ObjectConditionalReadArgs.Builder<B, A>, A extends ObjectConditionalReadArgs> extends ObjectReadRequest<B, A> {

    @Schema(name = "offset")
    @DecimalMin(value = "0", message = "offset 参数不能小于 0")
    private Long offset;

    @Schema(name = "length")
    @DecimalMin(value = "0", message = "length 参数不能小于 0")
    private Long length;

    @Schema(name = "matchETag")
    private String matchETag;

    @Schema(name = "notMatchETag")
    private String notMatchETag;
    private String modifiedSince;
    private String unmodifiedSince;

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

    public String getMatchETag() {
        return matchETag;
    }

    public void setMatchETag(String matchETag) {
        this.matchETag = matchETag;
    }

    public String getNotMatchETag() {
        return notMatchETag;
    }

    public void setNotMatchETag(String notMatchETag) {
        this.notMatchETag = notMatchETag;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    public String getUnmodifiedSince() {
        return unmodifiedSince;
    }

    public void setUnmodifiedSince(String unmodifiedSince) {
        this.unmodifiedSince = unmodifiedSince;
    }

    @Override
    public void prepare(B builder) {

        if (ObjectUtils.isNotEmpty(getLength()) && getLength() >= 0) {
            builder.length(getLength());
        }

        if (ObjectUtils.isNotEmpty(getOffset()) && getOffset() >= 0) {
            builder.length(getOffset());
        }

        if (StringUtils.isNotBlank(getMatchETag())) {
            builder.matchETag(getMatchETag());
        }

        if (StringUtils.isNotBlank(getNotMatchETag())) {
            builder.matchETag(getNotMatchETag());
        }

        if (StringUtils.isNotBlank(getModifiedSince())) {
            builder.modifiedSince(DateTimeUtils.stringToZonedDateTime(getModifiedSince()));
        }
        if (StringUtils.isNotBlank(getUnmodifiedSince())) {
            builder.unmodifiedSince(DateTimeUtils.stringToZonedDateTime(getUnmodifiedSince()));
        }
        super.prepare(builder);
    }
}
