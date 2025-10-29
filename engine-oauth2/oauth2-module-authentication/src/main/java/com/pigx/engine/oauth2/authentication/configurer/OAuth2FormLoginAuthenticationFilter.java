package com.pigx.engine.oauth2.authentication.configurer;

import com.pigx.engine.oauth2.authentication.provider.OAuth2FormLoginAuthenticationToken;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


public class OAuth2FormLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(OAuth2FormLoginAuthenticationFilter.class);

    private final HttpCryptoProcessor httpCryptoProcessor;
    private boolean postOnly = true;

    public OAuth2FormLoginAuthenticationFilter(AuthenticationManager authenticationManager, HttpCryptoProcessor httpCryptoProcessor) {
        super(authenticationManager);
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        OAuth2FormLoginAuthenticationToken authRequest = getAuthenticationToken(request);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private OAuth2FormLoginAuthenticationToken getAuthenticationToken(
            HttpServletRequest request) {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String sessionId = SessionUtils.analyseSessionId(request);

        if (StringUtils.isBlank(username)) {
            username = "";
        }

        if (StringUtils.isBlank(password)) {
            password = "";
        }

        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            username = httpCryptoProcessor.decrypt(sessionId, username);
            password = httpCryptoProcessor.decrypt(sessionId, password);
            log.debug("[PIGXD] |- Decrypt Username is : [{}], Password is : [{}]", username, password);
        }

        return new OAuth2FormLoginAuthenticationToken(username, password);
    }

    @Override
    public void setPostOnly(boolean postOnly) {
        super.setPostOnly(postOnly);
        this.postOnly = postOnly;
    }

    /**
     * 重写该方法，避免在日志Debug级别会输出错误信息的问题。
     *
     * @param request  请求
     * @param response 响应
     * @param failed   失败内容
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        getRememberMeServices().loginFail(request, response);
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
