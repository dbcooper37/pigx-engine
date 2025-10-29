package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketReplicationConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketReplicationConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketReplicationConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketReplicationConfigurationRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketReplicationConfigurationService extends BaseS3Service {
    private static final Logger log = LoggerFactory.getLogger(S3BucketReplicationConfigurationService.class);

    public S3BucketReplicationConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶复制配置
     *
     * @param request {@link DeleteBucketReplicationConfigurationRequest}
     */
    public void deleteBucketReplicationConfiguration(DeleteBucketReplicationConfigurationRequest request) {
        String function = "deleteBucketReplicationConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucketReplicationConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储复制配置
     *
     * @param request {@link GetBucketReplicationConfigurationRequest}
     * @return {@link BucketReplicationConfiguration}
     */
    public BucketReplicationConfiguration getBucketReplicationConfiguration(GetBucketReplicationConfigurationRequest request) {
        String function = "getBucketReplicationConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketReplicationConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储复制配置
     *
     * @param request {@link SetBucketReplicationConfigurationRequest}
     */
    public void setBucketReplicationConfiguration(SetBucketReplicationConfigurationRequest request) {
        String function = "setBucketReplicationConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketReplicationConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
