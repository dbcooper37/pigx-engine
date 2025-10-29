package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import io.minio.messages.Initiator;
import org.springframework.core.convert.converter.Converter;


public class InitiatorToDomainConverter implements Converter<Initiator, OwnerDomain> {

    @Override
    public OwnerDomain convert(Initiator source) {

        OwnerDomain attribute = new OwnerDomain();
        attribute.setId(source.id());
        attribute.setDisplayName(source.displayName());
        return attribute;
    }
}
