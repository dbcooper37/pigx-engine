package com.pigx.engine.oss.dialect.minio.definition.pool;

import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import com.pigx.engine.oss.dialect.minio.properties.MinioProperties;


public class MinioAsyncClientPooledObjectFactory extends AbstractOssClientPooledObjectFactory<MinioAsyncClient> {

    private final MinioProperties minioProperties;

    public MinioAsyncClientPooledObjectFactory(MinioProperties minioProperties) {
        super(minioProperties);
        this.minioProperties = minioProperties;
    }

    @Override
    public MinioAsyncClient create() throws Exception {
        io.minio.MinioAsyncClient minioAsyncClient = io.minio.MinioAsyncClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        return new MinioAsyncClient(minioAsyncClient);
    }
}
