package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.pigx.engine.oss.specification.domain.object.DeleteObjectDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;


public class DeleteObjectsResultToDomainConverter implements Converter<DeleteObjectsResult, List<DeleteObjectDomain>> {
    @Override
    public List<DeleteObjectDomain> convert(DeleteObjectsResult source) {

        List<DeleteObjectsResult.DeletedObject> items = source.getDeletedObjects();

        if (CollectionUtils.isNotEmpty(items)) {
            return items.stream().map(item -> {
                DeleteObjectDomain domain = new DeleteObjectDomain();
                domain.setObjectName(item.getKey());
                domain.setVersionId(item.getVersionId());
                return domain;
            }).toList();
        }

        return new ArrayList<>();
    }
}
