package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketWebsiteConfigurationRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketWebsiteConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketWebsiteConfigurationService.class);

    public S3BucketWebsiteConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶网页配置
     *
     * @param request {@link DeleteBucketWebsiteConfigurationRequest}
     */
    public void deleteBucketWebsiteConfiguration(DeleteBucketWebsiteConfigurationRequest request) {
        String function = "deleteBucketWebsiteConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucketWebsiteConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶网页配置
     *
     * @param request {@link GetBucketWebsiteConfigurationRequest}
     * @return {@link BucketWebsiteConfiguration}
     */
    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(GetBucketWebsiteConfigurationRequest request) {
        String function = "getBucketWebsiteConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketWebsiteConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶网页配置
     *
     * @param request {@link SetBucketWebsiteConfigurationRequest}
     */
    public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest request) {
        String function = "setBucketWebsiteConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketWebsiteConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
