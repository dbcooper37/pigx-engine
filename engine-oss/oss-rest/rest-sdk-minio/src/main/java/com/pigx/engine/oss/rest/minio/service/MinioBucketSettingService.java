package com.pigx.engine.oss.rest.minio.service;

import com.pigx.engine.oss.dialect.minio.converter.retention.ObjectLockConfigurationToDomainConverter;
import com.pigx.engine.oss.dialect.minio.converter.retention.VersioningConfigurationToDomainConverter;
import com.pigx.engine.oss.dialect.minio.converter.sse.SseConfigurationToEnumConverter;
import com.pigx.engine.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import com.pigx.engine.oss.dialect.minio.domain.VersioningConfigurationDomain;
import com.pigx.engine.oss.dialect.minio.enums.PolicyEnums;
import com.pigx.engine.oss.dialect.minio.enums.SseConfigurationEnums;
import com.pigx.engine.oss.dialect.minio.service.*;
import com.pigx.engine.oss.rest.minio.bo.BucketSettingBusiness;
import io.minio.messages.ObjectLockConfiguration;
import io.minio.messages.SseConfiguration;
import io.minio.messages.Tags;
import io.minio.messages.VersioningConfiguration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;


@Service
public class MinioBucketSettingService {

    private final Converter<SseConfiguration, SseConfigurationEnums> toSseConfigurationEnums;
    private final Converter<ObjectLockConfiguration, ObjectLockConfigurationDomain> toObjectLockDomain;
    private final Converter<VersioningConfiguration, VersioningConfigurationDomain> toVersioningDomain;

    private final MinioBucketEncryptionService minioBucketEncryptionService;
    private final MinioBucketPolicyService minioBucketPolicyService;
    private final MinioBucketTagsService minioBucketTagsService;
    private final MinioBucketVersioningService minioBucketVersioningService;
    private final MinioBucketQuotaService minioBucketQuotaService;
    private final MinioObjectLockConfigurationService minioObjectLockConfigurationService;

    public MinioBucketSettingService(MinioBucketEncryptionService minioBucketEncryptionService, MinioBucketPolicyService minioBucketPolicyService, MinioBucketTagsService minioBucketTagsService, MinioBucketVersioningService minioBucketVersioningService, MinioBucketQuotaService minioBucketQuotaService, MinioObjectLockConfigurationService minioObjectLockConfigurationService) {
        this.minioBucketEncryptionService = minioBucketEncryptionService;
        this.minioBucketPolicyService = minioBucketPolicyService;
        this.minioBucketTagsService = minioBucketTagsService;
        this.minioBucketVersioningService = minioBucketVersioningService;
        this.minioBucketQuotaService = minioBucketQuotaService;
        this.minioObjectLockConfigurationService = minioObjectLockConfigurationService;
        this.toSseConfigurationEnums = new SseConfigurationToEnumConverter();
        this.toObjectLockDomain = new ObjectLockConfigurationToDomainConverter();
        this.toVersioningDomain = new VersioningConfigurationToDomainConverter();
    }

    public BucketSettingBusiness get(String bucketName) {
        return get(bucketName, null);
    }

    public BucketSettingBusiness get(String bucketName, String region) {

        SseConfiguration sseConfiguration = minioBucketEncryptionService.getBucketEncryption(bucketName, region);
        Tags tags = minioBucketTagsService.getBucketTags(bucketName, region);
        PolicyEnums policy = minioBucketPolicyService.getBucketPolicy(bucketName, region);
        ObjectLockConfiguration objectLockConfiguration = minioObjectLockConfigurationService.getObjectLockConfiguration(bucketName, region);
        VersioningConfiguration versioningConfiguration = minioBucketVersioningService.getBucketVersioning(bucketName, region);
        long quota = minioBucketQuotaService.getBucketQuota(bucketName);

        BucketSettingBusiness entity = new BucketSettingBusiness();
        entity.setSseConfiguration(toSseConfigurationEnums.convert(sseConfiguration));
        entity.setTags(tags.get());
        entity.setPolicy(policy);
        entity.setQuota(quota);
        entity.setObjectLock(toObjectLockDomain.convert(objectLockConfiguration));
        entity.setVersioning(toVersioningDomain.convert(versioningConfiguration));

        return entity;
    }
}
