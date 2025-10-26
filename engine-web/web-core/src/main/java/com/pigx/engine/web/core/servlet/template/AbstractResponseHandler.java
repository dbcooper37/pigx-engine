package com.pigx.engine.web.core.servlet.template;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.web.core.servlet.utils.RequestUtils;
import com.pigx.engine.web.core.servlet.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/servlet/template/AbstractResponseHandler.class */
public abstract class AbstractResponseHandler {
    private final ThymeleafTemplateHandler templateHandler;

    protected AbstractResponseHandler(ThymeleafTemplateHandler templateHandler) {
        this.templateHandler = templateHandler;
    }

    protected void process(HttpServletRequest request, HttpServletResponse response, Supplier<Result<String>> supplier) {
        Result<String> result = supplier.get();
        if (RequestUtils.isHtml(request)) {
            String content = this.templateHandler.renderToError(request, response, result);
            if (StringUtils.isNotBlank(content)) {
                ResponseUtils.renderHtml(response, result.getStatus(), content);
                return;
            } else {
                ResponseUtils.renderResult(response, result);
                return;
            }
        }
        ResponseUtils.renderResult(response, result);
    }
}
