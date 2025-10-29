package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.ObjectListing;
import com.pigx.engine.oss.dialect.core.utils.ConverterUtils;
import com.pigx.engine.oss.specification.domain.object.ListObjectsDomain;
import com.pigx.engine.oss.specification.domain.object.ObjectDomain;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


public class ObjectListingToDomainConverter implements Converter<ObjectListing, ListObjectsDomain> {
    @Override
    public ListObjectsDomain convert(ObjectListing source) {

        List<ObjectDomain> summaries = ConverterUtils.toDomains(source.getObjectSummaries(), new ObjectSummaryToDomainConverter(source.getDelimiter()));

        ListObjectsDomain domain = new ListObjectsDomain();
        domain.setSummaries(summaries);
        domain.setNextMarker(source.getNextMarker());
        domain.setTruncated(source.isTruncated());
        domain.setPrefix(source.getPrefix());
        domain.setMarker(source.getMarker());
        domain.setDelimiter(source.getDelimiter());
        domain.setMaxKeys(source.getMaxKeys());
        domain.setEncodingType(source.getEncodingType());
        domain.setBucketName(source.getBucketName());

        return domain;
    }
}
