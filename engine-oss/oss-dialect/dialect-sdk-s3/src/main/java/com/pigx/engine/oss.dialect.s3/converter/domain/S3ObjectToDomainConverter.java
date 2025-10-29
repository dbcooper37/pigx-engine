package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.S3Object;
import com.pigx.engine.oss.specification.domain.object.GetObjectDomain;
import org.springframework.core.convert.converter.Converter;


public class S3ObjectToDomainConverter implements Converter<S3Object, GetObjectDomain> {
    @Override
    public GetObjectDomain convert(S3Object source) {

        GetObjectDomain domain = new GetObjectDomain();
        domain.setObjectContent(source.getObjectContent());
        domain.setBucketName(source.getBucketName());
        domain.setObjectName(source.getKey());

        return domain;
    }
}
