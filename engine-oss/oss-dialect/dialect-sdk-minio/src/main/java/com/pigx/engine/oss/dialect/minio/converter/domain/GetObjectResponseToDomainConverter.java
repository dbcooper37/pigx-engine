package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.object.GetObjectDomain;
import io.minio.GetObjectResponse;
import org.springframework.core.convert.converter.Converter;


public class GetObjectResponseToDomainConverter implements Converter<GetObjectResponse, GetObjectDomain> {
    @Override
    public GetObjectDomain convert(GetObjectResponse source) {

        GetObjectDomain domain = new GetObjectDomain();
        domain.setObjectContent(source);
        domain.setBucketName(source.bucket());
        domain.setRegion(source.region());
        domain.setObjectName(source.object());

        return domain;
    }
}
