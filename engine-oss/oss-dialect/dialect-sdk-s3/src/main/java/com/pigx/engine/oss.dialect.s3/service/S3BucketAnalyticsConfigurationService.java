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
public class S3BucketAnalyticsConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketAnalyticsConfigurationService.class);

    public S3BucketAnalyticsConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶分析配置
     *
     * @param request {@link CopyObjectRequest}
     * @return {@link CopyObjectResult}
     */
    public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(DeleteBucketAnalyticsConfigurationRequest request) {
        String function = "deleteBucketAnalyticsConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteBucketAnalyticsConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }


    /**
     * 获取存储桶访问分析配置
     *
     * @param request {@link GetBucketAnalyticsConfigurationRequest}
     * @return {@link GetBucketAnalyticsConfigurationResult}
     */
    public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(GetBucketAnalyticsConfigurationRequest request) {
        String function = "getBucketAnalyticsConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketAnalyticsConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶访问分析配置列表
     *
     * @param request {@link ListBucketAnalyticsConfigurationsRequest}
     * @return {@link ListBucketAnalyticsConfigurationsResult}
     */
    public ListBucketAnalyticsConfigurationsResult listBucketAnalyticsConfigurations(ListBucketAnalyticsConfigurationsRequest request) {
        String function = "listBucketAnalyticsConfigurations";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listBucketAnalyticsConfigurations(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶访问分析配置列表
     *
     * @param request {@link SetBucketAnalyticsConfigurationRequest}
     * @return {@link SetBucketAnalyticsConfigurationResult}
     */
    public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(SetBucketAnalyticsConfigurationRequest request) {
        String function = "setBucketAnalyticsConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setBucketAnalyticsConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
