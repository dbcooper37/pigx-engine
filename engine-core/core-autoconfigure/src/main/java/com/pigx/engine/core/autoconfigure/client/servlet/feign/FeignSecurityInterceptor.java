package com.pigx.engine.core.autoconfigure.client.servlet.feign;

import com.pigx.engine.core.definition.constant.HerodotusHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;


/**
 * Feign request interceptor that adds signed security tokens to internal service calls.
 * <p>
 * This interceptor generates HMAC-SHA256 signed tokens for requests that have the
 * {@code X-Herodotus-From-In} header set (typically by {@link FeignInnerContract}).
 * The token includes a timestamp to prevent replay attacks.
 * </p>
 *
 * <p><b>Token Format:</b> {@code timestamp:signature}</p>
 * <p>Where signature = HMAC-SHA256(timestamp + requestPath + targetHost, secretKey)</p>
 *
 * <p><b>Configuration:</b> The secret key should be configured via:</p>
 * <ul>
 *   <li>Property: {@code herodotus.feign.security.secret-key}</li>
 *   <li>Environment: {@code PIGX_FEIGN_SECURITY_KEY}</li>
 * </ul>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 * @see FeignInnerContract
 */
public class FeignSecurityInterceptor implements RequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(FeignSecurityInterceptor.class);

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final String secretKey;
    private final boolean enabled;

    /**
     * Creates a new FeignSecurityInterceptor.
     *
     * @param secretKey the secret key for HMAC signing
     * @param enabled   whether token generation is enabled
     */
    public FeignSecurityInterceptor(String secretKey, boolean enabled) {
        this.secretKey = secretKey;
        this.enabled = enabled;
    }

    @Override
    public void apply(RequestTemplate template) {
        // Check if this is an internal call (has the inner header marker)
        if (!template.headers().containsKey(HerodotusHeaders.X_HERODOTUS_FROM_IN)) {
            return;
        }

        if (!enabled) {
            log.trace("[PIGXD] |- Feign security token generation is disabled");
            return;
        }

        try {
            // Generate signed token
            String signedToken = generateSignedToken(template);
            
            // Replace the simple "true" value with the signed token
            template.removeHeader(HerodotusHeaders.X_HERODOTUS_FROM_IN);
            template.header(HerodotusHeaders.X_HERODOTUS_FROM_IN, signedToken);
            
            log.debug("[PIGXD] |- Added signed Feign security token for request to [{}]", 
                    template.path());
        } catch (Exception e) {
            log.error("[PIGXD] |- Failed to generate Feign security token: {}", e.getMessage());
            // Keep the original "true" value if signing fails
        }
    }

    /**
     * Generates a signed token for the request.
     *
     * @param template the Feign request template
     * @return the signed token in format timestamp:signature
     */
    private String generateSignedToken(RequestTemplate template) {
        long timestamp = Instant.now().getEpochSecond();
        String requestPath = template.path();
        String targetHost = getTargetHost(template);
        
        // Create data to sign: timestamp + path + host
        String dataToSign = timestamp + requestPath + targetHost;
        String signature = computeHmac(dataToSign);
        
        return timestamp + ":" + signature;
    }

    /**
     * Extracts the target host from the request template.
     */
    private String getTargetHost(RequestTemplate template) {
        // Try to get from the target URL
        if (template.feignTarget() != null) {
            String url = template.feignTarget().url();
            if (url != null) {
                // Extract host from URL
                try {
                    java.net.URL parsedUrl = new java.net.URL(url);
                    return parsedUrl.getHost();
                } catch (Exception e) {
                    // Fall back to url as is
                    return url;
                }
            }
        }
        return "localhost";
    }

    /**
     * Computes HMAC-SHA256 signature.
     */
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
}
