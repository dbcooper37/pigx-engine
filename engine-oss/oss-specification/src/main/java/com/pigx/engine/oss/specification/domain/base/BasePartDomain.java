package com.pigx.engine.oss.specification.domain.base;

import com.google.common.base.MoreObjects;
import com.pigx.engine.oss.specification.core.domain.OssDomain;
import io.swagger.v3.oas.annotations.media.Schema;


public abstract class BasePartDomain implements OssDomain {

    @Schema(name = "分片编号")
    private int partNumber;

    @Schema(name = "新对象的ETag值")
    private String etag;

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("partNumber", partNumber)
                .add("etag", etag)
                .toString();
    }
}
