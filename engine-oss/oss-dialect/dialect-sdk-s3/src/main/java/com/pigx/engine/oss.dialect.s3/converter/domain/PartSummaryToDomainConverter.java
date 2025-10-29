package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.PartSummary;
import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;


public class PartSummaryToDomainConverter implements Converter<List<PartSummary>, List<PartSummaryDomain>> {

    @Override
    public List<PartSummaryDomain> convert(List<PartSummary> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream().map(this::convert).toList();
        }
        return new ArrayList<>();
    }

    private PartSummaryDomain convert(PartSummary source) {
        PartSummaryDomain domain = new PartSummaryDomain();
        domain.setPartSize(source.getSize());
        domain.setLastModifiedDate(source.getLastModified());
        domain.setPartNumber(source.getPartNumber());
        domain.setEtag(source.getETag());
        return domain;
    }
}
