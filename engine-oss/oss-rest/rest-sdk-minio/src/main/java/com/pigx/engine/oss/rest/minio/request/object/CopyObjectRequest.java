package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.converter.RequestToCopySourceConverter;
import com.pigx.engine.oss.rest.minio.definition.ObjectWriteRequest;
import com.pigx.engine.oss.rest.minio.request.domain.CopySourceRequest;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.Directive;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;


public class CopyObjectRequest extends ObjectWriteRequest<CopyObjectArgs.Builder, CopyObjectArgs> {

    private final Converter<CopySourceRequest, CopySource> requestTo = new RequestToCopySourceConverter();

    @NotNull(message = "source 对象不能为空")
    private CopySourceRequest source;
    private String metadataDirective;
    private String taggingDirective;

    public CopyObjectRequest(ComposeObjectRequest request) {
        this.setExtraHeaders(request.getExtraHeaders());
        this.setExtraQueryParams(request.getExtraQueryParams());
        this.setBucketName(request.getBucketName());
        this.setRegion(request.getRegion());
        this.setObjectName(request.getObjectName());
        this.setHeaders(request.getHeaders());
        this.setUserMetadata(request.getUserMetadata());
        this.setServerSideEncryption(request.getServerSideEncryption());
        this.setTags(request.getTags());
        this.setRetention(request.getRetention());
        this.setLegalHold(request.getLegalHold());
        this.source = new CopySourceRequest(request.getSources().get(0));
    }

    public CopySourceRequest getSource() {
        return source;
    }

    public void setSource(CopySourceRequest source) {
        this.source = source;
    }

    public String getMetadataDirective() {
        return metadataDirective;
    }

    public void setMetadataDirective(String metadataDirective) {
        this.metadataDirective = metadataDirective;
    }

    public String getTaggingDirective() {
        return taggingDirective;
    }

    public void setTaggingDirective(String taggingDirective) {
        this.taggingDirective = taggingDirective;
    }

    @Override
    public void prepare(CopyObjectArgs.Builder builder) {
        builder.source(requestTo.convert(getSource()));
        if (StringUtils.isNotBlank(getMetadataDirective())) {
            builder.metadataDirective(Directive.valueOf(getMetadataDirective()));
        }
        if (StringUtils.isNotBlank(getTaggingDirective())) {
            builder.taggingDirective(Directive.valueOf(getTaggingDirective()));
        }
        super.prepare(builder);
    }

    @Override
    public CopyObjectArgs.Builder getBuilder() {
        return CopyObjectArgs.builder();
    }
}
