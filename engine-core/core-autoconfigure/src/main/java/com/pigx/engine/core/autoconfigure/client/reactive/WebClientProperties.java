package com.pigx.engine.core.autoconfigure.client.reactive;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;


@ConfigurationProperties(prefix = BaseConstants.PROPERTY_REACTIVE_WEBCLIENT)
public class WebClientProperties {

    /**
     * 连接超时时间，默认值：10000 毫秒
     */
    private Duration connectTimeout = Duration.ofMillis(10000L);
    /**
     * 读取超时时间，，默认值：10秒
     */
    private Duration readTimeout = Duration.ofSeconds(5000L);
    /**
     * 写入超时时间，，默认值：10秒
     */
    private Duration writeTimeout = Duration.ofSeconds(5000L);

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("connectTimeout", connectTimeout)
                .add("readTimeout", readTimeout)
                .add("writeTimeout", writeTimeout)
                .toString();
    }
}
