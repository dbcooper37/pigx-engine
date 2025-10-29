package com.pigx.engine.oss.rest.minio.service;

import com.pigx.engine.oss.dialect.minio.converter.ResponseToStatObjectDomainConverter;
import com.pigx.engine.oss.dialect.minio.domain.StatObjectDomain;
import com.pigx.engine.oss.dialect.minio.service.MinioObjectService;
import com.pigx.engine.oss.dialect.minio.service.MinioObjectTagsService;
import com.pigx.engine.oss.rest.minio.bo.ObjectSettingBusiness;
import io.minio.StatObjectResponse;
import io.minio.messages.Tags;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;


@Service
public class MinioObjectSettingService {

    private final Converter<StatObjectResponse, StatObjectDomain> toStatObjectDomain;

    private final MinioObjectService minioObjectService;
    private final MinioObjectTagsService minioObjectTagsService;

    public MinioObjectSettingService(MinioObjectService minioObjectService, MinioObjectTagsService minioObjectTagsService) {
        this.minioObjectService = minioObjectService;
        this.minioObjectTagsService = minioObjectTagsService;
        this.toStatObjectDomain = new ResponseToStatObjectDomainConverter();
    }

    public ObjectSettingBusiness get(String bucketName, String region, String objectName) {
        StatObjectResponse statObjectResponse = minioObjectService.statObject(bucketName, region, objectName);
        StatObjectDomain statObjectDomain = toStatObjectDomain.convert(statObjectResponse);

        Tags tags = minioObjectTagsService.getObjectTags(bucketName, region, objectName);

        ObjectSettingBusiness business = new ObjectSettingBusiness();
        business.setTags(tags.get());
        business.setRetentionMode(statObjectDomain.getRetentionMode());
        business.setRetentionRetainUntilDate(statObjectDomain.getRetentionRetainUntilDate());
        business.setLegalHold(statObjectDomain.getLegalHold());
        business.setDeleteMarker(statObjectDomain.getDeleteMarker());
        business.setEtag(statObjectDomain.getEtag());
        business.setLastModified(statObjectDomain.getLastModified());
        business.setSize(statObjectDomain.getSize());
        business.setUserMetadata(statObjectDomain.getUserMetadata());

        return business;
    }
}
