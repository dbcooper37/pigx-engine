package com.pigx.engine.web.core.servlet.utils;

import com.pigx.engine.web.core.definition.utils.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;


public class RequestUtils {

    /**
     * 判断请求是否为 HTML 类型
     *
     * @param request 请求对象 {@link HttpServletRequest}
     * @return true 请求体数据类型为 html，false 请求体数据类型不是 html
     */
    public static boolean isHtml(HttpServletRequest request) {
        String accept = HeaderUtils.getAccept(request);
        String contentType = HeaderUtils.getContentType(request);
        return HttpUtils.isHtml(accept, contentType);
    }
}
