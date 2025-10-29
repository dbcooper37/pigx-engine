package com.pigx.engine.oss.dialect.s3.converter.arguments;

import cn.hutool.v7.core.date.DateUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.pigx.engine.oss.dialect.s3.definition.arguments.ArgumentsToBucketConverter;
import com.pigx.engine.oss.specification.arguments.object.GeneratePresignedUrlArguments;


public class ArgumentsToGeneratePreSignedUrlRequestConverter extends ArgumentsToBucketConverter<GeneratePresignedUrlArguments, GeneratePresignedUrlRequest> {
    @Override
    public GeneratePresignedUrlRequest getInstance(GeneratePresignedUrlArguments arguments) {

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(arguments.getBucketName(), arguments.getObjectName());
        request.setMethod(HttpMethod.valueOf(arguments.getMethod().name()));
        request.setVersionId(arguments.getVersionId());
        request.setContentMd5(arguments.getContentMD5());
        request.setContentType(arguments.getContentType());
        request.setExpiration(DateUtil.offsetSecond(DateUtil.now(), Math.toIntExact(arguments.getExpiration().toSeconds())));
        return request;
    }
}
