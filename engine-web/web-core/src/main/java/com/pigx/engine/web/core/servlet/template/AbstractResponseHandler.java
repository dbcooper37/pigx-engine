package com.pigx.engine.web.core.servlet.template;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.web.core.servlet.utils.RequestUtils;
import com.pigx.engine.web.core.servlet.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Supplier;


public abstract class AbstractResponseHandler {

    private final ThymeleafTemplateHandler templateHandler;

    protected AbstractResponseHandler(ThymeleafTemplateHandler templateHandler) {
        this.templateHandler = templateHandler;
    }

    protected void process(HttpServletRequest request, HttpServletResponse response, Supplier<Result<String>> supplier) {

        Result<String> result = supplier.get();

        if (RequestUtils.isHtml(request)) {
            String content = templateHandler.renderToError(request, response, result);
            if (StringUtils.isNotBlank(content)) {
                ResponseUtils.renderHtml(response, result.getStatus(), content);
            } else {
                // 主要防止 Thymeleaf 模版转换有异常，做一项保护。
                ResponseUtils.renderResult(response, result);
            }
        } else {
            ResponseUtils.renderResult(response, result);
        }
    }
}
