package com.pigx.engine.core.autoconfigure.oauth2.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;


public class WebPathUtils {

    /**
     * 将配置路径转换为 {@link RequestMatcher} 数组
     *
     * @param path 静态请求路径
     * @return {@link RequestMatcher} 数组
     */
    public static RequestMatcher toRequestMatcher(String path) {
        return PathPatternRequestMatcher.withDefaults().matcher(path);
    }

    /**
     * 将配置路径转换为 {@link RequestMatcher} 数组
     *
     * @param paths 静态请求路径
     * @return {@link RequestMatcher} 数组
     */
    public static RequestMatcher[] toRequestMatchers(List<String> paths) {
        if (CollectionUtils.isNotEmpty(paths)) {
            List<PathPatternRequestMatcher> matchers = paths.stream().map(item -> PathPatternRequestMatcher.withDefaults().matcher(item)).toList();
            RequestMatcher[] result = new RequestMatcher[matchers.size()];
            return matchers.toArray(result);
        } else {
            return new RequestMatcher[]{};
        }
    }

    /**
     * 判断请求是否与设定的模式匹配
     *
     * @param matchers 路径匹配模式
     * @param request  请求
     * @return 是否匹配
     */
    public static boolean isRequestMatched(RequestMatcher[] matchers, HttpServletRequest request) {
        for (RequestMatcher matcher : matchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断请求是否与设定的模式匹配
     *
     * @param paths   路径
     * @param request 请求
     * @return 是否匹配
     */
    public static boolean isRequestMatched(List<String> paths, HttpServletRequest request) {
        RequestMatcher[] matchers = toRequestMatchers(paths);
        return isRequestMatched(matchers, request);
    }
}
