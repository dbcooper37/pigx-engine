package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.Owner;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import com.pigx.engine.oss.specification.domain.multipart.UploadDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;


public class MultipartUploadToDomainConverter implements Converter<List<MultipartUpload>, List<UploadDomain>> {

    private final Converter<Owner, OwnerDomain> toAttribute = new OwnerToDomainConverter();

    @Override
    public List<UploadDomain> convert(List<MultipartUpload> source) {

        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream().map(this::convert).toList();
        }

        return new ArrayList<>();
    }

    private UploadDomain convert(MultipartUpload source) {
        UploadDomain domain = new UploadDomain();
        domain.setKey(source.getKey());
        domain.setUploadId(source.getUploadId());
        domain.setOwner(toAttribute.convert(source.getOwner()));
        domain.setInitiator(toAttribute.convert(source.getInitiator()));
        domain.setStorageClass(source.getStorageClass());
        domain.setInitiated(source.getInitiated());
        return domain;
    }
}
