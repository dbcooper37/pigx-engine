package com.pigx.engine.oauth2.authentication.configurer;

import com.pigx.engine.oauth2.core.domain.FormLoginWebAuthenticationDetails;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;


public class OAuth2FormLoginWebAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, FormLoginWebAuthenticationDetails> {

    private final OAuth2AuthenticationProperties authenticationProperties;
    private final HttpCryptoProcessor httpCryptoProcessor;

    public OAuth2FormLoginWebAuthenticationDetailSource(OAuth2AuthenticationProperties authenticationProperties, HttpCryptoProcessor httpCryptoProcessor) {
        this.authenticationProperties = authenticationProperties;
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public FormLoginWebAuthenticationDetails buildDetails(HttpServletRequest request) {

        String encryptedCode = request.getParameter(authenticationProperties.getFormLogin().getCaptchaParameter());

        String sessionId = SessionUtils.analyseSessionId(request);

        String code = null;
        if (StringUtils.isNotBlank(sessionId) && StringUtils.isNotBlank(encryptedCode)) {
            code = httpCryptoProcessor.decrypt(sessionId, encryptedCode);
        }

        return new FormLoginWebAuthenticationDetails(request, authenticationProperties.getFormLogin().getCaptchaEnabled(), authenticationProperties.getFormLogin().getCaptchaParameter(), authenticationProperties.getFormLogin().getCategory(), code);
    }
}
