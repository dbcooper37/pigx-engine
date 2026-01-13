package com.pigx.engine.web.servlet.observability;

import io.micrometer.tracing.Tracer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


/**
 * Auto-configuration for observability features including distributed tracing.
 *
 * <p>This configuration integrates with Micrometer Tracing to provide:</p>
 * <ul>
 *   <li>Distributed tracing with trace ID propagation</li>
 *   <li>Span tagging with HTTP request metadata</li>
 *   <li>MDC integration for correlated logging</li>
 *   <li>Custom metrics collection</li>
 * </ul>
 *
 * <p><b>Supported Tracing Backends:</b></p>
 * <ul>
 *   <li>OpenTelemetry (OTLP exporter)</li>
 *   <li>Zipkin</li>
 *   <li>Jaeger</li>
 *   <li>Wavefront</li>
 * </ul>
 *
 * <p><b>Dependencies:</b></p>
 * <pre>{@code
 * <!-- For OpenTelemetry -->
 * <dependency>
 *     <groupId>io.micrometer</groupId>
 *     <artifactId>micrometer-tracing-bridge-otel</artifactId>
 * </dependency>
 * <dependency>
 *     <groupId>io.opentelemetry</groupId>
 *     <artifactId>opentelemetry-exporter-otlp</artifactId>
 * </dependency>
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Tracer.class)
@EnableConfigurationProperties(TracingProperties.class)
public class ObservabilityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ObservabilityConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Module [Observability] Configure.");
    }

    /**
     * Registers the tracing filter for HTTP request tracing.
     */
    @Bean
    @ConditionalOnBean(Tracer.class)
    @ConditionalOnMissingBean(TracingFilter.class)
    @ConditionalOnProperty(name = "herodotus.tracing.enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<TracingFilter> tracingFilterRegistration(
            Tracer tracer, TracingProperties tracingProperties) {
        
        FilterRegistrationBean<TracingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TracingFilter(tracer, tracingProperties));
        registration.addUrlPatterns("/*");
        registration.setName("tracingFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10); // Run early but after security filters
        
        log.trace("[PIGXD] |- Bean [Tracing Filter] Configure.");
        return registration;
    }
}
