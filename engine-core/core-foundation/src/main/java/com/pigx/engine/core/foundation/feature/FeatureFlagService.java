package com.pigx.engine.core.foundation.feature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Service for managing feature flags.
 *
 * <p>This service provides a simple in-memory feature flag implementation
 * that can be extended to integrate with external feature flag services
 * like LaunchDarkly, Split.io, or Unleash.</p>
 *
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>Simple boolean feature toggles</li>
 *   <li>Gradual rollout with percentage-based enabling</li>
 *   <li>User/tenant-specific feature enablement</li>
 *   <li>Feature flag variants for A/B testing</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>{@code
 * @Autowired
 * private FeatureFlagService featureFlags;
 *
 * public void processOrder(Order order) {
 *     if (featureFlags.isEnabled("new-checkout-flow")) {
 *         processWithNewFlow(order);
 *     } else {
 *         processWithLegacyFlow(order);
 *     }
 * }
 * }</pre>
 *
 * <p><b>Configuration:</b></p>
 * <pre>{@code
 * herodotus:
 *   features:
 *     enabled: true
 *     flags:
 *       new-checkout-flow:
 *         enabled: true
 *         rollout-percentage: 50
 *       beta-search:
 *         enabled: false
 * }</pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 * @see FeatureFlag
 * @see FeatureFlagProperties
 */
@Service
public class FeatureFlagService {

    private static final Logger log = LoggerFactory.getLogger(FeatureFlagService.class);

    private final Map<String, FeatureConfig> features = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> userOverrides = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> tenantOverrides = new ConcurrentHashMap<>();
    private final FeatureFlagProperties properties;

    public FeatureFlagService(FeatureFlagProperties properties) {
        this.properties = properties;
        initializeFromProperties();
    }

    private void initializeFromProperties() {
        if (properties.getFlags() != null) {
            properties.getFlags().forEach((name, config) -> {
                features.put(name, config);
                log.info("[PIGXD] |- Feature flag [{}] initialized: enabled={}, rollout={}%",
                        name, config.isEnabled(), config.getRolloutPercentage());
            });
        }
    }

    /**
     * Checks if a feature is enabled globally.
     *
     * @param featureName the feature name
     * @return true if the feature is enabled
     */
    public boolean isEnabled(String featureName) {
        if (!properties.isEnabled()) {
            return true; // All features enabled when flag system is disabled
        }

        FeatureConfig config = features.get(featureName);
        if (config == null) {
            log.warn("[PIGXD] |- Feature flag [{}] not found, defaulting to disabled", featureName);
            return false;
        }

        if (!config.isEnabled()) {
            return false;
        }

        // Check rollout percentage
        if (config.getRolloutPercentage() < 100) {
            return checkRollout(config.getRolloutPercentage());
        }

        return true;
    }

    /**
     * Checks if a feature is enabled for a specific user.
     *
     * @param featureName the feature name
     * @param userId      the user ID
     * @return true if the feature is enabled for this user
     */
    public boolean isEnabledForUser(String featureName, String userId) {
        // Check user-specific override first
        Set<String> enabledUsers = userOverrides.get(featureName);
        if (enabledUsers != null && enabledUsers.contains(userId)) {
            return true;
        }

        // Fall back to global check with consistent hashing for rollout
        FeatureConfig config = features.get(featureName);
        if (config == null || !config.isEnabled()) {
            return false;
        }

        if (config.getRolloutPercentage() < 100) {
            return checkRolloutConsistent(config.getRolloutPercentage(), userId);
        }

        return true;
    }

    /**
     * Checks if a feature is enabled for a specific tenant.
     *
     * @param featureName the feature name
     * @param tenantId    the tenant ID
     * @return true if the feature is enabled for this tenant
     */
    public boolean isEnabledForTenant(String featureName, String tenantId) {
        // Check tenant-specific override first
        Set<String> enabledTenants = tenantOverrides.get(featureName);
        if (enabledTenants != null && enabledTenants.contains(tenantId)) {
            return true;
        }

        // Fall back to global check
        return isEnabled(featureName);
    }

    /**
     * Gets a feature flag variant for A/B testing.
     *
     * @param featureName the feature name
     * @param userId      the user ID for consistent variant assignment
     * @return the variant name ("A", "B", "control", etc.)
     */
    public String getVariant(String featureName, String userId) {
        FeatureConfig config = features.get(featureName);
        if (config == null || config.getVariants() == null || config.getVariants().isEmpty()) {
            return "control";
        }

        // Use consistent hashing to assign variant
        int hash = Math.abs((featureName + userId).hashCode());
        String[] variants = config.getVariants().toArray(new String[0]);
        return variants[hash % variants.length];
    }

    /**
     * Dynamically enables a feature.
     */
    public void enableFeature(String featureName) {
        features.compute(featureName, (name, config) -> {
            if (config == null) {
                config = new FeatureConfig();
            }
            config.setEnabled(true);
            log.info("[PIGXD] |- Feature flag [{}] enabled dynamically", featureName);
            return config;
        });
    }

    /**
     * Dynamically disables a feature.
     */
    public void disableFeature(String featureName) {
        features.computeIfPresent(featureName, (name, config) -> {
            config.setEnabled(false);
            log.info("[PIGXD] |- Feature flag [{}] disabled dynamically", featureName);
            return config;
        });
    }

    /**
     * Sets the rollout percentage for gradual rollout.
     */
    public void setRolloutPercentage(String featureName, int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }

        features.compute(featureName, (name, config) -> {
            if (config == null) {
                config = new FeatureConfig();
            }
            config.setRolloutPercentage(percentage);
            log.info("[PIGXD] |- Feature flag [{}] rollout set to {}%", featureName, percentage);
            return config;
        });
    }

    /**
     * Enables a feature for a specific user.
     */
    public void enableForUser(String featureName, String userId) {
        userOverrides.computeIfAbsent(featureName, k -> ConcurrentHashMap.newKeySet())
                .add(userId);
        log.info("[PIGXD] |- Feature flag [{}] enabled for user [{}]", featureName, userId);
    }

    /**
     * Enables a feature for a specific tenant.
     */
    public void enableForTenant(String featureName, String tenantId) {
        tenantOverrides.computeIfAbsent(featureName, k -> ConcurrentHashMap.newKeySet())
                .add(tenantId);
        log.info("[PIGXD] |- Feature flag [{}] enabled for tenant [{}]", featureName, tenantId);
    }

    /**
     * Gets all registered feature flags with their status.
     */
    public Map<String, FeatureConfig> getAllFeatures() {
        return Map.copyOf(features);
    }

    /**
     * Random rollout check (non-deterministic).
     */
    private boolean checkRollout(int percentage) {
        return ThreadLocalRandom.current().nextInt(100) < percentage;
    }

    /**
     * Consistent rollout check using hash (deterministic for same input).
     */
    private boolean checkRolloutConsistent(int percentage, String identifier) {
        int hash = Math.abs(identifier.hashCode()) % 100;
        return hash < percentage;
    }
}
