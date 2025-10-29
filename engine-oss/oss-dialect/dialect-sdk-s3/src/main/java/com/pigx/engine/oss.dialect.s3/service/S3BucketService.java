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
public class S3BucketService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketService.class);

    public S3BucketService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 返回指定存储桶中版本的摘要信息列表
     *
     * @param request {@link ListVersionsRequest}
     * @return {@link VersionListing}
     */
    public VersionListing listVersions(ListVersionsRequest request) {
        String function = "listVersions";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listVersions(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 此操作可用于确定存储桶是否存在以及您是否有权访问它。如果存储桶存在并且您有权访问，则此操作返回200 OK。
     *
     * @param request {@link HeadBucketRequest}
     * @return {@link HeadBucketResult}
     */
    public HeadBucketResult headBucket(HeadBucketRequest request) {
        String function = "headBucket";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.headBucket(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶位置
     *
     * @param request {@link GetBucketLocationRequest}
     * @return 存储桶位置 {@link String}
     */
    public String getBucketLocation(GetBucketLocationRequest request) {
        String function = "getBucketLocation";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketLocation(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
