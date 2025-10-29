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
public class S3PublicAccessBlockService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3PublicAccessBlockService.class);

    public S3PublicAccessBlockService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除公共访问块
     *
     * @param request {@link DeletePublicAccessBlockRequest}
     * @return {@link DeletePublicAccessBlockResult}
     */
    public DeletePublicAccessBlockResult deletePublicAccessBlock(DeletePublicAccessBlockRequest request) {
        String function = "deletePublicAccessBlock";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deletePublicAccessBlock(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取公共访问块
     *
     * @param request {@link GetPublicAccessBlockRequest}
     * @return {@link GetPublicAccessBlockResult}
     */
    public GetPublicAccessBlockResult getPublicAccessBlock(GetPublicAccessBlockRequest request) {
        String function = "getPublicAccessBlock";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getPublicAccessBlock(request);
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
     * @param request {@link SetPublicAccessBlockRequest}
     * @return {@link SetPublicAccessBlockResult}
     */
    public SetPublicAccessBlockResult setPublicAccessBlock(SetPublicAccessBlockRequest request) {
        String function = "setPublicAccessBlock";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.setPublicAccessBlock(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
