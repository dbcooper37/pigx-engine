package com.pigx.engine.autoconfigure.logging.logstash;

import ch.qos.logback.core.util.Duration;
import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_LOG_LOGSTASH)
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/logging/logstash/LogstashLoggingProperties.class */
public class LogstashLoggingProperties {
    private String host = "127.0.0.1";
    private Integer port = 5044;
    private Duration keepAliveDuration = Duration.buildByMinutes(5.0d);
    private Duration reconnectionDelay = Duration.buildBySeconds(30.0d);
    private Duration writeTimeout = Duration.buildByMinutes(1.0d);
    private Boolean enabled = Boolean.FALSE;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Duration getKeepAliveDuration() {
        return this.keepAliveDuration;
    }

    public void setKeepAliveDuration(Duration keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }

    public Duration getReconnectionDelay() {
        return this.reconnectionDelay;
    }

    public void setReconnectionDelay(Duration reconnectionDelay) {
        this.reconnectionDelay = reconnectionDelay;
    }

    public Duration getWriteTimeout() {
        return this.writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
