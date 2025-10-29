package com.pigx.engine.oss.dialect.minio.service;

import com.pigx.engine.oss.dialect.core.exception.OssConnectException;
import com.pigx.engine.oss.dialect.core.exception.OssIOException;
import com.pigx.engine.oss.dialect.core.exception.OssInvalidKeyException;
import com.pigx.engine.oss.dialect.core.exception.OssNoSuchAlgorithmException;
import com.pigx.engine.oss.dialect.minio.definition.pool.MinioAdminClientObjectPool;
import com.pigx.engine.oss.dialect.minio.definition.service.BaseMinioAdminService;
import com.pigx.engine.oss.dialect.minio.domain.policy.PolicyDomain;
import io.minio.admin.MinioAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@Service
public class MinioAdminPolicyService extends BaseMinioAdminService {

    private static final Logger log = LoggerFactory.getLogger(MinioAdminPolicyService.class);

    public MinioAdminPolicyService(MinioAdminClientObjectPool minioAdminClientObjectPool) {
        super(minioAdminClientObjectPool);
    }

    /**
     * 获取屏蔽策略列表
     *
     * @return 屏蔽策略列表
     */
    public Map<String, String> listCannedPolicies() {
        String function = "listCannedPolicies";

        MinioAdminClient minioAdminClient = getClient();

        try {
            return minioAdminClient.listCannedPolicies();
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

    /**
     * 创建屏蔽策略
     *
     * @param name   策略名称
     * @param policy 策略 {@link PolicyDomain}
     */
    public void addCannedPolicy(String name, String policy) {
        String function = "addCannedPolicy";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.addCannedPolicy(name, policy);
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

    /**
     * 移除屏蔽策略
     *
     * @param name 策略名称
     */
    public void removeCannedPolicy(String name) {
        String function = "removeCannedPolicy";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.removeCannedPolicy(name);
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

    /**
     * 设置屏蔽策略
     *
     * @param userOrGroupName 用户名或组名
     * @param isGroup         是否是组
     * @param policyName      策略名称
     */
    public void setPolicy(String userOrGroupName, boolean isGroup, String policyName) {
        String function = "setPolicy";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.setPolicy(userOrGroupName, isGroup, policyName);
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
