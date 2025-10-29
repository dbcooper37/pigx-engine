package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.UploadPartResult;
import com.pigx.engine.oss.specification.domain.multipart.UploadPartDomain;
import org.springframework.core.convert.converter.Converter;


public class UploadPartResultToDomainConverter implements Converter<UploadPartResult, UploadPartDomain> {
    @Override
    public UploadPartDomain convert(UploadPartResult source) {

        UploadPartDomain domain = new UploadPartDomain();
        domain.setPartNumber(source.getPartNumber());
        domain.setEtag(source.getETag());

        return domain;
    }
}
