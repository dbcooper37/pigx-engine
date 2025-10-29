package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import com.pigx.engine.oss.specification.domain.object.ObjectDomain;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.core.convert.converter.Converter;


public class ObjectSummaryToDomainConverter implements Converter<S3ObjectSummary, ObjectDomain> {

    private final String delimiter;

    public ObjectSummaryToDomainConverter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public ObjectDomain convert(S3ObjectSummary source) {

        ObjectDomain objectDomain = new ObjectDomain();
        objectDomain.setBucketName(source.getBucketName());
        objectDomain.setObjectName(source.getKey());
        objectDomain.setETag(source.getETag());
        objectDomain.setSize(source.getSize());
        objectDomain.setLastModified(source.getLastModified());
        objectDomain.setStorageClass(source.getStorageClass());

        if (ObjectUtils.isNotEmpty(source.getOwner())) {
            OwnerDomain ownerAttributeDomain = new OwnerDomain();
            ownerAttributeDomain.setId(ownerAttributeDomain.getId());
            ownerAttributeDomain.setDisplayName(ownerAttributeDomain.getDisplayName());
            objectDomain.setOwner(ownerAttributeDomain);
        }

        objectDomain.setDir(StringUtils.isNotBlank(this.delimiter) && Strings.CS.contains(source.getKey(), this.delimiter));

        return objectDomain;
    }
}
