package com.pigx.engine.data.rdbms.autoconfigure.enhance;

import cn.hutool.v7.core.data.id.IdUtil;
import cn.hutool.v7.core.data.id.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;


@Component
public class HerodotusIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        // 采用雪花算法获取id,时间回拨会存在重复,这里用随机数来减少重复的概率
        final Snowflake snowflake = IdUtil.getSnowflake(1, (int) (Math.random() * 20 + 1));
        return snowflake.next();
    }
}
