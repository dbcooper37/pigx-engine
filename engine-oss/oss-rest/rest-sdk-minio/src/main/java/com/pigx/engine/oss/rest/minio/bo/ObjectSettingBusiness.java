package com.pigx.engine.oss.rest.minio.bo;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.oss.dialect.minio.enums.RetentionModeEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;


public class ObjectSettingBusiness implements BaseEntity {

    @Schema(name = "标签")
    private Map<String, String> tags;
    @Schema(name = "保留模式")
    private RetentionModeEnums retentionMode;
    @Schema(name = "保留截止日期")
    private String retentionRetainUntilDate;
    @Schema(name = "是否合规持有")
    private Boolean legalHold;
    @Schema(name = "是否标记删除")
    private Boolean deleteMarker;
    @Schema(name = "ETag")
    private String etag;
    @Schema(name = "最后修改时间")
    private String lastModified;
    @Schema(name = "对象大小")
    private Long size;
    @Schema(name = "用户自定义元数据")
    private Map<String, String> userMetadata;

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public RetentionModeEnums getRetentionMode() {
        return retentionMode;
    }

    public void setRetentionMode(RetentionModeEnums retentionMode) {
        this.retentionMode = retentionMode;
    }

    public String getRetentionRetainUntilDate() {
        return retentionRetainUntilDate;
    }

    public void setRetentionRetainUntilDate(String retentionRetainUntilDate) {
        this.retentionRetainUntilDate = retentionRetainUntilDate;
    }

    public Boolean getLegalHold() {
        return legalHold;
    }

    public void setLegalHold(Boolean legalHold) {
        this.legalHold = legalHold;
    }

    public Boolean getDeleteMarker() {
        return deleteMarker;
    }

    public void setDeleteMarker(Boolean deleteMarker) {
        this.deleteMarker = deleteMarker;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("retentionMode", retentionMode)
                .add("retentionRetainUntilDate", retentionRetainUntilDate)
                .add("legalHold", legalHold)
                .add("deleteMarker", deleteMarker)
                .add("etag", etag)
                .add("lastModified", lastModified)
                .add("size", size)
                .toString();
    }
}
