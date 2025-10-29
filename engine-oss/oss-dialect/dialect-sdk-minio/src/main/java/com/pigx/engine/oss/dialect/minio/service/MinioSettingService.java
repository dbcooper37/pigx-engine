package com.pigx.engine.oss.dialect.minio.service;

import com.pigx.engine.oss.dialect.minio.definition.pool.MinioClientObjectPool;
import com.pigx.engine.oss.dialect.minio.definition.service.BaseMinioService;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MinioSettingService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(MinioSettingService.class);

    public MinioSettingService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * Disables accelerate endpoint for Amazon S3 endpoint.
     */
    public void disableAccelerateEndpoint() {
        MinioClient minioClient = getClient();
        minioClient.disableAccelerateEndpoint();
    }

    /**
     * Enables accelerate endpoint for Amazon S3 endpoint.
     */
    public void enableAccelerateEndpoint() {
        MinioClient minioClient = getClient();
        minioClient.enableAccelerateEndpoint();
    }

    /**
     * Disables dual-stack endpoint for Amazon S3 endpoint.
     */
    public void disableDualStackEndpoint() {
        MinioClient minioClient = getClient();
        minioClient.disableDualStackEndpoint();
    }

    /**
     * Enables dual-stack endpoint for Amazon S3 endpoint.
     */
    public void enableDualStackEndpoint() {
        MinioClient minioClient = getClient();
        minioClient.enableDualStackEndpoint();
    }

    /**
     * Disables virtual-style endpoint
     */
    public void disableVirtualStyleEndpoint() {
        MinioClient minioClient = getClient();
        minioClient.disableVirtualStyleEndpoint();
    }

    /**
     * Enables virtual-style endpoint.
     */
    public void enableVirtualStyleEndpoint() {
        MinioClient minioClient = getClient();
        minioClient.enableVirtualStyleEndpoint();
    }

    /**
     * Sets HTTP connect, write and read timeouts. A value of 0 means no timeout, otherwise values
     * must be between 1 and Integer.MAX_VALUE when converted to milliseconds.
     *
     * <pre>Example:{@code
     * minioClient.setTimeout(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(10),
     *     TimeUnit.SECONDS.toMillis(30));
     * }</pre>
     *
     * @param connectTimeout HTTP connect timeout in milliseconds.
     * @param writeTimeout   HTTP write timeout in milliseconds.
     * @param readTimeout    HTTP read timeout in milliseconds.
     */
    public void setTimeout(long connectTimeout, long writeTimeout, long readTimeout) {
        MinioClient minioClient = getClient();
        minioClient.setTimeout(connectTimeout, writeTimeout, readTimeout);
    }

    /**
     * Sets application's name/version to user agent. For more information about user agent refer <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">#rfc2616</a>.
     *
     * @param name    Your application name.
     * @param version Your application version.
     */
    public void setAppInfo(String name, String version) {
        MinioClient minioClient = getClient();
        minioClient.setAppInfo(name, version);
    }
}
