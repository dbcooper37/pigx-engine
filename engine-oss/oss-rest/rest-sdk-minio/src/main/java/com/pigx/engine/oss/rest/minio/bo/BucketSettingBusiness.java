package com.pigx.engine.oss.rest.minio.bo;


import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import com.pigx.engine.oss.dialect.minio.domain.VersioningConfigurationDomain;
import com.pigx.engine.oss.dialect.minio.enums.PolicyEnums;
import com.pigx.engine.oss.dialect.minio.enums.SseConfigurationEnums;

import java.util.Map;


public class BucketSettingBusiness implements BaseEntity {

    /**
     * 服务端加密方式
     */
    private SseConfigurationEnums sseConfiguration;

    private PolicyEnums policy;
    /**
     * 标签
     */
    private Map<String, String> tags;

    /**
     * 对象锁定是否开启
     */
    private ObjectLockConfigurationDomain objectLock;

    /**
     * 配额大小
     */
    private Long quota;
    /**
     * 版本设置配置
     */
    private VersioningConfigurationDomain versioning;

    public SseConfigurationEnums getSseConfiguration() {
        return sseConfiguration;
    }

    public void setSseConfiguration(SseConfigurationEnums sseConfiguration) {
        this.sseConfiguration = sseConfiguration;
    }

    public PolicyEnums getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyEnums policy) {
        this.policy = policy;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public ObjectLockConfigurationDomain getObjectLock() {
        return objectLock;
    }

    public void setObjectLock(ObjectLockConfigurationDomain objectLock) {
        this.objectLock = objectLock;
    }

    public Long getQuota() {
        return quota;
    }

    public void setQuota(Long quota) {
        this.quota = quota;
    }

    public VersioningConfigurationDomain getVersioning() {
        return versioning;
    }

    public void setVersioning(VersioningConfigurationDomain versioning) {
        this.versioning = versioning;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sseConfiguration", sseConfiguration)
                .add("policy", policy)
                .add("tags", tags)
                .add("objectLock", objectLock)
                .add("quota", quota)
                .add("versioning", versioning)
                .toString();
    }
}
