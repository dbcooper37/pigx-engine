package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import io.minio.messages.Owner;
import org.springframework.core.convert.converter.Converter;


public class OwnerToDomainConverter implements Converter<Owner, OwnerDomain> {

    @Override
    public OwnerDomain convert(Owner source) {

        OwnerDomain attribute = new OwnerDomain();
        attribute.setId(source.id());
        attribute.setDisplayName(source.displayName());
        return attribute;
    }
}
