package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.GetBucketLoggingConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketLoggingConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketLoggingConfigurationService.class);

    public S3BucketLoggingConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取存储桶日志配置
     *
     * @param request {@link GetBucketLoggingConfigurationRequest}
     * @return {@link BucketLoggingConfiguration}
     */
    public BucketLoggingConfiguration getBucketLoggingConfiguration(GetBucketLoggingConfigurationRequest request) {
        String function = "getBucketLoggingConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketLoggingConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶日志配置
     *
     * @param request {@link SetBucketLoggingConfigurationRequest}
     */
    public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest request) {
        String function = "setBucketLoggingConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketLoggingConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
