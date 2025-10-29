package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketLifecycleConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketLifecycleConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketLifecycleConfigurationRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketLifecycleConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketLifecycleConfigurationService.class);

    public S3BucketLifecycleConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶生命周期配置
     *
     * @param request {@link DeleteBucketLifecycleConfigurationRequest}
     */
    public void deleteBucketLifecycleConfiguration(DeleteBucketLifecycleConfigurationRequest request) {
        String function = "deleteBucketLifecycleConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucketLifecycleConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶生命周期配置
     *
     * @param request {@link GetBucketLifecycleConfigurationRequest}
     * @return {@link BucketLifecycleConfiguration}
     */
    public BucketLifecycleConfiguration getBucketLifecycleConfiguration(GetBucketLifecycleConfigurationRequest request) {
        String function = "getBucketLifecycleConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketLifecycleConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶生命周期配置
     *
     * @param request {@link SetBucketLifecycleConfigurationRequest}
     */
    public void setBucketLifecycleConfiguration(SetBucketLifecycleConfigurationRequest request) {
        String function = "setBucketLifecycleConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketLifecycleConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
