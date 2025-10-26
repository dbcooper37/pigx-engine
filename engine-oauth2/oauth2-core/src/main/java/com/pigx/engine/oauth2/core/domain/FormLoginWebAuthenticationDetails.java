package com.pigx.engine.oauth2.core.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/domain/FormLoginWebAuthenticationDetails.class */
public class FormLoginWebAuthenticationDetails extends WebAuthenticationDetails {
    private final Boolean enabled;
    private final String parameterName;
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
        return this.enabled;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCode() {
        return this.code;
    }

    public String getParameterName() {
        return this.parameterName;
    }
}
