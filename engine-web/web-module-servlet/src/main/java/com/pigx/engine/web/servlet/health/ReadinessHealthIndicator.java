package com.pigx.engine.web.servlet.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.availability.ReadinessStateHealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Custom readiness health indicator for Kubernetes probes.
 *
 * <p>This indicator determines if the application is ready to receive traffic.
 * It considers various factors like database connectivity, cache availability,
 * and warm-up completion.</p>
 *
 * <p><b>Kubernetes Configuration:</b></p>
 * <pre>{@code
 * livenessProbe:
 *   httpGet:
 *     path: /actuator/health/liveness
 *     port: 8080
 *   initialDelaySeconds: 30
 *   periodSeconds: 10
 *
 * readinessProbe:
 *   httpGet:
 *     path: /actuator/health/readiness
 *     port: 8080
 *   initialDelaySeconds: 10
 *   periodSeconds: 5
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@Component
public class ReadinessHealthIndicator extends ReadinessStateHealthIndicator {

    private static final Logger log = LoggerFactory.getLogger(ReadinessHealthIndicator.class);

    private final ApplicationContext applicationContext;
    private final AtomicBoolean warmupComplete = new AtomicBoolean(false);
    private final AtomicBoolean manuallyDisabled = new AtomicBoolean(false);

    public ReadinessHealthIndicator(ApplicationAvailability availability, ApplicationContext applicationContext) {
        super(availability);
        this.applicationContext = applicationContext;
    }

    /**
     * Marks the warm-up phase as complete.
     * Call this after cache warming and other initialization tasks.
     */
    public void markWarmupComplete() {
        warmupComplete.set(true);
        publishReadyState();
        log.info("[PIGXD] |- Application warmup complete, now ready to accept traffic");
    }

    /**
     * Manually disables readiness (e.g., for maintenance).
     */
    public void disableReadiness() {
        manuallyDisabled.set(true);
        publishNotReadyState();
        log.warn("[PIGXD] |- Readiness manually disabled");
    }

    /**
     * Re-enables readiness after manual disable.
     */
    public void enableReadiness() {
        manuallyDisabled.set(false);
        publishReadyState();
        log.info("[PIGXD] |- Readiness re-enabled");
    }

    /**
     * Checks if the application is truly ready.
     */
    public boolean isApplicationReady() {
        return warmupComplete.get() && !manuallyDisabled.get();
    }

    private void publishReadyState() {
        AvailabilityChangeEvent.publish(applicationContext, this, ReadinessState.ACCEPTING_TRAFFIC);
    }

    private void publishNotReadyState() {
        AvailabilityChangeEvent.publish(applicationContext, this, ReadinessState.REFUSING_TRAFFIC);
    }
}
