package com.pigx.engine.core.foundation.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.spi.ScanException;
import ch.qos.logback.core.util.OptionHelper;
import org.slf4j.ILoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.util.Assert;


public class LogbackConfigurator {

    private final LoggerContext context;

    public LogbackConfigurator(LoggerContext context) {
        Assert.notNull(context, "Context must not be null");
        this.context = context;
    }

    public LogbackConfigurator(ILoggerFactory loggerFactory) {
        Assert.notNull(loggerFactory, "loggerFactory must not be null");
        this.context = (LoggerContext) loggerFactory;
    }

    public LoggerContext getContext() {
        return this.context;
    }

    public Object getConfigurationLock() {
        return this.context.getConfigurationLock();
    }


    public void appender(String name, Appender<?> appender) {
        appender.setName(name);
        start(appender);
    }

    public void logger(String name, LogLevel logLevel) {
        logger(name, Level.toLevel(logLevel.name()));
    }

    public void logger(String name, Level level) {
        logger(name, level, true);
    }

    public void logger(String name, Level level, boolean additive) {
        logger(name, level, additive, null);
    }

    public void logger(String name, Level level, boolean additive, Appender<ILoggingEvent> appender) {
        Logger logger = this.context.getLogger(name);
        if (level != null) {
            logger.setLevel(level);
        }
        logger.setAdditive(additive);
        if (appender != null) {
            logger.addAppender(appender);
        }
    }

    @SafeVarargs
    public final void root(LogLevel logLevel, Appender<ILoggingEvent>... appenders) {
        root(Level.toLevel(logLevel.name()), appenders);
    }

    @SafeVarargs
    public final void root(Level level, Appender<ILoggingEvent>... appenders) {
        Logger logger = this.context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        if (level != null) {
            logger.setLevel(level);
        }
        for (Appender<ILoggingEvent> appender : appenders) {
            logger.addAppender(appender);
        }
    }

    public void start(LifeCycle lifeCycle) {
        if (lifeCycle instanceof ContextAware contextAware) {
            contextAware.setContext(this.context);
        }
        lifeCycle.start();
    }

    public String resolve(String value) {
        try {
            return OptionHelper.substVars(value, this.context);
        } catch (ScanException ex) {
            throw new RuntimeException(ex);
        }
    }
}
