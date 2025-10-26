package com.pigx.engine.autoconfigure.oauth2.servlet;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/servlet/WebPathUtils.class */
public class WebPathUtils {
    public static RequestMatcher toRequestMatcher(String path) {
        return PathPatternRequestMatcher.withDefaults().matcher(path);
    }

    public static RequestMatcher[] toRequestMatchers(List<String> paths) {
        if (CollectionUtils.isNotEmpty(paths)) {
            List<PathPatternRequestMatcher> matchers = paths.stream().map(item -> {
                return PathPatternRequestMatcher.withDefaults().matcher(item);
            }).toList();
            RequestMatcher[] result = new RequestMatcher[matchers.size()];
            return (RequestMatcher[]) matchers.toArray(result);
        }
        return new RequestMatcher[0];
    }

    public static boolean isRequestMatched(RequestMatcher[] matchers, HttpServletRequest request) {
        for (RequestMatcher matcher : matchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRequestMatched(List<String> paths, HttpServletRequest request) {
        RequestMatcher[] matchers = toRequestMatchers(paths);
        return isRequestMatched(matchers, request);
    }
}
