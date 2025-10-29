package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class S3ObjectTaggingService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectTaggingService.class);

    public S3ObjectTaggingService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除对象标记
     *
     * @param request {@link DeleteObjectTaggingRequest}
     * @return {@link DeleteObjectTaggingResult}
     */
    public DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest request) {
        String function = "deleteObjectTagging";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteObjectTagging(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取对象标记设置
     *
     * @param request {@link GetObjectTaggingRequest}
     * @return {@link GetObjectTaggingResult}
     */
    public GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest request) {
        String function = "getObjectTagging";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getObjectTagging(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置对象标记设置
     *
     * @param request {@link SetObjectTaggingRequest}
     * @return {@link SetObjectTaggingResult}
     */
    public SetObjectTaggingResult setObjectTagging(SetObjectTaggingRequest request) {
        String function = "setObjectTagging";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setObjectTagging(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
