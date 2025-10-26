package com.pigx.engine.core.foundation.log;

import com.google.common.base.MoreObjects;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.logging.LogLevel;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/log/LogbackLogging.class */
public class LogbackLogging {
    private LogLevel level = LogLevel.INFO;
    private Map<String, LogLevel> loggers = new HashMap();

    public LogLevel getLevel() {
        return this.level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public Map<String, LogLevel> getLoggers() {
        return this.loggers;
    }

    public void setLoggers(Map<String, LogLevel> loggers) {
        this.loggers = loggers;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("level", this.level).toString();
    }
}
