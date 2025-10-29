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
public class S3BucketOwnershipControlsService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketOwnershipControlsService.class);

    public S3BucketOwnershipControlsService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶所有权控制
     *
     * @param request {@link DeleteBucketOwnershipControlsRequest}
     * @return {@link DeleteBucketOwnershipControlsResult}
     */
    public DeleteBucketOwnershipControlsResult deleteBucketOwnershipControls(DeleteBucketOwnershipControlsRequest request) {
        String function = "deleteBucketOwnershipControls";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteBucketOwnershipControls(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶所有权控制
     *
     * @param request {@link GetBucketOwnershipControlsRequest}
     * @return {@link GetBucketOwnershipControlsResult}
     */
    public GetBucketOwnershipControlsResult getBucketOwnershipControls(GetBucketOwnershipControlsRequest request) {
        String function = "getBucketOwnershipControls";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketOwnershipControls(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶所有权控制
     *
     * @param request {@link SetBucketOwnershipControlsRequest}
     * @return {@link SetBucketOwnershipControlsResult}
     */
    public SetBucketOwnershipControlsResult setBucketOwnershipControls(SetBucketOwnershipControlsRequest request) {
        String function = "setBucketOwnershipControls";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setBucketOwnershipControls(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
