package com.pigx.engine.data.hibernate.generator;

import cn.hutool.v7.core.data.id.IdUtil;
import cn.hutool.v7.core.data.id.Snowflake;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;
import org.hibernate.internal.util.ReflectHelper;

import java.lang.reflect.Member;

/**
 * 雪花主键生成器，使用 hutool 的雪花主键生成器
 *
 * @author lkhsh
 * @date 2023-07-14
 */
public class SnowflakeIdGeneratorType implements IdentifierGenerator {

    private final Snowflake snowflake;
    private final Class<?> propertyType;

    public SnowflakeIdGeneratorType(SnowflakeIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        // 工具获取的主键生成器是个单例，也就是同一个运行实例，理论上 dataCenter 和 workerId 不会重复
        snowflake = IdUtil.getSnowflake();
        // 初始化主键的类型
        propertyType = ReflectHelper.getPropertyType(member);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        if (String.class.isAssignableFrom(propertyType)) {
            return snowflake.nextStr();
        }
        return snowflake.next();
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
