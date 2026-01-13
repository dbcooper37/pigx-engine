package com.pigx.engine.web.servlet.secure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Set;


/**
 * Filter that applies global rate limiting to configured endpoint patterns.
 * <p>
 * This filter checks incoming requests against the global rate limiter
 * and returns appropriate rate limit headers in responses.
 * </p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class RateLimitFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);

    private static final String HEADER_RATE_LIMIT = "X-RateLimit-Limit";
    private static final String HEADER_RATE_REMAINING = "X-RateLimit-Remaining";
    private static final String HEADER_RATE_RESET = "X-RateLimit-Reset";

    private final GlobalRateLimiter rateLimiter;
    private final Set<String> rateLimitedPatterns;

    public RateLimitFilter(GlobalRateLimiter rateLimiter, Set<String> rateLimitedPatterns) {
        this.rateLimiter = rateLimiter;
        this.rateLimitedPatterns = rateLimitedPatterns;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) 
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUri = request.getRequestURI();

        // Check if this endpoint should be rate limited
        if (shouldApplyRateLimit(requestUri)) {
            try {
                rateLimiter.checkRateLimit(request);
                
                // Add rate limit headers
                addRateLimitHeaders(request, response);
                
            } catch (Exception e) {
                // Rate limit exceeded
                GlobalRateLimiter.RateLimitInfo info = rateLimiter.getRateLimitInfo(request);
                addRateLimitHeaders(info, response);
                
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"rate_limit_exceeded\"," +
                        "\"message\":\"" + e.getMessage() + "\"," +
                        "\"retry_after\":" + (info.getResetTime().getEpochSecond() - System.currentTimeMillis() / 1000) + "}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Checks if rate limiting should be applied to this endpoint.
     */
    private boolean shouldApplyRateLimit(String requestUri) {
        if (rateLimitedPatterns.isEmpty()) {
            return false;
        }

        for (String pattern : rateLimitedPatterns) {
            if (matchesPattern(requestUri, pattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simple pattern matching with * wildcard support.
     */
    private boolean matchesPattern(String uri, String pattern) {
        if (pattern.endsWith("*")) {
            String prefix = pattern.substring(0, pattern.length() - 1);
            return uri.startsWith(prefix);
        }
        return uri.equals(pattern);
    }

    /**
     * Adds rate limit headers to response.
     */
    private void addRateLimitHeaders(HttpServletRequest request, HttpServletResponse response) {
        try {
            GlobalRateLimiter.RateLimitInfo info = rateLimiter.getRateLimitInfo(request);
            addRateLimitHeaders(info, response);
        } catch (Exception e) {
            log.trace("[PIGXD] |- Could not add rate limit headers: {}", e.getMessage());
        }
    }

    /**
     * Adds rate limit headers from RateLimitInfo.
     */
    private void addRateLimitHeaders(GlobalRateLimiter.RateLimitInfo info, HttpServletResponse response) {
        response.setHeader(HEADER_RATE_LIMIT, String.valueOf(info.getLimit()));
        response.setHeader(HEADER_RATE_REMAINING, String.valueOf(info.getRemaining()));
        response.setHeader(HEADER_RATE_RESET, String.valueOf(info.getResetTime().getEpochSecond()));
    }
}
