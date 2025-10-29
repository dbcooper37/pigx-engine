package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pigx.engine.oss.specification.domain.object.ObjectMetadataDomain;
import org.springframework.core.convert.converter.Converter;


public class ObjectMetadataToDomainConverter implements Converter<ObjectMetadata, ObjectMetadataDomain> {
    @Override
    public ObjectMetadataDomain convert(ObjectMetadata source) {

        ObjectMetadataDomain domain = new ObjectMetadataDomain();
        domain.setUserMetadata(source.getUserMetadata());
        return domain;
    }
}
