package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.multipart.UploadPartCopyDomain;
import io.minio.UploadPartCopyResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;


public class UploadPartCopyResponseToDomainConverter implements Converter<UploadPartCopyResponse, UploadPartCopyDomain> {
    @Override
    public UploadPartCopyDomain convert(UploadPartCopyResponse source) {

        UploadPartCopyDomain domain = new UploadPartCopyDomain();
        domain.setUploadId(source.uploadId());
        domain.setPartNumber(source.partNumber());

        if (ObjectUtils.isNotEmpty(source.result())) {
            domain.setEtag(source.result().etag());
            domain.setLastModifiedDate(Date.from(source.result().lastModified().toInstant()));
        }

        return domain;
    }
}
