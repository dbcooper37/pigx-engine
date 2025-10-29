package com.pigx.engine.data.hibernate.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.hibernate.id.uuid.UuidValueGenerator;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.type.descriptor.java.UUIDJavaType;

import java.lang.reflect.Member;
import java.util.UUID;


public abstract class AbstractIdGeneratorType implements IdentifierGenerator {

    private final UuidValueGenerator generator;
    private final UUIDJavaType.ValueTransformer valueTransformer;

    public AbstractIdGeneratorType(Member member) {
        generator = StandardRandomStrategy.INSTANCE;

        final Class<?> propertyType = ReflectHelper.getPropertyType(member);
        this.valueTransformer = determineProperTransformer(propertyType);
    }

    private UUIDJavaType.ValueTransformer determineProperTransformer(Class<?> propertyType) {
        if (UUID.class.isAssignableFrom(propertyType)) {
            return UUIDJavaType.PassThroughTransformer.INSTANCE;
        }

        if (String.class.isAssignableFrom(propertyType)) {
            return UUIDJavaType.ToStringTransformer.INSTANCE;
        }

        if (byte[].class.isAssignableFrom(propertyType)) {
            return UUIDJavaType.ToBytesTransformer.INSTANCE;
        }

        throw new HibernateException("Unanticipated return type [" + propertyType.getName() + "] for UUID conversion");
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return valueTransformer.transform(generator.generateUuid(session));
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
