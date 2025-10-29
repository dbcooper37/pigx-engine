package com.pigx.engine.web.core.servlet.utils;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class ResponseUtils {

    private static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * 将内容写入到响应
     *
     * @param response    响应 {@link HttpServletResponse}
     * @param statusCode  状态码
     * @param content     待写入的内容
     * @param contentType 内容类型
     */
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
            log.error("[PIGXD] |- Render response error!");
        }
    }

    /**
     * 将 JSON 写入到响应。
     *
     * @param response   响应 {@link HttpServletResponse}
     * @param statusCode 状态码
     * @param content    待写入的内容
     */
    public static void renderJson(HttpServletResponse response, int statusCode, String content) {
        render(response, statusCode, content, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 将 JSON 写入到响应。
     *
     * @param response   响应 {@link HttpServletResponse}
     * @param statusCode 状态码
     * @param object     待写入的内容
     */
    public static void renderJson(HttpServletResponse response, int statusCode, Object object) {
        renderJson(response, statusCode, Jackson2Utils.toJson(object));
    }

    /**
     * 将 Result 以 JSON 格式输出到响应。
     *
     * @param response 响应 {@link HttpServletResponse}
     * @param result   待写入的内容 {@link Result}
     */
    public static void renderResult(HttpServletResponse response, Result<String> result) {
        renderJson(response, result.getStatus(), result);
    }

    /**
     * 将 HTML 写入到响应。
     *
     * @param response   响应 {@link HttpServletResponse}
     * @param statusCode 状态码
     * @param content    待写入的内容
     */
    public static void renderHtml(HttpServletResponse response, int statusCode, String content) {
        render(response, statusCode, content, MediaType.TEXT_HTML_VALUE);
    }
}
