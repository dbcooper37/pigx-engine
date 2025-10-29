package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.bucket.BucketDomain;
import io.minio.messages.Bucket;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;
import java.util.Optional;


public class BucketToDomainConverter implements Converter<Bucket, BucketDomain> {

    @Override
    public BucketDomain convert(Bucket source) {

        Optional<Bucket> optional = Optional.ofNullable(source);
        return optional.map(bucket -> {
            BucketDomain domain = new BucketDomain();
            domain.setBucketName(bucket.name());
            Optional.ofNullable(bucket.creationDate()).ifPresent(zonedDateTime ->
                    domain.setCreationDate(new Date(zonedDateTime.toInstant().toEpochMilli()))
            );
            return domain;
        }).orElse(null);
    }
}
