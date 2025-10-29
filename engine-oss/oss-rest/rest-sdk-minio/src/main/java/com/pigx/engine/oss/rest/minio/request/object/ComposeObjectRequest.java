package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.oss.rest.minio.converter.RequestToComposeSourceConverter;
import com.pigx.engine.oss.rest.minio.definition.ObjectWriteRequest;
import com.pigx.engine.oss.rest.minio.request.domain.ComposeSourceRequest;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.LinkedList;
import java.util.List;


public class ComposeObjectRequest extends ObjectWriteRequest<ComposeObjectArgs.Builder, ComposeObjectArgs> {

    private final Converter<ComposeSourceRequest, ComposeSource> requestTo = new RequestToComposeSourceConverter();
    private List<ComposeSourceRequest> sources;

    public ComposeObjectRequest(CopyObjectRequest request) {
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
        this.sources = new LinkedList<>();
        this.sources.add(new ComposeSourceRequest(request.getSource()));
    }

    public List<ComposeSourceRequest> getSources() {
        return sources;
    }

    public void setSources(List<ComposeSourceRequest> sources) {
        this.sources = sources;
    }

    @Override
    public void prepare(ComposeObjectArgs.Builder builder) {
        if (CollectionUtils.isNotEmpty(getSources())) {
            List<ComposeSource> composeSources = getSources().stream().map(requestTo::convert).toList();
            builder.sources(composeSources);
        }
        super.prepare(builder);
    }

    @Override
    public ComposeObjectArgs.Builder getBuilder() {
        return ComposeObjectArgs.builder();
    }
}
