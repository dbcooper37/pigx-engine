package com.pigx.engine.oss.dialect.minio.service;

import com.pigx.engine.oss.dialect.core.exception.*;
import com.pigx.engine.oss.dialect.minio.definition.pool.MinioClientObjectPool;
import com.pigx.engine.oss.dialect.minio.definition.service.BaseMinioService;
import io.minio.GetBucketVersioningArgs;
import io.minio.MinioClient;
import io.minio.SetBucketVersioningArgs;
import io.minio.errors.*;
import io.minio.messages.VersioningConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Service
public class MinioBucketVersioningService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(MinioBucketVersioningService.class);

    public MinioBucketVersioningService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 开启 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public void enabledBucketVersioning(String bucketName) {
        setBucketVersioning(bucketName, VersioningConfiguration.Status.ENABLED);
    }

    /**
     * 开启 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void enabledBucketVersioning(String bucketName, String region) {
        setBucketVersioning(bucketName, region, VersioningConfiguration.Status.ENABLED);
    }

    /**
     * 暂停 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public void suspendedBucketVersioning(String bucketName) {
        setBucketVersioning(bucketName, VersioningConfiguration.Status.SUSPENDED);
    }

    /**
     * 暂停 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void suspendedBucketVersioning(String bucketName, String region) {
        setBucketVersioning(bucketName, region, VersioningConfiguration.Status.SUSPENDED);
    }

    /**
     * 关闭 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public void offBucketVersioning(String bucketName) {
        setBucketVersioning(bucketName, VersioningConfiguration.Status.OFF);
    }

    /**
     * 关闭 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void offBucketVersioning(String bucketName, String region) {
        setBucketVersioning(bucketName, region, VersioningConfiguration.Status.OFF);
    }


    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param status     {@link  VersioningConfiguration.Status}
     */
    public void setBucketVersioning(String bucketName, VersioningConfiguration.Status status) {
        setBucketVersioning(bucketName, status, null);
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param status     {@link  VersioningConfiguration.Status}
     * @param mfaDelete  mfaDelete
     */
    public void setBucketVersioning(String bucketName, VersioningConfiguration.Status status, Boolean mfaDelete) {
        setBucketVersioning(bucketName, new VersioningConfiguration(status, mfaDelete));
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName              bucketName
     * @param versioningConfiguration {@link VersioningConfiguration}
     */
    public void setBucketVersioning(String bucketName, VersioningConfiguration versioningConfiguration) {
        setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucketName).config(versioningConfiguration).build());
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     * @param status     {@link  VersioningConfiguration.Status}
     */
    public void setBucketVersioning(String bucketName, String region, VersioningConfiguration.Status status) {
        setBucketVersioning(bucketName, region, status, null);
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     * @param status     {@link  VersioningConfiguration.Status}
     * @param mfaDelete  mfaDelete
     */
    public void setBucketVersioning(String bucketName, String region, VersioningConfiguration.Status status, Boolean mfaDelete) {
        setBucketVersioning(bucketName, region, new VersioningConfiguration(status, mfaDelete));
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName              bucketName
     * @param region                  region
     * @param versioningConfiguration {@link VersioningConfiguration}
     */
    public void setBucketVersioning(String bucketName, String region, VersioningConfiguration versioningConfiguration) {
        setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucketName).region(region).config(versioningConfiguration).build());
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param setBucketVersioningArgs {@link SetBucketVersioningArgs}
     */
    public void setBucketVersioning(SetBucketVersioningArgs setBucketVersioningArgs) {
        String function = "setBucketVersioning";
        MinioClient minioClient = getClient();

        try {
            minioClient.setBucketVersioning(setBucketVersioningArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param bucketName bucketName
     * @return {@link VersioningConfiguration}
     */
    public VersioningConfiguration getBucketVersioning(String bucketName) {
        return getBucketVersioning(GetBucketVersioningArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link VersioningConfiguration}
     */
    public VersioningConfiguration getBucketVersioning(String bucketName, String region) {
        return getBucketVersioning(GetBucketVersioningArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param getBucketVersioningArgs {@link GetBucketVersioningArgs}
     * @return {@link VersioningConfiguration}
     */
    public VersioningConfiguration getBucketVersioning(GetBucketVersioningArgs getBucketVersioningArgs) {
        String function = "getBucketVersioning";
        MinioClient minioClient = getClient();

        try {
            return minioClient.getBucketVersioning(getBucketVersioningArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException [{}].", function, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }
}
