package com.pigx.engine.oss.specification.domain.bucket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.oss.specification.core.domain.OssDomain;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;


@Schema(name = "存储桶")
public class BucketDomain implements OssDomain {

    /**
     * 存储桶名称
     */
    @Schema(name = "存储桶名称")
    private String bucketName;

    /**
     * 存储桶所有者信息
     */
    @Schema(name = "存储桶所有者信息", description = "Minio listBuckets API 返回的 Bucket 信息中不包含 OwnerDomain 信息")
    private OwnerDomain ownerAttribute;

    /**
     * 存储桶创建时间
     */
    @Schema(name = "存储桶创建时间")
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public OwnerDomain getOwner() {
        return ownerAttribute;
    }

    public void setOwner(OwnerDomain ownerAttribute) {
        this.ownerAttribute = ownerAttribute;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", bucketName)
                .add("ownerAttribute", ownerAttribute)
                .add("creationDate", creationDate)
                .toString();
    }
}
