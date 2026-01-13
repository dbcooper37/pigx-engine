package com.pigx.engine.core.autoconfigure.client.servlet.feign;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Configuration properties for Feign client security.
 * <p>
 * These properties configure the security tokens used for internal
 * service-to-service communication via Feign clients.
 * </p>
 *
 * <p><b>Important:</b> The secret key must be the same across all services
 * in the cluster for authentication to work.</p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "herodotus.feign.security")
public class FeignSecurityProperties {

    /**
     * Whether Feign security token generation is enabled.
     * When enabled, all internal Feign calls will include a signed security token.
     */
    private boolean enabled = true;

    /**
     * The secret key used for HMAC signing of Feign tokens.
     * This should be a secure, randomly generated string of at least 32 characters.
     * <p>
     * Can also be set via environment variable: PIGX_FEIGN_SECURITY_KEY
     * </p>
     */
    private String secretKey = getDefaultSecretKey();

    private static String getDefaultSecretKey() {
        String envKey = System.getenv("PIGX_FEIGN_SECURITY_KEY");
        if (envKey != null && !envKey.isEmpty()) {
            return envKey;
        }
        return "pigx-engine-default-feign-secret-key-change-in-production";
    }

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .toString();
    }
}
