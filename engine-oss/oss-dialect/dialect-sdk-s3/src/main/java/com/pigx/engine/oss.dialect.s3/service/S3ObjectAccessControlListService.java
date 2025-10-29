package com.pigx.engine.oss.dialect.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GetObjectAclRequest;
import com.amazonaws.services.s3.model.SetObjectAclRequest;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class S3ObjectAccessControlListService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectAccessControlListService.class);

    public S3ObjectAccessControlListService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取对象访问控制列表
     *
     * @param request {@link GetObjectAclRequest}
     * @return {@link AccessControlList}
     */
    public AccessControlList getObjectAccessControlList(GetObjectAclRequest request) {
        String function = "getObjectAccessControlList";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getObjectAcl(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置对象访问控制列表
     *
     * @param request {@link SetObjectAclRequest}
     */
    public void setObjectAccessControlList(SetObjectAclRequest request) {
        String function = "setObjectAccessControlList";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setObjectAcl(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
