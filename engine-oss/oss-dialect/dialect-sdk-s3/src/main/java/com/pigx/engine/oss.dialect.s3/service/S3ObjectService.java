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
public class S3ObjectService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectService.class);

    public S3ObjectService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取对象详细信息
     *
     * @param request {@link GetObjectMetadataRequest }
     * @return {@link ObjectMetadata}
     */
    public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest request) {
        String function = "getObjectMetadata";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getObjectMetadata(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 上传对象
     *
     * @param request {@link PutObjectRequest }
     * @return {@link PutObjectResult}
     */
    public PutObjectResult putObject(PutObjectRequest request) {
        String function = "putObject";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.putObject(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 复制对象
     *
     * @param request {@link CopyObjectRequest}
     * @return {@link CopyObjectResult}
     */
    public CopyObjectResult copyObject(CopyObjectRequest request) {
        String function = "copyObject";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.copyObject(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link DeleteVersionRequest}
     */
    public void deleteVersion(DeleteVersionRequest request) {
        String function = "deleteObjects";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteVersion(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link RestoreObjectRequest}
     * @return {@link RestoreObjectResult}
     */
    public RestoreObjectResult restoreObject(RestoreObjectRequest request) {
        String function = "restoreObjectV2";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.restoreObjectV2(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link SelectObjectContentRequest}
     * @return {@link SelectObjectContentResult}
     */
    public SelectObjectContentResult selectObjectContent(SelectObjectContentRequest request) {
        String function = "selectObjectContent";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.selectObjectContent(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link WriteGetObjectResponseRequest}
     * @return {@link WriteGetObjectResponseResult}
     */
    public WriteGetObjectResponseResult writeGetObjectResponse(WriteGetObjectResponseRequest request) {
        String function = "writeGetObjectResponse";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.writeGetObjectResponse(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 列出下一批对象
     *
     * @param request {@link ListNextBatchOfObjectsRequest }
     * @return {@link ObjectListing}
     */
    public ObjectListing listNextBatchOfObjects(ListNextBatchOfObjectsRequest request) {
        String function = "listNextBatchOfObjects";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listNextBatchOfObjects(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }


}
