package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRetentionRequest;
import com.amazonaws.services.s3.model.GetObjectRetentionResult;
import com.amazonaws.services.s3.model.SetObjectRetentionRequest;
import com.amazonaws.services.s3.model.SetObjectRetentionResult;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3ObjectRetentionService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectRetentionService.class);

    public S3ObjectRetentionService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取对象保留设置
     *
     * @param request {@link GetObjectRetentionRequest}
     * @return {@link GetObjectRetentionResult}
     */
    public GetObjectRetentionResult getObjectRetention(GetObjectRetentionRequest request) {
        String function = "getObjectRetention";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getObjectRetention(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置对象保留设置
     *
     * @param request {@link SetObjectRetentionRequest}
     * @return {@link SetObjectRetentionResult}
     */
    public SetObjectRetentionResult setObjectRetention(SetObjectRetentionRequest request) {
        String function = "setObjectRetention";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setObjectRetention(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
