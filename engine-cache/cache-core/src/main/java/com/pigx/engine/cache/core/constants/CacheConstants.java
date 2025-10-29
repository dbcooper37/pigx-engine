package com.pigx.engine.cache.core.constants;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface CacheConstants extends BaseConstants {

    String PROPERTY_REDIS_REDISSON = PROPERTY_SPRING_DATA + ".redisson";
    String ITEM_REDISSON_ENABLED = PROPERTY_REDIS_REDISSON + PROPERTY_ENABLED;
}
