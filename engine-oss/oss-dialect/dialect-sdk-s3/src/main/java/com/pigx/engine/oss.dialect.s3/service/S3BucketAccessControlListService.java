package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GetBucketAclRequest;
import com.amazonaws.services.s3.model.SetBucketAclRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3BucketAccessControlListService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketAccessControlListService.class);

    public S3BucketAccessControlListService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取存储桶访问控制列表
     *
     * @param request {@link GetBucketAclRequest}
     * @return {@link AccessControlList}
     */
    public AccessControlList getBucketAccessControlList(GetBucketAclRequest request) {
        String function = "getBucketAccessControlList";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketAcl(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储桶访问控制列表
     *
     * @param request {@link SetBucketAclRequest}
     */
    public void setBucketAccessControlList(SetBucketAclRequest request) {
        String function = "setBucketAccessControlList";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketAcl(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
