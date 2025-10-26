package com.pigx.engine.data.hibernate.generator;

import cn.hutool.v7.core.data.id.IdUtil;
import cn.hutool.v7.core.data.id.Snowflake;
import java.lang.reflect.Member;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;
import org.hibernate.internal.util.ReflectHelper;

/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/generator/SnowflakeIdGeneratorType.class */
public class SnowflakeIdGeneratorType implements IdentifierGenerator {
    private final Snowflake snowflake = IdUtil.getSnowflake();
    private final Class<?> propertyType;

    public SnowflakeIdGeneratorType(SnowflakeIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        this.propertyType = ReflectHelper.getPropertyType(member);
    }

    public Object generate(SharedSessionContractImplementor session, Object object) {
        if (String.class.isAssignableFrom(this.propertyType)) {
            return this.snowflake.nextStr();
        }
        return this.snowflake.next();
    }

    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
