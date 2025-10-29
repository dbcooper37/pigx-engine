package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.ListPartsRequest;
import com.pigx.engine.oss.specification.arguments.multipart.ListPartsArguments;
import org.springframework.core.convert.converter.Converter;


public class ArgumentsToListPartsRequestConverter implements Converter<ListPartsArguments, ListPartsRequest> {
    @Override
    public ListPartsRequest convert(ListPartsArguments source) {

        ListPartsRequest request = new ListPartsRequest(source.getBucketName(), source.getObjectName(), source.getUploadId());
        return request.withMaxParts(source.getMaxParts()).withPartNumberMarker(source.getPartNumberMarker());
    }
}
