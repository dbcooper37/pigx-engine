package com.pigx.engine.oss.dialect.minio.service;

import com.pigx.engine.oss.dialect.core.exception.*;
import com.pigx.engine.oss.dialect.minio.definition.pool.MinioClientObjectPool;
import com.pigx.engine.oss.dialect.minio.definition.service.BaseMinioService;
import io.minio.DeleteBucketTagsArgs;
import io.minio.GetBucketTagsArgs;
import io.minio.MinioClient;
import io.minio.SetBucketTagsArgs;
import io.minio.errors.*;
import io.minio.messages.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Service
public class MinioBucketTagsService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(MinioBucketPolicyService.class);

    public MinioBucketTagsService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     * @return {@link Tags}
     */
    public Tags getBucketTags(String bucketName) {
        return getBucketTags(bucketName, null);
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return {@link Tags}
     */
    public Tags getBucketTags(String bucketName, String region) {
        return getBucketTags(GetBucketTagsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param getBucketTagsArgs {@link GetBucketTagsArgs}
     * @return {@link Tags}
     */
    public Tags getBucketTags(GetBucketTagsArgs getBucketTagsArgs) {
        String function = "getBucketTags";
        MinioClient minioClient = getClient();

        try {
            return minioClient.getBucketTags(getBucketTagsArgs);
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
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName 存储桶名称
     * @param tags       标签
     */
    public void setBucketTags(String bucketName, Tags tags) {
        setBucketTags(bucketName, null, tags);
    }

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param tags       标签
     */
    public void setBucketTags(String bucketName, String region, Tags tags) {
        setBucketTags(SetBucketTagsArgs.builder().bucket(bucketName).region(region).tags(tags).build());
    }

    /**
     * 设置 Bucket 标签
     *
     * @param setBucketTagsArgs {@link SetBucketTagsArgs}
     */
    public void setBucketTags(SetBucketTagsArgs setBucketTagsArgs) {
        String function = "setBucketTags";
        MinioClient minioClient = getClient();

        try {
            minioClient.setBucketTags(setBucketTagsArgs);
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
     * 删除 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     */
    public void deleteBucketTags(String bucketName) {
        deleteBucketTags(bucketName, null);
    }

    /**
     * 删除 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public void deleteBucketTags(String bucketName, String region) {
        deleteBucketTags(DeleteBucketTagsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 标签
     *
     * @param deleteBucketTagsArgs {@link DeleteBucketTagsArgs}
     */
    public void deleteBucketTags(DeleteBucketTagsArgs deleteBucketTagsArgs) {
        String function = "deleteBucketTags";
        MinioClient minioClient = getClient();

        try {
            minioClient.deleteBucketTags(deleteBucketTagsArgs);
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
}
