package com.pigx.engine.data.rdbms.autoconfigure.enhance;

import cn.hutool.v7.core.data.id.IdUtil;
import cn.hutool.v7.core.data.id.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
/* loaded from: data-rdbms-spring-boot-starter-3.5.7.0.jar:cn/herodotus/engine/data/rdbms/autoconfigure/enhance/HerodotusIdentifierGenerator.class */
public class HerodotusIdentifierGenerator implements IdentifierGenerator {
    public Number nextId(Object entity) {
        Snowflake snowflake = IdUtil.getSnowflake(1L, (int) ((Math.random() * 20.0d) + 1.0d));
        return snowflake.next();
    }
}
