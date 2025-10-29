package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.Owner;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import org.springframework.core.convert.converter.Converter;


public class OwnerToDomainConverter implements Converter<Owner, OwnerDomain> {

    @Override
    public OwnerDomain convert(Owner source) {
        OwnerDomain attribute = new OwnerDomain();
        attribute.setId(source.getId());
        attribute.setDisplayName(source.getDisplayName());
        return attribute;
    }
}
