package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import io.minio.messages.Part;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PartToDomainConverter implements Converter<List<Part>, List<PartSummaryDomain>> {

    @Override
    public List<PartSummaryDomain> convert(List<Part> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream().map(this::convert).toList();
        }
        return new ArrayList<>();
    }

    private PartSummaryDomain convert(Part source) {

        PartSummaryDomain domain = new PartSummaryDomain();
        domain.setPartSize(source.partSize());
        domain.setLastModifiedDate(Date.from(source.lastModified().toInstant()));
        domain.setPartNumber(source.partNumber());
        domain.setEtag(source.etag());
        return domain;
    }
}
