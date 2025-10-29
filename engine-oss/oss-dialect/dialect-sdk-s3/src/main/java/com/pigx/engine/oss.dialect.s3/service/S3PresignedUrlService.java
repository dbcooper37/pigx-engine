package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


public class S3PresignedUrlService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketAccessControlListService.class);

    public S3PresignedUrlService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取对象标记设置
     *
     * @param request {@link GeneratePresignedUrlRequest}
     * @return {@link URL}
     */
    public URL generatePresignedUrl(GeneratePresignedUrlRequest request) {
        String function = "generatePresignedUrl";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.generatePresignedUrl(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    public PresignedUrlDownloadResult download(PresignedUrlDownloadRequest request) {
        String function = "download";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.download(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    public PresignedUrlUploadResult upload(PresignedUrlUploadRequest request) {
        String function = "upload";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.upload(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
