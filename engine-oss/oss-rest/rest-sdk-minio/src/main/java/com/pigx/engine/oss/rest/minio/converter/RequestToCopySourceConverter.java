package com.pigx.engine.oss.rest.minio.converter;

import com.pigx.engine.oss.rest.minio.request.domain.CopySourceRequest;
import io.minio.CopySource;
import org.springframework.core.convert.converter.Converter;


public class RequestToCopySourceConverter implements Converter<CopySourceRequest, CopySource> {
    @Override
    public CopySource convert(CopySourceRequest source) {
        return null;
    }
}
