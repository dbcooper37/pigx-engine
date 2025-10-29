package com.pigx.engine.oss.dialect.s3.definition.pool;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import com.pigx.engine.oss.dialect.s3.properties.S3Properties;


public class S3ClientPooledObjectFactory extends AbstractOssClientPooledObjectFactory<AmazonS3> {

    private final S3Properties s3Properties;

    public S3ClientPooledObjectFactory(S3Properties s3Properties) {
        super(s3Properties);
        this.s3Properties = s3Properties;
    }


    @Override
    public AmazonS3 create() throws Exception {

        AWSCredentials awsCredentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        return AmazonS3Client.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getEndpoint(), null))
                .build();
    }
}
