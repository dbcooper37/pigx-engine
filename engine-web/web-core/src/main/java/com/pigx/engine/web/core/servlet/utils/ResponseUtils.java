package com.pigx.engine.web.core.servlet.utils;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/servlet/utils/ResponseUtils.class */
public class ResponseUtils {
    private static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

    public static void render(HttpServletResponse response, int statusCode, String content, String contentType) {
        try {
            response.setStatus(statusCode);
            response.setContentType(contentType);
            response.setContentType(contentType);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(content);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("[Herodotus] |- Render response error!");
        }
    }

    public static void renderJson(HttpServletResponse response, int statusCode, String content) {
        render(response, statusCode, content, "application/json");
    }

    public static void renderJson(HttpServletResponse response, int statusCode, Object object) {
        renderJson(response, statusCode, Jackson2Utils.toJson(object));
    }

    public static void renderResult(HttpServletResponse response, Result<String> result) {
        renderJson(response, result.getStatus(), result);
    }

    public static void renderHtml(HttpServletResponse response, int statusCode, String content) {
        render(response, statusCode, content, "text/html");
    }
}
