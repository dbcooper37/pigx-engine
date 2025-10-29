package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import com.pigx.engine.oss.specification.domain.multipart.ListPartsDomain;
import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


public class PartListingToDomainConverter implements Converter<PartListing, ListPartsDomain> {

    private final Converter<Owner, OwnerDomain> toAttribute = new OwnerToDomainConverter();
    private final Converter<List<PartSummary>, List<PartSummaryDomain>> toDomain = new PartSummaryToDomainConverter();

    @Override
    public ListPartsDomain convert(PartListing source) {

        ListPartsDomain domain = new ListPartsDomain();
        domain.setOwner(toAttribute.convert(source.getOwner()));
        domain.setInitiator(toAttribute.convert(source.getInitiator()));
        domain.setStorageClass(source.getStorageClass());
        domain.setMaxParts(source.getMaxParts());
        domain.setPartNumberMarker(source.getPartNumberMarker());
        domain.setNextPartNumberMarker(source.getNextPartNumberMarker());
        domain.setTruncated(source.isTruncated());
        domain.setParts(toDomain.convert(source.getParts()));
        domain.setUploadId(source.getUploadId());
        domain.setBucketName(source.getBucketName());
        domain.setObjectName(source.getKey());

        return domain;
    }
}
