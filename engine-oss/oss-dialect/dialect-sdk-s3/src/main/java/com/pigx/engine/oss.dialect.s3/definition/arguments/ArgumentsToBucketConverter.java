package com.pigx.engine.oss.dialect.s3.definition.arguments;

import com.amazonaws.AmazonWebServiceRequest;
import com.pigx.engine.oss.specification.arguments.base.BucketArguments;


public abstract class ArgumentsToBucketConverter<S extends BucketArguments, T extends AmazonWebServiceRequest> extends ArgumentsToBaseConverter<S, T> {

}
