package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetS3AccountOwnerRequest;
import com.amazonaws.services.s3.model.Owner;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class S3AccountOwnerService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3AccountOwnerService.class);

    public S3AccountOwnerService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取请求的已验证发件人正在使用的Amazon Web Services帐户的当前所有者。
     *
     * @param request {@link GetS3AccountOwnerRequest}
     * @return {@link Owner}
     */
    public Owner getS3AccountOwner(GetS3AccountOwnerRequest request) {
        String function = "getS3AccountOwner";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getS3AccountOwner(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
