package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.pigx.engine.oss.specification.arguments.multipart.ListMultipartUploadsArguments;
import org.springframework.core.convert.converter.Converter;


public class ArgumentsToListMultipartUploadsRequest implements Converter<ListMultipartUploadsArguments, ListMultipartUploadsRequest> {
    @Override
    public ListMultipartUploadsRequest convert(ListMultipartUploadsArguments source) {

        ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(source.getBucketName());
        return request
                .withDelimiter(source.getDelimiter())
                .withPrefix(source.getPrefix())
                .withMaxUploads(source.getMaxUploads())
                .withKeyMarker(source.getKeyMarker())
                .withUploadIdMarker(source.getUploadIdMarker())
                .withEncodingType(source.getEncodingType());
    }
}
