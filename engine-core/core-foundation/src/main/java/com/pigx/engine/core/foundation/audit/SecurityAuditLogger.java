package com.pigx.engine.core.foundation.audit;

import com.pigx.engine.core.foundation.context.TenantContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Centralized security audit logger for tracking security-related events.
 * <p>
 * This logger provides structured logging for security events such as:
 * <ul>
 *   <li>Authentication attempts (success/failure)</li>
 *   <li>Authorization decisions</li>
 *   <li>Cryptographic operations</li>
 *   <li>Session management events</li>
 *   <li>Internal service communication</li>
 * </ul>
 * </p>
 *
 * <p><b>Log Format:</b> Events are logged in a structured format suitable for
 * log aggregation systems like ELK Stack or Splunk.</p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class SecurityAuditLogger {

    private static final Logger AUDIT_LOG = LoggerFactory.getLogger("SECURITY_AUDIT");

    private static final String MDC_TENANT_ID = "tenantId";
    private static final String MDC_USER_ID = "userId";
    private static final String MDC_SESSION_ID = "sessionId";
    private static final String MDC_CLIENT_IP = "clientIp";
    private static final String MDC_EVENT_TYPE = "eventType";

    /**
     * Security event types for categorization.
     */
    public enum EventType {
        // Authentication events
        AUTH_LOGIN_SUCCESS,
        AUTH_LOGIN_FAILURE,
        AUTH_LOGOUT,
        AUTH_TOKEN_ISSUED,
        AUTH_TOKEN_REVOKED,
        AUTH_TOKEN_EXPIRED,
        AUTH_SESSION_CREATED,
        AUTH_SESSION_EXPIRED,
        
        // Authorization events
        AUTHZ_ACCESS_GRANTED,
        AUTHZ_ACCESS_DENIED,
        AUTHZ_PRIVILEGE_ESCALATION_ATTEMPT,
        
        // Feign/Internal communication events
        FEIGN_VALID_REQUEST,
        FEIGN_INVALID_TOKEN,
        FEIGN_TOKEN_EXPIRED,
        
        // Crypto events
        CRYPTO_ENCRYPT_SUCCESS,
        CRYPTO_ENCRYPT_FAILURE,
        CRYPTO_DECRYPT_SUCCESS,
        CRYPTO_DECRYPT_FAILURE,
        CRYPTO_SESSION_EXPIRED,
        CRYPTO_KEY_GENERATED,
        
        // Session events
        SESSION_CREATED,
        SESSION_DESTROYED,
        SESSION_HIJACK_ATTEMPT,
        
        // Data access events
        DATA_TENANT_SWITCH,
        DATA_SENSITIVE_ACCESS,
        
        // Rate limiting events
        RATE_LIMIT_EXCEEDED,
        
        // General security events
        SUSPICIOUS_ACTIVITY,
        SECURITY_CONFIG_CHANGED
    }

    /**
     * Logs a security audit event.
     *
     * @param eventType the type of security event
     * @param message   descriptive message about the event
     * @param details   additional details as key-value pairs
     */
    public static void log(EventType eventType, String message, Map<String, Object> details) {
        try {
            // Set MDC context for structured logging
            MDC.put(MDC_TENANT_ID, TenantContextHolder.getTenantId());
            MDC.put(MDC_EVENT_TYPE, eventType.name());

            StringBuilder logMessage = new StringBuilder();
            logMessage.append("[AUDIT] ")
                    .append("event=").append(eventType.name())
                    .append(" | timestamp=").append(Instant.now())
                    .append(" | tenant=").append(TenantContextHolder.getTenantId())
                    .append(" | message=").append(message);

            if (details != null && !details.isEmpty()) {
                logMessage.append(" | details={");
                details.forEach((key, value) ->
                        logMessage.append(key).append("=").append(value).append(", "));
                logMessage.setLength(logMessage.length() - 2); // Remove trailing ", "
                logMessage.append("}");
            }

            // Log based on event severity
            if (isSecurityThreat(eventType)) {
                AUDIT_LOG.warn(logMessage.toString());
            } else if (isFailureEvent(eventType)) {
                AUDIT_LOG.info(logMessage.toString());
            } else {
                AUDIT_LOG.debug(logMessage.toString());
            }

        } finally {
            MDC.remove(MDC_TENANT_ID);
            MDC.remove(MDC_EVENT_TYPE);
        }
    }

    /**
     * Logs a security event with user context.
     */
    public static void log(EventType eventType, String userId, String clientIp, String message, Map<String, Object> details) {
        try {
            MDC.put(MDC_USER_ID, userId);
            MDC.put(MDC_CLIENT_IP, clientIp);
            
            Map<String, Object> enrichedDetails = new ConcurrentHashMap<>();
            if (details != null) {
                enrichedDetails.putAll(details);
            }
            enrichedDetails.put("userId", userId);
            enrichedDetails.put("clientIp", clientIp);

            log(eventType, message, enrichedDetails);
        } finally {
            MDC.remove(MDC_USER_ID);
            MDC.remove(MDC_CLIENT_IP);
        }
    }

    /**
     * Convenience method for authentication success events.
     */
    public static void logAuthSuccess(String userId, String clientIp, String authMethod) {
        Map<String, Object> details = new ConcurrentHashMap<>();
        details.put("authMethod", authMethod);
        log(EventType.AUTH_LOGIN_SUCCESS, userId, clientIp, 
                "User authenticated successfully", details);
    }

    /**
     * Convenience method for authentication failure events.
     */
    public static void logAuthFailure(String userId, String clientIp, String reason) {
        Map<String, Object> details = new ConcurrentHashMap<>();
        details.put("reason", reason);
        log(EventType.AUTH_LOGIN_FAILURE, userId, clientIp,
                "Authentication failed", details);
    }

    /**
     * Convenience method for access denied events.
     */
    public static void logAccessDenied(String userId, String clientIp, String resource, String reason) {
        Map<String, Object> details = new ConcurrentHashMap<>();
        details.put("resource", resource);
        details.put("reason", reason);
        log(EventType.AUTHZ_ACCESS_DENIED, userId, clientIp,
                "Access denied to resource", details);
    }

    /**
     * Convenience method for Feign token validation events.
     */
    public static void logFeignValidation(boolean valid, String clientIp, String uri, String reason) {
        Map<String, Object> details = new ConcurrentHashMap<>();
        details.put("uri", uri);
        if (reason != null) {
            details.put("reason", reason);
        }
        
        EventType eventType = valid ? EventType.FEIGN_VALID_REQUEST : EventType.FEIGN_INVALID_TOKEN;
        String message = valid ? "Valid Feign internal request" : "Invalid Feign token detected";
        
        log(eventType, null, clientIp, message, details);
    }

    /**
     * Convenience method for crypto events.
     */
    public static void logCryptoEvent(boolean success, boolean isEncrypt, String sessionId, String reason) {
        Map<String, Object> details = new ConcurrentHashMap<>();
        details.put("sessionId", sessionId != null ? maskSessionId(sessionId) : "unknown");
        if (reason != null) {
            details.put("reason", reason);
        }
        
        EventType eventType;
        if (isEncrypt) {
            eventType = success ? EventType.CRYPTO_ENCRYPT_SUCCESS : EventType.CRYPTO_ENCRYPT_FAILURE;
        } else {
            eventType = success ? EventType.CRYPTO_DECRYPT_SUCCESS : EventType.CRYPTO_DECRYPT_FAILURE;
        }
        
        String message = (isEncrypt ? "Encrypt" : "Decrypt") + " operation " + (success ? "succeeded" : "failed");
        log(eventType, message, details);
    }

    /**
     * Convenience method for rate limiting events.
     */
    public static void logRateLimitExceeded(String clientIp, String endpoint, int limit) {
        Map<String, Object> details = new ConcurrentHashMap<>();
        details.put("endpoint", endpoint);
        details.put("limit", limit);
        log(EventType.RATE_LIMIT_EXCEEDED, null, clientIp,
                "Rate limit exceeded", details);
    }

    /**
     * Convenience method for suspicious activity.
     */
    public static void logSuspiciousActivity(String clientIp, String description, Map<String, Object> context) {
        Map<String, Object> details = new ConcurrentHashMap<>();
        details.put("description", description);
        if (context != null) {
            details.putAll(context);
        }
        log(EventType.SUSPICIOUS_ACTIVITY, null, clientIp,
                "Suspicious activity detected", details);
    }

    /**
     * Masks sensitive session ID for logging.
     */
    private static String maskSessionId(String sessionId) {
        if (sessionId == null || sessionId.length() < 8) {
            return "***";
        }
        return sessionId.substring(0, 4) + "****" + sessionId.substring(sessionId.length() - 4);
    }

    /**
     * Determines if the event type represents a security threat.
     */
    private static boolean isSecurityThreat(EventType eventType) {
        return eventType == EventType.AUTHZ_PRIVILEGE_ESCALATION_ATTEMPT
                || eventType == EventType.FEIGN_INVALID_TOKEN
                || eventType == EventType.SESSION_HIJACK_ATTEMPT
                || eventType == EventType.SUSPICIOUS_ACTIVITY;
    }

    /**
     * Determines if the event type represents a failure.
     */
    private static boolean isFailureEvent(EventType eventType) {
        return eventType == EventType.AUTH_LOGIN_FAILURE
                || eventType == EventType.AUTHZ_ACCESS_DENIED
                || eventType == EventType.CRYPTO_DECRYPT_FAILURE
                || eventType == EventType.CRYPTO_ENCRYPT_FAILURE
                || eventType == EventType.RATE_LIMIT_EXCEEDED;
    }
}
