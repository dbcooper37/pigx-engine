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
public class S3BucketIntelligentTieringConfigurationService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketIntelligentTieringConfigurationService.class);

    public S3BucketIntelligentTieringConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶智能分层配置
     *
     * @param request {@link DeleteBucketIntelligentTieringConfigurationRequest}
     * @return {@link DeleteBucketIntelligentTieringConfigurationResult}
     */
    public DeleteBucketIntelligentTieringConfigurationResult deleteBucketIntelligentTieringConfiguration(DeleteBucketIntelligentTieringConfigurationRequest request) {
        String function = "deleteBucketIntelligentTieringConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteBucketIntelligentTieringConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶智能分层配置
     *
     * @param request {@link GetBucketIntelligentTieringConfigurationRequest}
     * @return {@link GetBucketIntelligentTieringConfigurationResult}
     */
    public GetBucketIntelligentTieringConfigurationResult getBucketIntelligentTieringConfiguration(GetBucketIntelligentTieringConfigurationRequest request) {
        String function = "getBucketIntelligentTieringConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketIntelligentTieringConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶智能分层配置列表
     *
     * @param request {@link ListBucketIntelligentTieringConfigurationsRequest}
     * @return {@link ListBucketIntelligentTieringConfigurationsResult}
     */
    public ListBucketIntelligentTieringConfigurationsResult listBucketIntelligentTieringConfigurations(ListBucketIntelligentTieringConfigurationsRequest request) {
        String function = "listBucketIntelligentTieringConfigurations";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listBucketIntelligentTieringConfigurations(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶智能分层配置列表
     *
     * @param request {@link SetBucketIntelligentTieringConfigurationRequest}
     * @return {@link SetBucketIntelligentTieringConfigurationResult}
     */
    public SetBucketIntelligentTieringConfigurationResult setBucketIntelligentTieringConfiguration(SetBucketIntelligentTieringConfigurationRequest request) {
        String function = "setBucketIntelligentTieringConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setBucketIntelligentTieringConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
