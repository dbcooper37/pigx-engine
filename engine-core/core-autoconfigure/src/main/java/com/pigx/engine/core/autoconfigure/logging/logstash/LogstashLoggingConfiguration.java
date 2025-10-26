package com.pigx.engine.autoconfigure.logging.logstash;

import ch.qos.logback.classic.LoggerContext;
import com.pigx.engine.core.autoconfigure.logging.LoggingProperties;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.foundation.log.LogbackConfigurator;
import com.google.common.base.MoreObjects;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import net.logstash.logback.LogstashFormatter;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.apache.skywalking.apm.toolkit.log.logback.v1.x.TraceIdPatternLogbackLayout;
import org.apache.skywalking.apm.toolkit.log.logback.v1.x.logstash.TraceIdJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({LogstashLoggingProperties.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({LogstashFormatter.class, TraceIdPatternLogbackLayout.class})
@ConditionalOnLogstashEnabled
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/logging/logstash/LogstashLoggingConfiguration.class */
public class LogstashLoggingConfiguration {
    private static final Logger log = LoggerFactory.getLogger(LogstashLoggingConfiguration.class);

    @Value("${spring.application.name:-}")
    private String applicationName;

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Logstash Logging] Configure.");
    }

    @Bean
    public LogstashTcpSocketAppender logstashTcpSocketAppender(LoggingProperties loggingProperties, LogstashLoggingProperties logstashLoggingProperties) {
        LogbackConfigurator configurator = new LogbackConfigurator(LoggerFactory.getILoggerFactory());
        LoggerContext loggerContext = configurator.getContext();
        String destination = logstashLoggingProperties.getHost() + ":" + logstashLoggingProperties.getPort();
        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName("LOGSTASH");
        logstashTcpSocketAppender.addDestination(destination);
        logstashTcpSocketAppender.setKeepAliveDuration(logstashLoggingProperties.getKeepAliveDuration());
        logstashTcpSocketAppender.setReconnectionDelay(logstashLoggingProperties.getReconnectionDelay());
        logstashTcpSocketAppender.setWriteTimeout(logstashLoggingProperties.getWriteTimeout());
        logstashTcpSocketAppender.setContext(loggerContext);
        TraceIdJsonProvider traceIdJsonProvider = new TraceIdJsonProvider();
        traceIdJsonProvider.setContext(loggerContext);
        CustomFields customFields = new CustomFields();
        customFields.setService(this.applicationName);
        LogstashEncoder logstashEncoder = new LogstashEncoder();
        logstashEncoder.setCustomFields(Jackson2Utils.toJson(customFields));
        logstashEncoder.addProvider(traceIdJsonProvider);
        logstashTcpSocketAppender.setEncoder(logstashEncoder);
        Map<String, LogLevel> loggers = loggingProperties.getLoggers();
        Objects.requireNonNull(configurator);
        loggers.forEach(configurator::logger);
        configurator.root(loggingProperties.getLevel(), logstashTcpSocketAppender);
        configurator.start(logstashTcpSocketAppender);
        return logstashTcpSocketAppender;
    }

    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/logging/logstash/LogstashLoggingConfiguration$CustomFields.class */
    static class CustomFields {
        private String service;

        CustomFields() {
        }

        public String getService() {
            return this.service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("service", this.service).toString();
        }
    }
}
