package com.pigx.engine.oss.specification.domain.object;

import com.pigx.engine.oss.specification.domain.base.ObjectWriteDomain;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


public class ObjectMetadataDomain extends ObjectWriteDomain {

    @Schema(name = "用户自定义 Metadata")
    private Map<String, String> userMetadata = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    @Schema(name = "内容大小")
    private long contentLength;

    @Schema(name = "contentType")
    private String contentType;

    @Schema(name = "最后修改时间")
    private Date LastModified;

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getLastModified() {
        return LastModified;
    }

    public void setLastModified(Date lastModified) {
        LastModified = lastModified;
    }
}
