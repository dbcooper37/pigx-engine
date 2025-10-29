package com.pigx.engine.oss.dialect.minio.definition.pool;

import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import com.pigx.engine.oss.dialect.minio.properties.MinioProperties;
import io.minio.MinioClient;


public class MinioClientPooledObjectFactory extends AbstractOssClientPooledObjectFactory<MinioClient> {

    private final MinioProperties minioProperties;

    public MinioClientPooledObjectFactory(MinioProperties minioProperties) {
        super(minioProperties);
        this.minioProperties = minioProperties;
    }

    @Override
    public MinioClient create() throws Exception {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
