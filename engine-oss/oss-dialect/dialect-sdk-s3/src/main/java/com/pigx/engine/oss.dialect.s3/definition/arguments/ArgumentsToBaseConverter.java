package com.pigx.engine.oss.dialect.s3.definition.arguments;

import com.amazonaws.AmazonWebServiceRequest;
import com.pigx.engine.oss.specification.arguments.base.BaseArguments;
import com.pigx.engine.oss.specification.core.converter.OssConverter;
import org.apache.commons.collections4.MapUtils;


public abstract class ArgumentsToBaseConverter<S extends BaseArguments, T extends AmazonWebServiceRequest> implements OssConverter<S, T> {

    @Override
    public void prepare(S arguments, T request) {
        if (MapUtils.isNotEmpty(arguments.getExtraHeaders())) {
            arguments.getExtraHeaders().entrySet().forEach((entry -> request.putCustomRequestHeader(entry.getKey(), entry.getValue())));
        }

        if (MapUtils.isNotEmpty(arguments.getExtraQueryParams())) {
            arguments.getExtraQueryParams().entrySet().forEach((entry -> request.putCustomQueryParameter(entry.getKey(), entry.getValue())));
        }
    }
}
