package com.pigx.engine.core.autoconfigure.crypto;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_CRYPTO)
public class CryptoProperties {

    /**
     * 加密算法策略，默认：国密算法
     */
    private CryptoStrategy strategy = CryptoStrategy.SM;

    public CryptoStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(CryptoStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("strategy", strategy)
                .toString();
    }
}
