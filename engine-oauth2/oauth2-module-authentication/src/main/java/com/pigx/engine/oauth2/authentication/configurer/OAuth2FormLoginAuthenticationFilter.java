package com.pigx.engine.oauth2.authentication.configurer;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.oauth2.authentication.provider.OAuth2FormLoginAuthenticationToken;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/configurer/OAuth2FormLoginAuthenticationFilter.class */
public class OAuth2FormLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(OAuth2FormLoginAuthenticationFilter.class);
    private final HttpCryptoProcessor httpCryptoProcessor;
    private boolean postOnly;

    public OAuth2FormLoginAuthenticationFilter(AuthenticationManager authenticationManager, HttpCryptoProcessor httpCryptoProcessor) {
        super(authenticationManager);
        this.postOnly = true;
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.AuthenticationServiceException */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationServiceException, AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        OAuth2FormLoginAuthenticationToken authRequest = getAuthenticationToken(request);
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }

    private OAuth2FormLoginAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String sessionId = SessionUtils.analyseSessionId(request);
        if (StringUtils.isBlank(username)) {
            username = SymbolConstants.BLANK;
        }
        if (StringUtils.isBlank(password)) {
            password = SymbolConstants.BLANK;
        }
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            username = this.httpCryptoProcessor.decrypt(sessionId, username);
            password = this.httpCryptoProcessor.decrypt(sessionId, password);
            log.debug("[Herodotus] |- Decrypt Username is : [{}], Password is : [{}]", username, password);
        }
        return new OAuth2FormLoginAuthenticationToken(username, password);
    }

    public void setPostOnly(boolean postOnly) {
        super.setPostOnly(postOnly);
        this.postOnly = postOnly;
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        getRememberMeServices().loginFail(request, response);
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
