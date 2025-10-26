package com.pigx.engine.web.core.servlet.utils;

import com.pigx.engine.core.definition.constant.HerodotusHeaders;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/servlet/utils/HeaderUtils.class */
public class HeaderUtils {
    public static List<String> getHeaders(HttpHeaders httpHeaders, String name) {
        return httpHeaders.get(name);
    }

    public static List<String> getHeaders(ServerHttpRequest serverHttpRequest, String name) {
        return getHeaders(serverHttpRequest.getHeaders(), name);
    }

    public static String getHeader(HttpHeaders httpHeaders, String name) {
        List<String> values = getHeaders(httpHeaders, name);
        if (CollectionUtils.isNotEmpty(values)) {
            return values.get(0);
        }
        return null;
    }

    public static String getHeader(ServerHttpRequest serverHttpRequest, String name) {
        return getHeader(serverHttpRequest.getHeaders(), name);
    }

    public static String getHeader(HttpServletRequest httpServletRequest, String name) {
        return httpServletRequest.getHeader(name);
    }

    public static boolean hasHeader(HttpHeaders httpHeaders, String name) {
        return httpHeaders.containsKey(name);
    }

    public static Boolean hasHeader(HttpServletRequest httpServletRequest, String name) {
        return Boolean.valueOf(StringUtils.isNotBlank(getHeader(httpServletRequest, name)));
    }

    public static Boolean hasHeader(ServerHttpRequest serverHttpRequest, String name) {
        return Boolean.valueOf(hasHeader(serverHttpRequest.getHeaders(), name));
    }

    public static String getHerodotusSessionId(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, HerodotusHeaders.X_HERODOTUS_SESSION_ID);
    }

    public static String getHerodotusSessionId(ServerHttpRequest serverHttpRequest) {
        return getHeader(serverHttpRequest, HerodotusHeaders.X_HERODOTUS_SESSION_ID);
    }

    public static String getHerodotusSessionId(HttpInputMessage httpInputMessage) {
        return getHeader(httpInputMessage.getHeaders(), HerodotusHeaders.X_HERODOTUS_SESSION_ID);
    }

    public static String getHerodotusTenantId(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, HerodotusHeaders.X_HERODOTUS_TENANT_ID);
    }

    public static String getHerodotusFromIn(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, HerodotusHeaders.X_HERODOTUS_FROM_IN);
    }

    public static boolean hasHerodotusSessionIdHeader(HttpServletRequest httpServletRequest) {
        return hasHeader(httpServletRequest, HerodotusHeaders.X_HERODOTUS_SESSION_ID).booleanValue();
    }

    public static boolean hasHerodotusSessionIdHeader(ServerHttpRequest serverHttpRequest) {
        return hasHeader(serverHttpRequest, HerodotusHeaders.X_HERODOTUS_SESSION_ID).booleanValue();
    }

    public static boolean hasHerodotusSessionIdHeader(HttpInputMessage httpInputMessage) {
        return hasHeader(httpInputMessage.getHeaders(), HerodotusHeaders.X_HERODOTUS_SESSION_ID);
    }

    public static String getCookie(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, "Cookie");
    }

    public static String getCookie(ServerHttpRequest serverHttpRequest) {
        return getHeader(serverHttpRequest, "Cookie");
    }

    public static String getCookie(HttpInputMessage httpInputMessage) {
        return getHeader(httpInputMessage.getHeaders(), "Cookie");
    }

    public static String getAuthorization(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, "Authorization");
    }

    public static String getBearerToken(HttpServletRequest request) {
        String header = getAuthorization(request);
        if (StringUtils.isNotBlank(header) && Strings.CS.startsWith(header, SystemConstants.BEARER_TOKEN)) {
            return Strings.CS.remove(header, SystemConstants.BEARER_TOKEN);
        }
        return null;
    }

    public static String getOrigin(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, "Origin");
    }

    public static String getAccept(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, "Accept");
    }

    public static String getContentType(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, "Content-Type");
    }

    public static String getIp(HttpServletRequest httpServletRequest) {
        String ip = ServletUtil.getClientIP(httpServletRequest, new String[]{SymbolConstants.BLANK});
        if (Strings.CS.equals(ip, "0:0:0:0:0:0:0:1")) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return "127.0.0.1";
            }
        }
        return ip;
    }
}
