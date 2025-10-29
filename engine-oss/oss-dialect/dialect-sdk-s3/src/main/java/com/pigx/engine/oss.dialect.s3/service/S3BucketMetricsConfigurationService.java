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
public class S3BucketMetricsConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketMetricsConfigurationService.class);

    public S3BucketMetricsConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶生命度量配置
     *
     * @param request {@link DeleteBucketMetricsConfigurationRequest}
     * @return {@link DeleteBucketMetricsConfigurationResult}
     */
    public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(DeleteBucketMetricsConfigurationRequest request) {
        String function = "deleteBucketMetricsConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteBucketMetricsConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶度量配置
     *
     * @param request {@link GetBucketMetricsConfigurationRequest}
     * @return {@link GetBucketMetricsConfigurationResult}
     */
    public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(GetBucketMetricsConfigurationRequest request) {
        String function = "getBucketMetricsConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketMetricsConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶度量配置列表
     *
     * @param request {@link ListBucketMetricsConfigurationsRequest}
     * @return {@link ListBucketMetricsConfigurationsResult}
     */
    public ListBucketMetricsConfigurationsResult listBucketMetricsConfigurations(ListBucketMetricsConfigurationsRequest request) {
        String function = "listBucketMetricsConfigurations";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listBucketMetricsConfigurations(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶度量配置列表
     *
     * @param request {@link SetBucketLoggingConfigurationRequest}
     * @return {@link SetBucketMetricsConfigurationResult}
     */
    public SetBucketMetricsConfigurationResult setBucketLoggingConfiguration(SetBucketMetricsConfigurationRequest request) {
        String function = "setBucketLoggingConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setBucketMetricsConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
