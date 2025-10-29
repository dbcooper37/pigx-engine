package com.pigx.engine.oauth2.core.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


public class FormLoginWebAuthenticationDetails extends WebAuthenticationDetails {

    /**
     * 验证码是否关闭
     */
    private final Boolean enabled;
    /**
     * 请求中，验证码对应的表单参数名。对应UI Properties 里面的 captcha parameter
     */
    private final String parameterName;
    /**
     * 验证码分类
     */
    private final String category;
    private final String code;

    public FormLoginWebAuthenticationDetails(String remoteAddress, String sessionId, Boolean enabled, String parameterName, String category, String code) {
        super(remoteAddress, sessionId);
        this.enabled = enabled;
        this.parameterName = parameterName;
        this.category = category;
        this.code = code;
    }

    public FormLoginWebAuthenticationDetails(HttpServletRequest request, Boolean enabled, String parameterName, String category, String code) {
        super(request);
        this.enabled = enabled;
        this.parameterName = parameterName;
        this.category = category;
        this.code = code;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getCategory() {
        return category;
    }

    public String getCode() {
        return code;
    }

    public String getParameterName() {
        return parameterName;
    }
}
