package com.pigx.engine.web.core.servlet.template;

import com.pigx.engine.core.definition.domain.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/servlet/template/ThymeleafTemplateHandler.class */
public class ThymeleafTemplateHandler {
    private final SpringTemplateEngine springTemplateEngine;

    public ThymeleafTemplateHandler(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    public String renderToError(HttpServletRequest request, HttpServletResponse response, Result<String> result) {
        return render(request, response, "error", result.toErrorModel());
    }

    public String render(String template, Map<String, Object> model) {
        Context context = new Context();
        context.setVariables(model);
        return this.springTemplateEngine.process(template, context);
    }

    public String render(HttpServletRequest request, HttpServletResponse response, String template, Map<String, Object> model) {
        WebContext context = new WebContext(JakartaServletWebApplication.buildApplication(request.getServletContext()).buildExchange(request, response));
        context.setVariables(model);
        return this.springTemplateEngine.process(template, context);
    }
}
