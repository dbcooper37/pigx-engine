package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectLegalHoldRequest;
import com.amazonaws.services.s3.model.GetObjectLegalHoldResult;
import com.amazonaws.services.s3.model.SetObjectLegalHoldRequest;
import com.amazonaws.services.s3.model.SetObjectLegalHoldResult;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3ObjectLegalHoldService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectLegalHoldService.class);

    public S3ObjectLegalHoldService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取对象合法持有设置
     *
     * @param request {@link GetObjectLegalHoldRequest}
     * @return {@link GetObjectLegalHoldResult}
     */
    public GetObjectLegalHoldResult getObjectLegalHold(GetObjectLegalHoldRequest request) {
        String function = "getObjectLegalHold";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getObjectLegalHold(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置对象合法持有设置
     *
     * @param request {@link SetObjectLegalHoldRequest}
     * @return {@link SetObjectLegalHoldResult}
     */
    public SetObjectLegalHoldResult setObjectLegalHold(SetObjectLegalHoldRequest request) {
        String function = "setObjectLegalHold";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setObjectLegalHold(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
