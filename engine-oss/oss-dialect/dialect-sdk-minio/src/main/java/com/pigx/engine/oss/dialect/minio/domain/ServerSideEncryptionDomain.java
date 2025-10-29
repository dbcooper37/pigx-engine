package com.pigx.engine.oss.dialect.minio.domain;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.oss.dialect.minio.enums.ServerSideEncryptionEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;


public class ServerSideEncryptionDomain implements BaseEntity {

    @Schema(name = "服务端加密方式类型", description = "1:SSE_KMS, 2:SSE_S3, 3: 自定义")
    private ServerSideEncryptionEnums type;

    @Schema(name = "自定义服务端加密方式加密Key", description = "Minio 默认仅支持 256 位 AES")
    private String customerKey;

    @Schema(name = "KMS加密MasterKeyId", description = "可选参数，主要用于AWS_KMS加密算法")
    private String keyId;

    @Schema(name = "KMS加密context", description = "可选参数，主要用于AWS_KMS加密算法")
    private Map<String, String> context;

    public ServerSideEncryptionEnums getType() {
        return type;
    }

    public void setType(ServerSideEncryptionEnums type) {
        this.type = type;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }
}
