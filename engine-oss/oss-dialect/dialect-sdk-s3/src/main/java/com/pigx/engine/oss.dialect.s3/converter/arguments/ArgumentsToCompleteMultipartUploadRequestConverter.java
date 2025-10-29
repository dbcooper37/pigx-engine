package com.pigx.engine.oss.dialect.s3.converter.arguments;

import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.PartETag;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.multipart.CompleteMultipartUploadArguments;
import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


public class ArgumentsToCompleteMultipartUploadRequestConverter extends ArgumentsToBucketConverter<CompleteMultipartUploadArguments, CompleteMultipartUploadRequest> {
    @Override
    public CompleteMultipartUploadRequest getInstance(CompleteMultipartUploadArguments arguments) {

        CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest();
        return request
                .withBucketName(arguments.getBucketName())
                .withKey(arguments.getObjectName())
                .withUploadId(arguments.getUploadId())
                .withPartETags(convert(arguments.getParts()));
    }

    private List<PartETag> convert(List<PartSummaryDomain> attributes) {
        if (CollectionUtils.isNotEmpty(attributes)) {
            return attributes.stream().map(item -> new PartETag(item.getPartNumber(), item.getEtag())).toList();
        }
        return new ArrayList<>();
    }
}
