package com.pigx.engine.web.core.servlet.utils;

import com.pigx.engine.web.core.definition.utils.HttpUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.util.WebUtils;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/servlet/utils/CookieUtils.class */
public class CookieUtils extends HttpUtils {
    public static Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
        return WebUtils.getCookie(httpServletRequest, name);
    }

    public static String get(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = getCookie(httpServletRequest, name);
        if (ObjectUtils.isNotEmpty(cookie)) {
            return cookie.getValue();
        }
        return null;
    }

    public static String getFromCookieHeader(HttpServletRequest httpServletRequest, String name) {
        String cookie = HeaderUtils.getCookie(httpServletRequest);
        return get(cookie, name);
    }

    public static String getFromCookieHeader(ServerHttpRequest serverHttpRequest, String name) {
        String cookie = HeaderUtils.getCookie(serverHttpRequest);
        return get(cookie, name);
    }

    public static String getFromCookieHeader(HttpInputMessage httpInputMessage, String name) {
        String cookie = HeaderUtils.getCookie(httpInputMessage);
        return get(cookie, name);
    }

    public static String getAnyFromCookieHeader(HttpServletRequest httpServletRequest, String... name) {
        String cookie = HeaderUtils.getCookie(httpServletRequest);
        return getAny(cookie, name);
    }

    public static String getAnyFromCookieHeader(ServerHttpRequest serverHttpRequest, String... name) {
        String cookie = HeaderUtils.getCookie(serverHttpRequest);
        return getAny(cookie, name);
    }

    public static String getAnyFromCookieHeader(HttpInputMessage httpInputMessage, String... name) {
        String cookie = HeaderUtils.getCookie(httpInputMessage);
        return getAny(cookie, name);
    }

    public static void remove(HttpServletResponse response, String key) {
        set(response, key, null, 0);
    }

    public static void set(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
