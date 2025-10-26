package com.pigx.engine.web.core.servlet.utils;

import com.pigx.engine.web.core.definition.utils.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/servlet/utils/RequestUtils.class */
public class RequestUtils {
    public static boolean isHtml(HttpServletRequest request) {
        String accept = HeaderUtils.getAccept(request);
        String contentType = HeaderUtils.getContentType(request);
        return HttpUtils.isHtml(accept, contentType).booleanValue();
    }
}
