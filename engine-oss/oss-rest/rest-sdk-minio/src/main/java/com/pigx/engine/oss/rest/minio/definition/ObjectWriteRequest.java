package com.pigx.engine.oss.rest.minio.definition;

import com.pigx.engine.oss.dialect.minio.converter.retention.DomainToRetentionConverter;
import com.pigx.engine.oss.dialect.minio.converter.sse.RequestToServerSideEncryptionConverter;
import com.pigx.engine.oss.dialect.minio.domain.RetentionDomain;
import com.pigx.engine.oss.dialect.minio.domain.ServerSideEncryptionDomain;
import io.minio.ObjectWriteArgs;
import io.minio.ServerSideEncryption;
import io.minio.messages.Retention;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;


public abstract class ObjectWriteRequest<B extends ObjectWriteArgs.Builder<B, A>, A extends ObjectWriteArgs> extends ObjectRequest<B, A> {

    private final Converter<RetentionDomain, Retention> toRetention = new DomainToRetentionConverter();
    private final Converter<ServerSideEncryptionDomain, ServerSideEncryption> toServerSideEncryption = new RequestToServerSideEncryptionConverter();

    @Schema(name = "自定义 Header 信息")
    private Map<String, String> headers;

    @Schema(name = "用户信息")
    private Map<String, String> userMetadata;

    @Schema(name = "服务端加密")
    private ServerSideEncryptionDomain serverSideEncryption;

    @Schema(name = "标签")
    private Map<String, String> tags;

    @Schema(name = "保留配置")
    private RetentionDomain retention;

    @Schema(name = "合法持有")
    private Boolean legalHold;

    public Converter<RetentionDomain, Retention> getToRetention() {
        return toRetention;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public ServerSideEncryptionDomain getServerSideEncryption() {
        return serverSideEncryption;
    }

    public void setServerSideEncryption(ServerSideEncryptionDomain serverSideEncryption) {
        this.serverSideEncryption = serverSideEncryption;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public RetentionDomain getRetention() {
        return retention;
    }

    public void setRetention(RetentionDomain retention) {
        this.retention = retention;
    }

    public Boolean getLegalHold() {
        return legalHold;
    }

    public void setLegalHold(Boolean legalHold) {
        this.legalHold = legalHold;
    }

    @Override
    public void prepare(B builder) {
        if (MapUtils.isNotEmpty(getHeaders())) {
            builder.headers(getHeaders());
        }

        if (MapUtils.isNotEmpty(getUserMetadata())) {
            builder.headers(getUserMetadata());
        }

        if (MapUtils.isNotEmpty(getTags())) {
            builder.headers(getTags());
        }

        builder.tags(getTags());

        if (ObjectUtils.isNotEmpty(getServerSideEncryption())) {
            ServerSideEncryption encryption = toServerSideEncryption.convert(getServerSideEncryption());
            if (ObjectUtils.isNotEmpty(encryption)) {
                builder.sse(encryption);
            }
        }

        if (ObjectUtils.isNotEmpty(getRetention())) {
            builder.retention(toRetention.convert(getRetention()));
        }

        if (ObjectUtils.isNotEmpty(getLegalHold())) {
            builder.legalHold(getLegalHold());
        }

        super.prepare(builder);
    }
}
