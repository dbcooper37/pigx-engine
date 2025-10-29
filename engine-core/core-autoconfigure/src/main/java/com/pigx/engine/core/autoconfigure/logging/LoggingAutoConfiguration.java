package com.pigx.engine.core.autoconfigure.logging;

import com.pigx.engine.core.autoconfigure.logging.logstash.LogstashLoggingConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;


@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@Import({
        LogstashLoggingConfiguration.class
})
public class LoggingAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoggingAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Logging] Configure.");
    }
}
