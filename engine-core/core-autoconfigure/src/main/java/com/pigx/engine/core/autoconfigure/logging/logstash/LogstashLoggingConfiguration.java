package com.pigx.engine.core.autoconfigure.logging.logstash;

import ch.qos.logback.classic.LoggerContext;
import com.google.common.base.MoreObjects;
import com.pigx.engine.core.autoconfigure.logging.LoggingProperties;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.foundation.log.LogbackConfigurator;
import jakarta.annotation.PostConstruct;
import net.logstash.logback.LogstashFormatter;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.apache.skywalking.apm.toolkit.log.logback.v1.x.TraceIdPatternLogbackLayout;
import org.apache.skywalking.apm.toolkit.log.logback.v1.x.logstash.TraceIdJsonProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration(proxyBeanMethods = false)
@ConditionalOnLogstashEnabled
@ConditionalOnClass({LogstashFormatter.class, TraceIdPatternLogbackLayout.class})
@EnableConfigurationProperties({LogstashLoggingProperties.class})
public class LogstashLoggingConfiguration {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LogstashLoggingConfiguration.class);

    @Value("${spring.application.name:-}")
    private String applicationName;

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Module [Logstash Logging] Configure.");
    }

    @Bean
    public LogstashTcpSocketAppender logstashTcpSocketAppender(LoggingProperties loggingProperties, LogstashLoggingProperties logstashLoggingProperties) {

        LogbackConfigurator configurator = new LogbackConfigurator(LoggerFactory.getILoggerFactory());

        LoggerContext loggerContext = configurator.getContext();

        String destination = logstashLoggingProperties.getHost() + SymbolConstants.COLON + logstashLoggingProperties.getPort();

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
        customFields.setService(applicationName);

        LogstashEncoder logstashEncoder = new LogstashEncoder();
        logstashEncoder.setCustomFields(Jackson2Utils.toJson(customFields));
        logstashEncoder.addProvider(traceIdJsonProvider);

        logstashTcpSocketAppender.setEncoder(logstashEncoder);

        // 添加日志输出配置
        Map<String, LogLevel> loggers = loggingProperties.getLoggers();
        loggers.forEach(configurator::logger);

        // 将 logstashTcpSocketAppender 添加到 Logback 日志中
        configurator.root(loggingProperties.getLevel(), logstashTcpSocketAppender);
        configurator.start(logstashTcpSocketAppender);

        return logstashTcpSocketAppender;
    }

    static class CustomFields {

        private String service;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("service", service)
                    .toString();
        }
    }
}
