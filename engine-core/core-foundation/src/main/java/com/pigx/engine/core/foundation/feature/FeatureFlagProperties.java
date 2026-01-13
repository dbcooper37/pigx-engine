package com.pigx.engine.core.foundation.feature;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;


/**
 * Configuration properties for the feature flag system.
 *
 * <p><b>Example configuration:</b></p>
 * <pre>{@code
 * herodotus:
 *   features:
 *     enabled: true
 *     flags:
 *       new-checkout-flow:
 *         enabled: true
 *         rollout-percentage: 50
 *         description: "New streamlined checkout experience"
 *         owner: "payments-team"
 *       beta-search:
 *         enabled: true
 *         rollout-percentage: 10
 *         variants:
 *           - A
 *           - B
 *           - control
 *       dark-mode:
 *         enabled: false
 *         description: "Dark mode UI theme"
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "herodotus.features")
public class FeatureFlagProperties {

    /**
     * Whether the feature flag system is enabled.
     * When disabled, all features are considered enabled (pass-through mode).
     */
    private boolean enabled = true;

    /**
     * Map of feature flag configurations.
     * Key is the feature name, value is the configuration.
     */
    private Map<String, FeatureConfig> flags = new HashMap<>();

    /**
     * Default rollout percentage for new features.
     */
    private int defaultRolloutPercentage = 100;

    /**
     * Whether to log feature flag evaluations.
     * Useful for debugging but may be verbose in production.
     */
    private boolean logEvaluations = false;

    // Getters and Setters

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, FeatureConfig> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, FeatureConfig> flags) {
        this.flags = flags;
    }

    public int getDefaultRolloutPercentage() {
        return defaultRolloutPercentage;
    }

    public void setDefaultRolloutPercentage(int defaultRolloutPercentage) {
        this.defaultRolloutPercentage = defaultRolloutPercentage;
    }

    public boolean isLogEvaluations() {
        return logEvaluations;
    }

    public void setLogEvaluations(boolean logEvaluations) {
        this.logEvaluations = logEvaluations;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("flagCount", flags != null ? flags.size() : 0)
                .add("defaultRolloutPercentage", defaultRolloutPercentage)
                .add("logEvaluations", logEvaluations)
                .toString();
    }
}
