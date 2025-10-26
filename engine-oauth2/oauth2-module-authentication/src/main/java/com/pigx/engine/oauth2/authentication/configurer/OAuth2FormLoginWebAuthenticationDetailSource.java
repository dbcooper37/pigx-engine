package com.pigx.engine.oauth2.authentication.configurer;

import com.pigx.engine.oauth2.core.domain.FormLoginWebAuthenticationDetails;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/configurer/OAuth2FormLoginWebAuthenticationDetailSource.class */
public class OAuth2FormLoginWebAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, FormLoginWebAuthenticationDetails> {
    private final OAuth2AuthenticationProperties authenticationProperties;
    private final HttpCryptoProcessor httpCryptoProcessor;

    public OAuth2FormLoginWebAuthenticationDetailSource(OAuth2AuthenticationProperties authenticationProperties, HttpCryptoProcessor httpCryptoProcessor) {
        this.authenticationProperties = authenticationProperties;
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    public FormLoginWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        String encryptedCode = request.getParameter(this.authenticationProperties.getFormLogin().getCaptchaParameter());
        String sessionId = SessionUtils.analyseSessionId(request);
        String code = null;
        if (StringUtils.isNotBlank(sessionId) && StringUtils.isNotBlank(encryptedCode)) {
            code = this.httpCryptoProcessor.decrypt(sessionId, encryptedCode);
        }
        return new FormLoginWebAuthenticationDetails(request, this.authenticationProperties.getFormLogin().getCaptchaEnabled(), this.authenticationProperties.getFormLogin().getCaptchaParameter(), this.authenticationProperties.getFormLogin().getCategory(), code);
    }
}
