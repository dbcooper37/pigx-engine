package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketTaggingConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketTaggingConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketTaggingConfigurationRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketTaggingConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketTaggingConfigurationService.class);

    public S3BucketTaggingConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶标记配置
     *
     * @param request {@link DeleteBucketTaggingConfigurationRequest}
     */
    public void deleteBucketTaggingConfiguration(DeleteBucketTaggingConfigurationRequest request) {
        String function = "deleteBucketTaggingConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucketTaggingConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储标记配置
     *
     * @param request {@link GetBucketTaggingConfigurationRequest}
     * @return {@link BucketTaggingConfiguration}
     */
    public BucketTaggingConfiguration getBucketTaggingConfiguration(GetBucketTaggingConfigurationRequest request) {
        String function = "getBucketTaggingConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketTaggingConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储标记配置
     *
     * @param request {@link SetBucketTaggingConfigurationRequest}
     */
    public void setBucketTaggingConfiguration(SetBucketTaggingConfigurationRequest request) {
        String function = "setBucketTaggingConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketTaggingConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
