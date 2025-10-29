package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketAccelerateConfiguration;
import com.amazonaws.services.s3.model.GetBucketAccelerateConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketAccelerateConfigurationRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketAccelerateConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketAccelerateConfigurationService.class);

    public S3BucketAccelerateConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取存储桶加速配置
     *
     * @param request {@link GetBucketAccelerateConfigurationRequest}
     * @return {@link BucketAccelerateConfiguration}
     */
    public BucketAccelerateConfiguration getBucketAccelerateConfiguration(GetBucketAccelerateConfigurationRequest request) {
        String function = "getBucketAccelerateConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketAccelerateConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶加速配置
     *
     * @param request {@link SetBucketAccelerateConfigurationRequest}
     */
    public void setBucketAccelerateConfiguration(SetBucketAccelerateConfigurationRequest request) {
        String function = "setBucketAccelerateConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketAccelerateConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
