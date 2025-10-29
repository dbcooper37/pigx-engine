package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketEncryptionService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketEncryptionService.class);

    public S3BucketEncryptionService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶分加密
     *
     * @param request {@link DeleteBucketEncryptionRequest}
     * @return {@link DeleteBucketEncryptionResult}
     */
    public DeleteBucketEncryptionResult deleteBucketEncryption(DeleteBucketEncryptionRequest request) {
        String function = "deleteBucketEncryption";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteBucketEncryption(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶加密
     *
     * @param request {@link GetBucketEncryptionRequest}
     * @return {@link GetBucketEncryptionResult}
     */
    public GetBucketEncryptionResult getBucketEncryption(GetBucketEncryptionRequest request) {
        String function = "getBucketEncryption";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketEncryption(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶加密
     *
     * @param request {@link SetBucketEncryptionRequest}
     * @return {@link SetBucketEncryptionResult}
     */
    public SetBucketEncryptionResult setBucketEncryption(SetBucketEncryptionRequest request) {
        String function = "setBucketEncryption";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setBucketEncryption(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
