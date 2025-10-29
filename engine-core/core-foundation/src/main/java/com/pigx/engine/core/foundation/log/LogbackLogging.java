package com.pigx.engine.core.foundation.log;

import com.google.common.base.MoreObjects;
import org.springframework.boot.logging.LogLevel;

import java.util.HashMap;
import java.util.Map;


public class LogbackLogging {

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
