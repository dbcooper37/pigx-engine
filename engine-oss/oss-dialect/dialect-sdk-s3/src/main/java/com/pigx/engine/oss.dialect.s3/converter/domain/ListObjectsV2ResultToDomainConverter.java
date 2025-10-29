package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.pigx.engine.oss.dialect.core.utils.ConverterUtils;
import com.pigx.engine.oss.specification.domain.object.ListObjectsV2Domain;
import com.pigx.engine.oss.specification.domain.object.ObjectDomain;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


public class ListObjectsV2ResultToDomainConverter implements Converter<ListObjectsV2Result, ListObjectsV2Domain> {
    @Override
    public ListObjectsV2Domain convert(ListObjectsV2Result source) {

        List<ObjectDomain> summaries = ConverterUtils.toDomains(source.getObjectSummaries(), new ObjectSummaryToDomainConverter(source.getDelimiter()));

        ListObjectsV2Domain domain = new ListObjectsV2Domain();
        domain.setSummaries(summaries);
        domain.setTruncated(source.isTruncated());
        domain.setKeyCount(source.getKeyCount());
        domain.setNextContinuationToken(source.getNextContinuationToken());
        domain.setContinuationToken(source.getContinuationToken());
        domain.setPrefix(source.getPrefix());
        domain.setMarker(source.getStartAfter());
        domain.setDelimiter(source.getDelimiter());
        domain.setMaxKeys(source.getMaxKeys());
        domain.setEncodingType(source.getEncodingType());
        domain.setBucketName(source.getBucketName());

        return domain;
    }
}
