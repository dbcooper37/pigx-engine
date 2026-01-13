package com.pigx.engine.web.servlet.secure;

import com.pigx.engine.core.foundation.audit.SecurityAuditLogger;
import com.pigx.engine.web.core.exception.FrequentRequestsException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Global rate limiter for protecting sensitive endpoints.
 * <p>
 * This rate limiter uses a sliding window algorithm to limit requests
 * based on client IP address. It's designed for protecting endpoints that
 * don't have the @AccessLimited annotation but still need rate limiting.
 * </p>
 *
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>Per-IP rate limiting</li>
 *   <li>Sliding window algorithm for accurate limiting</li>
 *   <li>Configurable rate limits per endpoint pattern</li>
 *   <li>Automatic cleanup of expired entries</li>
 *   <li>Integration with security audit logging</li>
 * </ul>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class GlobalRateLimiter {

    private static final Logger log = LoggerFactory.getLogger(GlobalRateLimiter.class);

    /**
     * Map of client IP to rate limit state.
     */
    private final ConcurrentMap<String, RateLimitState> rateLimitStates = new ConcurrentHashMap<>();

    /**
     * Map of endpoint patterns to their rate limit configurations.
     */
    private final ConcurrentMap<String, RateLimitConfig> endpointConfigs = new ConcurrentHashMap<>();

    /**
     * Default rate limit configuration.
     */
    private final RateLimitConfig defaultConfig;

    /**
     * Last cleanup timestamp.
     */
    private final AtomicLong lastCleanup = new AtomicLong(System.currentTimeMillis());

    /**
     * Cleanup interval in milliseconds (5 minutes).
     */
    private static final long CLEANUP_INTERVAL = 5 * 60 * 1000;

    public GlobalRateLimiter(int defaultMaxRequests, Duration defaultWindow) {
        this.defaultConfig = new RateLimitConfig(defaultMaxRequests, defaultWindow);
    }

    /**
     * Registers a rate limit configuration for an endpoint pattern.
     *
     * @param pattern     the endpoint pattern (can contain * wildcard)
     * @param maxRequests maximum requests allowed in the window
     * @param window      the time window
     */
    public void registerEndpoint(String pattern, int maxRequests, Duration window) {
        endpointConfigs.put(pattern, new RateLimitConfig(maxRequests, window));
        log.debug("[PIGXD] |- Registered rate limit for pattern [{}]: {} requests per {}", 
                pattern, maxRequests, window);
    }

    /**
     * Checks if a request should be allowed based on rate limiting.
     *
     * @param request the HTTP request
     * @return true if the request is allowed
     * @throws FrequentRequestsException if rate limit is exceeded
     */
    public boolean checkRateLimit(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        String endpoint = request.getRequestURI();
        RateLimitConfig config = getConfigForEndpoint(endpoint);

        String key = clientIp + ":" + endpoint;
        RateLimitState state = rateLimitStates.computeIfAbsent(key, 
                k -> new RateLimitState(config.windowMillis));

        // Check and potentially cleanup old entries
        maybeCleanup();

        boolean allowed = state.incrementAndCheck(config.maxRequests, config.windowMillis);

        if (!allowed) {
            log.warn("[PIGXD] |- Rate limit exceeded for IP [{}] on endpoint [{}]. " +
                    "Limit: {} requests per {}ms", 
                    clientIp, endpoint, config.maxRequests, config.windowMillis);
            
            // Audit log the rate limit event
            SecurityAuditLogger.logRateLimitExceeded(clientIp, endpoint, config.maxRequests);
            
            throw new FrequentRequestsException(
                    "Rate limit exceeded. Please try again in " + (config.windowMillis / 1000) + " seconds.");
        }

        return true;
    }

    /**
     * Gets rate limit information for a client.
     *
     * @param request the HTTP request
     * @return rate limit info containing remaining requests and reset time
     */
    public RateLimitInfo getRateLimitInfo(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        String endpoint = request.getRequestURI();
        String key = clientIp + ":" + endpoint;
        RateLimitConfig config = getConfigForEndpoint(endpoint);

        RateLimitState state = rateLimitStates.get(key);
        if (state == null) {
            return new RateLimitInfo(config.maxRequests, config.maxRequests, 
                    Instant.now().plusMillis(config.windowMillis));
        }

        int remaining = Math.max(0, config.maxRequests - state.getCount());
        Instant resetTime = Instant.ofEpochMilli(state.getWindowStart() + config.windowMillis);
        
        return new RateLimitInfo(config.maxRequests, remaining, resetTime);
    }

    /**
     * Resets rate limit state for a specific client and endpoint.
     *
     * @param clientIp the client IP address
     * @param endpoint the endpoint
     */
    public void resetRateLimit(String clientIp, String endpoint) {
        String key = clientIp + ":" + endpoint;
        rateLimitStates.remove(key);
        log.debug("[PIGXD] |- Reset rate limit for [{}]", key);
    }

    /**
     * Gets the appropriate configuration for an endpoint.
     */
    private RateLimitConfig getConfigForEndpoint(String endpoint) {
        // Check for exact match first
        if (endpointConfigs.containsKey(endpoint)) {
            return endpointConfigs.get(endpoint);
        }

        // Check for pattern matches
        for (var entry : endpointConfigs.entrySet()) {
            if (matchesPattern(endpoint, entry.getKey())) {
                return entry.getValue();
            }
        }

        return defaultConfig;
    }

    /**
     * Simple pattern matching with * wildcard support.
     */
    private boolean matchesPattern(String endpoint, String pattern) {
        if (pattern.endsWith("*")) {
            String prefix = pattern.substring(0, pattern.length() - 1);
            return endpoint.startsWith(prefix);
        }
        return endpoint.equals(pattern);
    }

    /**
     * Gets client IP from request, considering proxy headers.
     */
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            // Take the first IP in the chain
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    /**
     * Cleans up expired rate limit entries periodically.
     */
    private void maybeCleanup() {
        long now = System.currentTimeMillis();
        if (now - lastCleanup.get() > CLEANUP_INTERVAL) {
            if (lastCleanup.compareAndSet(lastCleanup.get(), now)) {
                int removed = 0;
                for (var entry : rateLimitStates.entrySet()) {
                    if (entry.getValue().isExpired()) {
                        rateLimitStates.remove(entry.getKey());
                        removed++;
                    }
                }
                if (removed > 0) {
                    log.debug("[PIGXD] |- Cleaned up {} expired rate limit entries", removed);
                }
            }
        }
    }

    /**
     * Rate limit configuration for an endpoint.
     */
    private static class RateLimitConfig {
        final int maxRequests;
        final long windowMillis;

        RateLimitConfig(int maxRequests, Duration window) {
            this.maxRequests = maxRequests;
            this.windowMillis = window.toMillis();
        }
    }

    /**
     * Rate limit state for a client-endpoint pair.
     */
    private static class RateLimitState {
        private final AtomicInteger count = new AtomicInteger(0);
        private final AtomicLong windowStart = new AtomicLong(System.currentTimeMillis());
        private final long windowMillis;

        RateLimitState(long windowMillis) {
            this.windowMillis = windowMillis;
        }

        synchronized boolean incrementAndCheck(int maxRequests, long windowMillis) {
            long now = System.currentTimeMillis();
            
            // Check if window has expired
            if (now - windowStart.get() > windowMillis) {
                // Reset the window
                windowStart.set(now);
                count.set(1);
                return true;
            }

            // Check if limit exceeded
            if (count.get() >= maxRequests) {
                return false;
            }

            count.incrementAndGet();
            return true;
        }

        int getCount() {
            return count.get();
        }

        long getWindowStart() {
            return windowStart.get();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - windowStart.get() > windowMillis * 2;
        }
    }

    /**
     * Rate limit information for a client.
     */
    public static class RateLimitInfo {
        private final int limit;
        private final int remaining;
        private final Instant resetTime;

        public RateLimitInfo(int limit, int remaining, Instant resetTime) {
            this.limit = limit;
            this.remaining = remaining;
            this.resetTime = resetTime;
        }

        public int getLimit() {
            return limit;
        }

        public int getRemaining() {
            return remaining;
        }

        public Instant getResetTime() {
            return resetTime;
        }
    }
}
