package com.pigx.engine.web.core.servlet.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/servlet/utils/SessionUtils.class */
public class SessionUtils {
    private static final String[] SESSION_IDS = {"JSESSIONID, SESSION"};

    public static HttpSession getSession(HttpServletRequest httpServletRequest, boolean create) {
        return httpServletRequest.getSession(create);
    }

    public static HttpSession getSession(HttpServletRequest httpServletRequest) {
        return getSession(httpServletRequest, false);
    }

    public static String getSessionId(HttpServletRequest httpServletRequest, boolean create) {
        HttpSession httpSession = getSession(httpServletRequest, create);
        if (ObjectUtils.isNotEmpty(httpSession)) {
            return httpSession.getId();
        }
        return null;
    }

    public static String getSessionId(HttpServletRequest httpServletRequest) {
        return getSessionId(httpServletRequest, false);
    }

    public static String getSessionIdFromHeader(HttpInputMessage httpInputMessage) {
        return CookieUtils.getAnyFromCookieHeader(httpInputMessage, SESSION_IDS);
    }

    public static String getSessionIdFromHeader(ServerHttpRequest serverHttpRequest) {
        return CookieUtils.getAnyFromCookieHeader(serverHttpRequest, SESSION_IDS);
    }

    public static String analyseSessionId(HttpServletRequest httpServletRequest) {
        String sessionId = getSessionId(httpServletRequest);
        if (StringUtils.isBlank(sessionId)) {
            sessionId = HeaderUtils.getHerodotusSessionId(httpServletRequest);
        }
        return sessionId;
    }

    public static String analyseSessionId(ServerHttpRequest serverHttpRequest) {
        String sessionId = getSessionIdFromHeader(serverHttpRequest);
        if (StringUtils.isBlank(sessionId)) {
            sessionId = HeaderUtils.getHerodotusSessionId(serverHttpRequest);
        }
        return sessionId;
    }

    public static String analyseSessionId(HttpInputMessage httpInputMessage) {
        String sessionId = getSessionIdFromHeader(httpInputMessage);
        if (StringUtils.isBlank(sessionId)) {
            sessionId = HeaderUtils.getHerodotusSessionId(httpInputMessage);
        }
        return sessionId;
    }

    public static boolean isCryptoEnabled(HttpServletRequest httpServletRequest, String sessionId) {
        return HeaderUtils.hasHerodotusSessionIdHeader(httpServletRequest) && StringUtils.isNotBlank(sessionId);
    }

    public static boolean isCryptoEnabled(HttpInputMessage httpInputMessage, String sessionId) {
        return HeaderUtils.hasHerodotusSessionIdHeader(httpInputMessage) && StringUtils.isNotBlank(sessionId);
    }
}
