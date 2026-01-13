package com.pigx.engine.oauth2.authorization.validator;

import com.pigx.engine.core.foundation.audit.SecurityAuditLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;


/**
 * Validates Feign internal communication tokens to prevent security bypass.
 * <p>
 * This validator ensures that internal service-to-service calls are authenticated
 * using HMAC-based signed tokens with timestamp validation to prevent replay attacks.
 * </p>
 *
 * <p><b>Token Format:</b> {@code timestamp:signature}</p>
 * <p>Where signature = HMAC-SHA256(timestamp + requestUri + targetHost, secretKey)</p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class FeignTokenValidator {

    private static final Logger log = LoggerFactory.getLogger(FeignTokenValidator.class);

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private final String secretKey;
    private final boolean enabled;
    private final long tokenValiditySeconds;

    /**
     * Creates a new FeignTokenValidator.
     *
     * @param secretKey the secret key used for HMAC signing
     * @param enabled   whether token validation is enabled
     */
    public FeignTokenValidator(String secretKey, boolean enabled, long tokenValiditySeconds) {
        this.secretKey = secretKey;
        this.enabled = enabled;
        this.tokenValiditySeconds = tokenValiditySeconds > 0 ? tokenValiditySeconds : 300;
    }

    /**
     * Validates the Feign internal token from the request.
     *
     * @param tokenValue the token value from the X-Herodotus-From-In header
     * @param request    the HTTP request
     * @return true if the token is valid, false otherwise
     */
    public boolean validate(String tokenValue, HttpServletRequest request) {
        if (!enabled) {
            log.trace("[PIGXD] |- Feign token validation is disabled, allowing request");
            return true;
        }

        if (StringUtils.isBlank(tokenValue)) {
            log.warn("[PIGXD] |- Feign token is blank");
            return false;
        }

        try {
            // Parse token format: timestamp:signature
            String[] parts = tokenValue.split(":");
            if (parts.length != 2) {
                log.warn("[PIGXD] |- Invalid Feign token format from [{}]", request.getRemoteAddr());
                return false;
            }

            long timestamp = Long.parseLong(parts[0]);
            String providedSignature = parts[1];

            // Check timestamp validity (prevent replay attacks)
            long currentTime = Instant.now().getEpochSecond();
            if (Math.abs(currentTime - timestamp) > tokenValiditySeconds) {
                log.warn("[PIGXD] |- Feign token expired. Token time: {}, Current time: {}, Difference: {}s",
                        timestamp, currentTime, Math.abs(currentTime - timestamp));
                SecurityAuditLogger.logFeignValidation(false, request.getRemoteAddr(), 
                        request.getRequestURI(), "Token expired");
                return false;
            }

            // Compute expected signature
            String requestUri = request.getRequestURI();
            String targetHost = request.getServerName();
            if (StringUtils.isBlank(targetHost)) {
                targetHost = "localhost";
            }
            String dataToSign = timestamp + requestUri + targetHost;
            String expectedSignature = computeHmac(dataToSign);

            // Constant-time comparison to prevent timing attacks
            if (!constantTimeEquals(providedSignature, expectedSignature)) {
                log.warn("[PIGXD] |- Invalid Feign token signature for host [{}] and URI [{}]",
                        targetHost, requestUri);
                // Audit log the invalid token attempt
                SecurityAuditLogger.logFeignValidation(false, request.getRemoteAddr(), requestUri, "Invalid signature");
                return false;
            }

            log.debug("[PIGXD] |- Valid Feign token for host [{}] and URI [{}]", targetHost, requestUri);
            // Audit log the valid request
            SecurityAuditLogger.logFeignValidation(true, request.getRemoteAddr(), requestUri, null);
            return true;

        } catch (NumberFormatException e) {
            log.warn("[PIGXD] |- Invalid timestamp in Feign token from [{}]", request.getRemoteAddr());
            SecurityAuditLogger.logFeignValidation(false, request.getRemoteAddr(), 
                    request.getRequestURI(), "Invalid timestamp format");
            return false;
        } catch (Exception e) {
            log.error("[PIGXD] |- Error validating Feign token from [{}]: {}",
                    request.getRemoteAddr(), e.getMessage());
            SecurityAuditLogger.logFeignValidation(false, request.getRemoteAddr(), 
                    request.getRequestURI(), "Validation error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates a signed Feign token for internal service calls.
     *
     * @param requestUri the target request URI
     * @param targetHost the target service host
     * @return the signed token in format timestamp:signature
     */
    public String generateToken(String requestUri, String targetHost) {
        long timestamp = Instant.now().getEpochSecond();
        String dataToSign = timestamp + requestUri + targetHost;
        String signature = computeHmac(dataToSign);
        return timestamp + ":" + signature;
    }

    private String computeHmac(String data) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    secretKey.getBytes(StandardCharsets.UTF_8),
                    HMAC_ALGORITHM
            );
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute HMAC", e);
        }
    }

    /**
     * Constant-time string comparison to prevent timing attacks.
     */
    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() != b.length()) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
