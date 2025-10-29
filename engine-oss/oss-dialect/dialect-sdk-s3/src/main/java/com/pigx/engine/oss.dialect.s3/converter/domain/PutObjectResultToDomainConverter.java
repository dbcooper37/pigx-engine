package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.pigx.engine.oss.specification.domain.object.PutObjectDomain;
import org.springframework.core.convert.converter.Converter;


public class PutObjectResultToDomainConverter implements Converter<PutObjectResult, PutObjectDomain> {
    @Override
    public PutObjectDomain convert(PutObjectResult source) {

        PutObjectDomain domain = new PutObjectDomain();
        domain.setEtag(source.getETag());
        domain.setVersionId(source.getVersionId());
        return domain;
    }
}
