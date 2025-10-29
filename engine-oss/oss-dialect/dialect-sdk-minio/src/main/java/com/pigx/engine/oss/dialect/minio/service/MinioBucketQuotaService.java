package com.pigx.engine.oss.dialect.minio.service;

import com.pigx.engine.oss.dialect.core.exception.OssConnectException;
import com.pigx.engine.oss.dialect.core.exception.OssIOException;
import com.pigx.engine.oss.dialect.core.exception.OssInvalidKeyException;
import com.pigx.engine.oss.dialect.core.exception.OssNoSuchAlgorithmException;
import com.pigx.engine.oss.dialect.minio.definition.pool.MinioAdminClientObjectPool;
import com.pigx.engine.oss.dialect.minio.definition.service.BaseMinioAdminService;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.QuotaUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Service
public class MinioBucketQuotaService extends BaseMinioAdminService {

    private static final Logger log = LoggerFactory.getLogger(MinioBucketQuotaService.class);

    public MinioBucketQuotaService(MinioAdminClientObjectPool minioAdminClientObjectPool) {
        super(minioAdminClientObjectPool);
    }

    /**
     * 设置存储桶配额
     *
     * @param bucketName 存储桶名称
     * @param size       配额大小
     * @param unit       配额单位
     */
    public void setBucketQuota(String bucketName, long size, QuotaUnit unit) {
        String function = "setBucketQuota";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.setBucketQuota(bucketName, size, unit);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } finally {
            close(minioAdminClient);
        }
    }

    /**
     * 清除存储桶配额
     *
     * @param bucketName 存储桶名称
     */
    public void clearBucketQuota(String bucketName) {
        setBucketQuota(bucketName, 0, QuotaUnit.KB);
    }

    /**
     * 获取存储桶配额大小
     *
     * @param bucketName 存储桶名称
     * @return 配额大小
     */
    public long getBucketQuota(String bucketName) {
        String function = "getBucketQuota";

        MinioAdminClient minioAdminClient = getClient();

        try {
            return minioAdminClient.getBucketQuota(bucketName);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } finally {
            close(minioAdminClient);
        }
    }

}
