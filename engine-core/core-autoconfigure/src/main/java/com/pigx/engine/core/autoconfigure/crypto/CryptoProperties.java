package com.pigx.engine.autoconfigure.crypto;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_CRYPTO)
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/crypto/CryptoProperties.class */
public class CryptoProperties {
    private CryptoStrategy strategy = CryptoStrategy.SM;

    public CryptoStrategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(CryptoStrategy strategy) {
        this.strategy = strategy;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("strategy", this.strategy).toString();
    }
}
