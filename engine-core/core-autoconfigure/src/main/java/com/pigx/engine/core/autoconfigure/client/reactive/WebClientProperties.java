package com.pigx.engine.autoconfigure.client.reactive;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.google.common.base.MoreObjects;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_REACTIVE_WEBCLIENT)
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/reactive/WebClientProperties.class */
public class WebClientProperties {
    private Duration connectTimeout = Duration.ofMillis(10000);
    private Duration readTimeout = Duration.ofSeconds(5000);
    private Duration writeTimeout = Duration.ofSeconds(5000);

    public Duration getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Duration getWriteTimeout() {
        return this.writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("connectTimeout", this.connectTimeout).add("readTimeout", this.readTimeout).add("writeTimeout", this.writeTimeout).toString();
    }
}
