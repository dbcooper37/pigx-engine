package com.pigx.engine.oss.rest.minio.definition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pigx.engine.oss.dialect.minio.converter.sse.RequestToServerSideEncryptionCustomerKeyConverter;
import io.minio.ObjectReadArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public abstract class ObjectReadRequest<B extends ObjectReadArgs.Builder<B, A>, A extends ObjectReadArgs> extends ObjectVersionRequest<B, A> {

    private final Converter<String, ServerSideEncryptionCustomerKey> toCustomerKey = new RequestToServerSideEncryptionCustomerKeyConverter();

    @Schema(name = "服务端加密自定义Key", description = "Minio 默认仅支持 256 位 AES")
    private String customerKey;

    @JsonIgnore
    private ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey;

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public ServerSideEncryptionCustomerKey getServerSideEncryptionCustomerKey() {
        serverSideEncryptionCustomerKey = toCustomerKey.convert(getCustomerKey());
        return serverSideEncryptionCustomerKey;
    }

    public void setServerSideEncryptionCustomerKey(ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        this.serverSideEncryptionCustomerKey = serverSideEncryptionCustomerKey;
    }

    @Override
    public void prepare(B builder) {

        if (ObjectUtils.isNotEmpty(getServerSideEncryptionCustomerKey())) {
            builder.ssec(getServerSideEncryptionCustomerKey());
        }

        super.prepare(builder);
    }
}
