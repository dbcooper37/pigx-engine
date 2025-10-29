package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.CopyPartResult;
import com.pigx.engine.oss.specification.domain.multipart.UploadPartCopyDomain;
import org.springframework.core.convert.converter.Converter;


public class CopyPartResultToDomainConverter implements Converter<CopyPartResult, UploadPartCopyDomain> {

    @Override
    public UploadPartCopyDomain convert(CopyPartResult source) {

        UploadPartCopyDomain domain = new UploadPartCopyDomain();
        domain.setLastModifiedDate(source.getLastModifiedDate());
        domain.setPartNumber(source.getPartNumber());
        domain.setEtag(source.getETag());

        return domain;
    }
}
