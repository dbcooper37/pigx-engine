package com.pigx.engine.web.servlet.secure;

import com.pigx.engine.core.foundation.utils.XssUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/secure/XssHttpServletRequestWrapper.class */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private String cleaning(String value) {
        return XssUtils.process(value);
    }

    private String[] cleaning(String[] parameters) {
        List<String> cleanParameters = Arrays.stream(parameters).map(XssUtils::process).toList();
        String[] results = new String[cleanParameters.size()];
        return (String[]) cleanParameters.toArray(results);
    }

    public String getHeader(String name) {
        String header = super.getHeader(name);
        return StringUtils.isBlank(header) ? header : cleaning(header);
    }

    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        return StringUtils.isBlank(parameter) ? parameter : cleaning(parameter);
    }

    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (ArrayUtils.isNotEmpty(parameterValues)) {
            return cleaning(parameterValues);
        }
        return super.getParameterValues(name);
    }

    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        return (Map) parameterMap.entrySet().stream().collect(Collectors.toMap((v0) -> {
            return v0.getKey();
        }, entry -> {
            return cleaning((String[]) entry.getValue());
        }));
    }
}
