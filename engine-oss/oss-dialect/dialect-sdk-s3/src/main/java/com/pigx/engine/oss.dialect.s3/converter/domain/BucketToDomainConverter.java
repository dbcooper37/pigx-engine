package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.Bucket;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import com.pigx.engine.oss.specification.domain.bucket.BucketDomain;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;


public class BucketToDomainConverter implements Converter<Bucket, BucketDomain> {
    @Override
    public BucketDomain convert(Bucket source) {

        Optional<Bucket> optional = Optional.ofNullable(source);
        return optional.map(bucket -> {

            BucketDomain bucketDomain = new BucketDomain();

            Optional.ofNullable(bucket.getOwner()).ifPresent(o -> {
                OwnerDomain ownerAttributeDomain = new OwnerDomain();
                ownerAttributeDomain.setId(bucket.getOwner().getId());
                ownerAttributeDomain.setDisplayName(bucket.getOwner().getDisplayName());
                bucketDomain.setOwner(ownerAttributeDomain);
            });

            bucketDomain.setBucketName(bucket.getName());
            bucketDomain.setCreationDate(bucket.getCreationDate());

            return bucketDomain;
        }).orElse(null);
    }
}
