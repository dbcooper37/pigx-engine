package com.pigx.engine.web.core.definition.utils;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.http.HttpMethod;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/definition/utils/HttpUtils.class */
public class HttpUtils {
    private static Map<String, String> rawCookieToMap(String cookie) {
        if (StringUtils.isNotBlank(cookie)) {
            return (Map) Stream.of((Object[]) cookie.split(SymbolConstants.SEMICOLON_AND_SPACE)).map(pair -> {
                return pair.split(SymbolConstants.EQUAL);
            }).collect(Collectors.toMap(kv -> {
                return kv[0];
            }, kv2 -> {
                return kv2[1];
            }));
        }
        return Collections.emptyMap();
    }

    public static List<String> get(String cookie, String... name) {
        Map<String, String> cookies = rawCookieToMap(cookie);
        Stream streamOf = Stream.of((Object[]) name);
        Objects.requireNonNull(cookies);
        return streamOf.map((v1) -> {
            return r1.get(v1);
        }).toList();
    }

    public static String getAny(String cookie, String... name) {
        List<String> result = get(cookie, name);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        }
        return null;
    }

    public static String get(String cookie, String name) {
        Map<String, String> cookies = rawCookieToMap(cookie);
        return cookies.get(name);
    }

    public static boolean isGetRequest(HttpMethod method) {
        return method == HttpMethod.GET;
    }

    public static Boolean isPostRequest(HttpMethod method, String contentType) {
        return Boolean.valueOf((method == HttpMethod.POST || method == HttpMethod.PUT) && (Strings.CI.equals("application/x-www-form-urlencoded", contentType) || "application/json".equals(contentType)));
    }

    public static boolean isJson(String contentType) {
        return Strings.CI.equals("application/json", contentType) || Strings.CI.equals("application/json;charset=UTF-8", contentType);
    }

    public static boolean isFormUrlencoded(String contentType) {
        return Strings.CI.equals("application/x-www-form-urlencoded", contentType);
    }

    public static boolean isGetTypeRequest(HttpMethod method) {
        return method == HttpMethod.GET || method == HttpMethod.DELETE;
    }

    public static Boolean isPostTypeRequest(HttpMethod method, String contentType) {
        return Boolean.valueOf((method == HttpMethod.POST || method == HttpMethod.PUT) && (isFormUrlencoded(contentType) || isJson(contentType)));
    }

    public static Boolean isHtml(String accept, String contentType) {
        if (StringUtils.isNotBlank(contentType) && Strings.CI.equals("text/html", contentType)) {
            return true;
        }
        return Boolean.valueOf(Strings.CS.containsAny(accept, new CharSequence[]{"text/html", "application/xhtml+xml"}));
    }
}
