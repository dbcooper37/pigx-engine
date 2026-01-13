package com.pigx.engine.web.servlet.observability;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Configuration properties for distributed tracing.
 *
 * <p><b>Example configuration:</b></p>
 * <pre>{@code
 * herodotus:
 *   tracing:
 *     enabled: true
 *     include-trace-id-in-response: true
 *     include-user-agent: false
 *     trace-actuator-endpoints: false
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "herodotus.tracing")
public class TracingProperties {

    /**
     * Whether tracing is enabled.
     * Default: true
     */
    private boolean enabled = true;

    /**
     * Whether to include trace ID in response headers.
     * Useful for debugging but may expose internal information.
     * Default: true
     */
    private boolean includeTraceIdInResponse = true;

    /**
     * Whether to include User-Agent in span tags.
     * Can increase storage if there are many unique clients.
     * Default: false
     */
    private boolean includeUserAgent = false;

    /**
     * Whether to trace actuator endpoints.
     * Usually disabled to reduce noise in traces.
     * Default: false
     */
    private boolean traceActuatorEndpoints = false;

    /**
     * Sampling probability for traces (0.0 to 1.0).
     * 1.0 means all requests are traced.
     * Use lower values in production for cost control.
     * Default: 1.0
     */
    private double samplingProbability = 1.0;

    // Getters and Setters

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isIncludeTraceIdInResponse() {
        return includeTraceIdInResponse;
    }

    public void setIncludeTraceIdInResponse(boolean includeTraceIdInResponse) {
        this.includeTraceIdInResponse = includeTraceIdInResponse;
    }

    public boolean isIncludeUserAgent() {
        return includeUserAgent;
    }

    public void setIncludeUserAgent(boolean includeUserAgent) {
        this.includeUserAgent = includeUserAgent;
    }

    public boolean isTraceActuatorEndpoints() {
        return traceActuatorEndpoints;
    }

    public void setTraceActuatorEndpoints(boolean traceActuatorEndpoints) {
        this.traceActuatorEndpoints = traceActuatorEndpoints;
    }

    public double getSamplingProbability() {
        return samplingProbability;
    }

    public void setSamplingProbability(double samplingProbability) {
        this.samplingProbability = samplingProbability;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("includeTraceIdInResponse", includeTraceIdInResponse)
                .add("includeUserAgent", includeUserAgent)
                .add("traceActuatorEndpoints", traceActuatorEndpoints)
                .add("samplingProbability", samplingProbability)
                .toString();
    }
}
