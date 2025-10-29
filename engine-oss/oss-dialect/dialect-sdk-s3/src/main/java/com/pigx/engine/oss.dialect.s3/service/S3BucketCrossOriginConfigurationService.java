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
public class S3BucketCrossOriginConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketCrossOriginConfigurationService.class);

    public S3BucketCrossOriginConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶跨域配置
     *
     * @param request {@link DeleteBucketLifecycleConfigurationRequest}
     */
    public void deleteBucketCrossOriginConfiguration(DeleteBucketCrossOriginConfigurationRequest request) {
        String function = "deleteBucketCrossOriginConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucketCrossOriginConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶跨域配置
     *
     * @param request {@link GetBucketCrossOriginConfigurationRequest}
     * @return {@link BucketCrossOriginConfiguration}
     */
    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(GetBucketCrossOriginConfigurationRequest request) {
        String function = "getBucketCrossOriginConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketCrossOriginConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶跨域配置
     *
     * @param request {@link SetBucketCrossOriginConfigurationRequest}
     * @return {@link SetBucketAnalyticsConfigurationResult}
     */
    public void setBucketCrossOriginConfiguration(SetBucketCrossOriginConfigurationRequest request) {
        String function = "setBucketCrossOriginConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketCrossOriginConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
