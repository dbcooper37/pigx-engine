package com.pigx.engine.core.foundation.feature;

import com.google.common.base.MoreObjects;

import java.util.List;


/**
 * Configuration for a single feature flag.
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class FeatureConfig {

    /**
     * Whether the feature is enabled.
     */
    private boolean enabled = false;

    /**
     * Rollout percentage (0-100).
     * 100 means fully enabled, 50 means 50% of requests get the feature.
     */
    private int rolloutPercentage = 100;

    /**
     * Description of the feature for documentation.
     */
    private String description;

    /**
     * Variants for A/B testing.
     * Example: ["A", "B", "control"]
     */
    private List<String> variants;

    /**
     * When the feature was enabled.
     */
    private String enabledSince;

    /**
     * Owner/team responsible for this feature.
     */
    private String owner;

    // Getters and Setters

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRolloutPercentage() {
        return rolloutPercentage;
    }

    public void setRolloutPercentage(int rolloutPercentage) {
        this.rolloutPercentage = rolloutPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public String getEnabledSince() {
        return enabledSince;
    }

    public void setEnabledSince(String enabledSince) {
        this.enabledSince = enabledSince;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("rolloutPercentage", rolloutPercentage)
                .add("description", description)
                .add("owner", owner)
                .toString();
    }
}
