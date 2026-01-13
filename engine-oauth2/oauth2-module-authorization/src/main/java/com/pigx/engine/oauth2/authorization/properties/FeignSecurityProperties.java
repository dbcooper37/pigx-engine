package com.pigx.engine.oauth2.authorization.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Configuration properties for Feign internal communication security.
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "herodotus.feign.security")
public class FeignSecurityProperties {

    /**
     * Whether Feign token validation is enabled.
     * When enabled, all internal service calls must include a valid signed token.
     */
    private boolean enabled = true;

    /**
     * The secret key used for HMAC signing of Feign tokens.
     * This should be a secure, randomly generated string.
     * <p>
     * <b>Important:</b> This key must be the same across all services in the cluster.
     * </p>
     */
    private String secretKey = "pigx-engine-default-feign-secret-key-change-in-production";

    /**
     * Token validity duration in seconds.
     * Tokens older than this will be rejected (prevents replay attacks).
     */
    private long tokenValiditySeconds = 300;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(long tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("tokenValiditySeconds", tokenValiditySeconds)
                .toString();
    }
}
