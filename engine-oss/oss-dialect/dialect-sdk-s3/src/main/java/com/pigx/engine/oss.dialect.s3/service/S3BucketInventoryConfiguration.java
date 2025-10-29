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
public class S3BucketInventoryConfiguration extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketInventoryConfiguration.class);

    public S3BucketInventoryConfiguration(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶库存配置
     *
     * @param request {@link DeleteBucketInventoryConfigurationRequest}
     * @return {@link DeleteBucketInventoryConfigurationResult}
     */
    public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(DeleteBucketInventoryConfigurationRequest request) {
        String function = "deleteBucketInventoryConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteBucketInventoryConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶库存配置
     *
     * @param request {@link GetBucketInventoryConfigurationRequest}
     * @return {@link GetBucketInventoryConfigurationResult}
     */
    public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(GetBucketInventoryConfigurationRequest request) {
        String function = "getBucketInventoryConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketInventoryConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶库存配置列表
     *
     * @param request {@link ListBucketInventoryConfigurationsRequest}
     * @return {@link ListBucketInventoryConfigurationsResult}
     */
    public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(ListBucketInventoryConfigurationsRequest request) {
        String function = "listBucketInventoryConfigurations";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listBucketInventoryConfigurations(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶库存配置列表
     *
     * @param request {@link SetBucketInventoryConfigurationRequest}
     * @return {@link SetBucketInventoryConfigurationResult}
     */
    public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(SetBucketInventoryConfigurationRequest request) {
        String function = "setBucketInventoryConfiguration";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setBucketInventoryConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
