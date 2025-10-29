package com.pigx.engine.oss.rest.minio.converter;

import com.pigx.engine.oss.rest.minio.request.domain.ComposeSourceRequest;
import io.minio.ComposeSource;
import org.springframework.core.convert.converter.Converter;


public class RequestToComposeSourceConverter implements Converter<ComposeSourceRequest, ComposeSource> {
    @Override
    public ComposeSource convert(ComposeSourceRequest source) {
        return null;
    }
}
