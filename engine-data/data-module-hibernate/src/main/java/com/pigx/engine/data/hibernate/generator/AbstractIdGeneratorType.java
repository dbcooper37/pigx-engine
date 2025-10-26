package com.pigx.engine.data.hibernate.generator;

import java.lang.reflect.Member;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.hibernate.id.uuid.UuidValueGenerator;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.type.descriptor.java.UUIDJavaType;

/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/generator/AbstractIdGeneratorType.class */
public abstract class AbstractIdGeneratorType implements IdentifierGenerator {
    private final UuidValueGenerator generator = StandardRandomStrategy.INSTANCE;
    private final UUIDJavaType.ValueTransformer valueTransformer;

    public AbstractIdGeneratorType(Member member) {
        Class<?> propertyType = ReflectHelper.getPropertyType(member);
        this.valueTransformer = determineProperTransformer(propertyType);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.hibernate.HibernateException */
    private UUIDJavaType.ValueTransformer determineProperTransformer(Class<?> propertyType) throws HibernateException {
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

    public Object generate(SharedSessionContractImplementor session, Object object) {
        return this.valueTransformer.transform(this.generator.generateUuid(session));
    }

    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
