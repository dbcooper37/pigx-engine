package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.GetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketVersioningConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketVersioningConfigurationService.class);

    public S3BucketVersioningConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取存储桶版本配置
     *
     * @param request {@link GetBucketVersioningConfigurationRequest}
     * @return {@link BucketVersioningConfiguration}
     */
    public BucketVersioningConfiguration getBucketVersioningConfiguration(GetBucketVersioningConfigurationRequest request) {
        String function = "getBucketVersioningConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketVersioningConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶版本配置
     *
     * @param request {@link SetBucketVersioningConfigurationRequest}
     */
    public void setBucketVersioningConfiguration(SetBucketVersioningConfigurationRequest request) {
        String function = "setBucketVersioningConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketVersioningConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
