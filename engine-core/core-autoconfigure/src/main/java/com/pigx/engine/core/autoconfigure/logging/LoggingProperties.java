package com.pigx.engine.core.autoconfigure.logging;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_LOG)
public class LoggingProperties {

    /**
     * 日志级别，默认为INFO
     */
    private LogLevel level = LogLevel.INFO;

    /**
     * 日志输出内容配置
     */
    private Map<String, LogLevel> loggers = new HashMap<>();

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public Map<String, LogLevel> getLoggers() {
        return loggers;
    }

    public void setLoggers(Map<String, LogLevel> loggers) {
        this.loggers = loggers;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("level", level)
                .toString();
    }
}
