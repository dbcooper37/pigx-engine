package com.pigx.engine.oss.dialect.minio.definition.pool;

import com.pigx.engine.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import com.pigx.engine.oss.dialect.minio.properties.MinioProperties;
import io.minio.admin.MinioAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MinioAdminClientPooledObjectFactory extends AbstractOssClientPooledObjectFactory<MinioAdminClient> {

    private static final Logger log = LoggerFactory.getLogger(MinioAdminClientPooledObjectFactory.class);

    private final MinioProperties minioProperties;

    public MinioAdminClientPooledObjectFactory(MinioProperties minioProperties) {
        super(minioProperties);
        this.minioProperties = minioProperties;
    }

    @Override
    public MinioAdminClient create() throws Exception {
        log.debug("[Herodotus] |- Minio admin client factory create object.");
        return MinioAdminClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
