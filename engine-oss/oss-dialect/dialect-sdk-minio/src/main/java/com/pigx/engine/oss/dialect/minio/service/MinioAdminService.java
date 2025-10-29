package com.pigx.engine.oss.dialect.minio.service;

import com.pigx.engine.oss.dialect.core.exception.OssConnectException;
import com.pigx.engine.oss.dialect.core.exception.OssIOException;
import com.pigx.engine.oss.dialect.core.exception.OssInvalidKeyException;
import com.pigx.engine.oss.dialect.core.exception.OssNoSuchAlgorithmException;
import com.pigx.engine.oss.dialect.minio.definition.pool.MinioAdminClientObjectPool;
import com.pigx.engine.oss.dialect.minio.definition.service.BaseMinioAdminService;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.messages.DataUsageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Service
public class MinioAdminService extends BaseMinioAdminService {

    private static final Logger log = LoggerFactory.getLogger(MinioAdminService.class);

    public MinioAdminService(MinioAdminClientObjectPool minioAdminClientObjectPool) {
        super(minioAdminClientObjectPool);
    }

    /**
     * 获取服务器/群集数据使用情况信息
     *
     * @return {@link DataUsageInfo}
     */
    public DataUsageInfo getDataUsageInfo() {
        String function = "getDataUsageInfo";

        MinioAdminClient minioAdminClient = getClient();

        try {
            return minioAdminClient.getDataUsageInfo();
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } finally {
            close(minioAdminClient);
        }
    }


}
