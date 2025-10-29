package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import io.minio.messages.Part;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


public class DomainToPartConverter implements Converter<List<PartSummaryDomain>, Part[]> {
    @Override
    public Part[] convert(List<PartSummaryDomain> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            List<Part> parts = source.stream().map(item -> new Part(item.getPartNumber(), item.getEtag())).toList();
            Part[] result = new Part[parts.size()];
            return parts.toArray(result);
        }
        return new Part[]{};
    }
}
