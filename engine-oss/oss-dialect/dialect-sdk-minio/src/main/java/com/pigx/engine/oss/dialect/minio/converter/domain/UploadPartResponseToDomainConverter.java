package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.multipart.UploadPartDomain;
import io.minio.UploadPartResponse;
import org.springframework.core.convert.converter.Converter;


public class UploadPartResponseToDomainConverter implements Converter<UploadPartResponse, UploadPartDomain> {

    @Override
    public UploadPartDomain convert(UploadPartResponse source) {
        UploadPartDomain domain = new UploadPartDomain();
        domain.setPartNumber(source.partNumber());
        domain.setEtag(source.etag());
        return domain;
    }
}
