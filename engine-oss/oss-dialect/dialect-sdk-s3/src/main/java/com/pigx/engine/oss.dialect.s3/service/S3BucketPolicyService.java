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
public class S3BucketPolicyService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketPolicyService.class);

    public S3BucketPolicyService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶访问策略
     *
     * @param request {@link DeleteBucketPolicyRequest}
     */
    public void deleteBucketPolicy(DeleteBucketPolicyRequest request) {
        String function = "deleteBucketPolicy";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucketPolicy(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储访问策略
     *
     * @param request {@link GetBucketPolicyRequest}
     * @return {@link BucketPolicy}
     */
    public BucketPolicy getBucketPolicy(GetBucketPolicyRequest request) {
        String function = "getBucketPolicy";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketPolicy(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储访问策略状态
     *
     * @param request {@link GetBucketPolicyStatusRequest}
     * @return {@link GetBucketPolicyStatusResult}
     */
    public GetBucketPolicyStatusResult getBucketPolicyStatus(GetBucketPolicyStatusRequest request) {
        String function = "getBucketPolicyStatus";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketPolicyStatus(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储访问策略
     *
     * @param request {@link SetBucketPolicyRequest}
     */
    public void setBucketOwnershipControls(SetBucketPolicyRequest request) {
        String function = "setBucketOwnershipControls";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketPolicy(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
