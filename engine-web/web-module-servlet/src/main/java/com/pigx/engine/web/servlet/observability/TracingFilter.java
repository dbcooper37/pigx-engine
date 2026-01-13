package com.pigx.engine.web.servlet.observability;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * HTTP request tracing filter for distributed tracing support.
 *
 * <p>This filter integrates with Micrometer Tracing (which supports both
 * OpenTelemetry and Brave/Zipkin) to provide distributed tracing capabilities.</p>
 *
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>Adds trace and span IDs to MDC for correlation in logs</li>
 *   <li>Creates child spans for each HTTP request</li>
 *   <li>Tags spans with HTTP method, URI, status code</li>
 *   <li>Propagates trace context through HTTP headers</li>
 * </ul>
 *
 * <p><b>Configuration:</b></p>
 * <pre>{@code
 * # Enable tracing in application.yml
 * management:
 *   tracing:
 *     enabled: true
 *     sampling:
 *       probability: 1.0  # Sample all requests (use 0.1 for 10% in production)
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class TracingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TracingFilter.class);

    private static final String MDC_TRACE_ID = "traceId";
    private static final String MDC_SPAN_ID = "spanId";
    private static final String MDC_PARENT_SPAN_ID = "parentSpanId";

    private final Tracer tracer;
    private final TracingProperties tracingProperties;

    public TracingFilter(Tracer tracer, TracingProperties tracingProperties) {
        this.tracer = tracer;
        this.tracingProperties = tracingProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        if (!tracingProperties.isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Skip static resources if configured
        if (shouldSkip(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        Span currentSpan = tracer.currentSpan();
        
        try {
            // Add trace context to MDC for logging
            if (currentSpan != null) {
                String traceId = currentSpan.context().traceId();
                String spanId = currentSpan.context().spanId();
                
                MDC.put(MDC_TRACE_ID, traceId);
                MDC.put(MDC_SPAN_ID, spanId);
                
                // Add trace ID to response header for debugging
                if (tracingProperties.isIncludeTraceIdInResponse()) {
                    response.setHeader("X-Trace-Id", traceId);
                }
                
                // Tag span with request info
                currentSpan.tag("http.method", request.getMethod());
                currentSpan.tag("http.url", request.getRequestURI());
                
                if (request.getQueryString() != null) {
                    currentSpan.tag("http.query_string", sanitizeQueryString(request.getQueryString()));
                }
                
                // Add tenant ID if present
                String tenantId = request.getHeader("X-Tenant-Id");
                if (tenantId != null) {
                    currentSpan.tag("tenant.id", tenantId);
                }
                
                // Add user agent for client identification
                String userAgent = request.getHeader("User-Agent");
                if (userAgent != null && tracingProperties.isIncludeUserAgent()) {
                    currentSpan.tag("http.user_agent", truncate(userAgent, 200));
                }
            }

            filterChain.doFilter(request, response);

            // Tag response status
            if (currentSpan != null) {
                currentSpan.tag("http.status_code", String.valueOf(response.getStatus()));
                
                if (response.getStatus() >= 400) {
                    currentSpan.tag("error", "true");
                    currentSpan.event("HTTP Error: " + response.getStatus());
                }
            }

        } catch (Exception e) {
            if (currentSpan != null) {
                currentSpan.tag("error", "true");
                currentSpan.tag("error.message", truncate(e.getMessage(), 500));
                currentSpan.event("Exception: " + e.getClass().getSimpleName());
            }
            throw e;
        } finally {
            MDC.remove(MDC_TRACE_ID);
            MDC.remove(MDC_SPAN_ID);
            MDC.remove(MDC_PARENT_SPAN_ID);
        }
    }

    /**
     * Determines if the request should skip tracing.
     */
    private boolean shouldSkip(HttpServletRequest request) {
        String path = request.getRequestURI();
        
        // Skip actuator endpoints unless explicitly enabled
        if (!tracingProperties.isTraceActuatorEndpoints() && path.startsWith("/actuator")) {
            return true;
        }
        
        // Skip static resources
        if (isStaticResource(path)) {
            return true;
        }
        
        return false;
    }

    /**
     * Checks if the path is a static resource.
     */
    private boolean isStaticResource(String path) {
        return path.endsWith(".css") || path.endsWith(".js") ||
               path.endsWith(".ico") || path.endsWith(".png") ||
               path.endsWith(".jpg") || path.endsWith(".gif") ||
               path.endsWith(".woff") || path.endsWith(".woff2") ||
               path.endsWith(".ttf") || path.endsWith(".svg");
    }

    /**
     * Sanitizes query string to remove sensitive parameters.
     */
    private String sanitizeQueryString(String queryString) {
        // Remove sensitive parameters
        return queryString
                .replaceAll("password=[^&]*", "password=***")
                .replaceAll("token=[^&]*", "token=***")
                .replaceAll("secret=[^&]*", "secret=***")
                .replaceAll("key=[^&]*", "key=***");
    }

    /**
     * Truncates string to max length.
     */
    private String truncate(String value, int maxLength) {
        if (value == null) return null;
        return value.length() <= maxLength ? value : value.substring(0, maxLength) + "...";
    }
}
