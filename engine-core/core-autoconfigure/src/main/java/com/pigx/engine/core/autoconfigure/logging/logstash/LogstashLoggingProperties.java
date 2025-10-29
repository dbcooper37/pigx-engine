package com.pigx.engine.core.autoconfigure.logging.logstash;

import ch.qos.logback.core.util.Duration;
import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = BaseConstants.PROPERTY_LOG_LOGSTASH)
public class LogstashLoggingProperties {

    /**
     * 日志中心的 logstash 服务器主机。
     */
    private String host = "127.0.0.1";
    /**
     * 日志中心的 logstash 服务器端口。
     */
    private Integer port = 5044;
    /**
     * 保持活动持续时间，默认5分钟，单位：分钟
     */
    private Duration keepAliveDuration = Duration.buildByMinutes(5);

    /**
     * 尝试连接到目标间隔时间，默认30秒， 单位：秒
     */
    private Duration reconnectionDelay = Duration.buildBySeconds(30);
    /**
     * 日志写入超时时间，默认1分钟，单位：分钟
     */
    private Duration writeTimeout = Duration.buildByMinutes(1);
    /**
     * 是否开启 Logstash 日志
     */
    private Boolean enabled = Boolean.FALSE;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Duration getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(Duration keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }

    public Duration getReconnectionDelay() {
        return reconnectionDelay;
    }

    public void setReconnectionDelay(Duration reconnectionDelay) {
        this.reconnectionDelay = reconnectionDelay;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
