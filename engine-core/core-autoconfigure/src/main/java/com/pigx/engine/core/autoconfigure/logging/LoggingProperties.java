package com.pigx.engine.autoconfigure.logging;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.google.common.base.MoreObjects;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_LOG)
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/logging/LoggingProperties.class */
public class LoggingProperties {
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
