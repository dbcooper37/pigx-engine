package com.pigx.engine.oauth2.authentication.response;

import com.pigx.engine.core.autoconfigure.oauth2.definition.SecurityGlobalExceptionHandler;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/response/OAuth2FormLoginAuthenticationFailureHandler.class */
public class OAuth2FormLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(OAuth2FormLoginAuthenticationFailureHandler.class);
    private String defaultFailureUrl;
    private boolean forwardToDestination = false;
    private boolean allowSessionCreation = true;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public OAuth2FormLoginAuthenticationFailureHandler() {
    }

    public OAuth2FormLoginAuthenticationFailureHandler(String defaultFailureUrl) {
        setDefaultFailureUrl(defaultFailureUrl);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String errorMessage;
        if (this.defaultFailureUrl == null) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Sending 401 Unauthorized error since no failure URL is set");
            } else {
                this.logger.debug("Sending 401 Unauthorized error");
            }
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return;
        }
        Result<String> result = SecurityGlobalExceptionHandler.resolveSecurityException(e, request.getRequestURI());
        if (ObjectUtils.isNotEmpty(result) && StringUtils.isNotBlank(result.getMessage())) {
            errorMessage = result.getMessage();
        } else {
            errorMessage = e.getClass().getSimpleName();
            log.warn("[Herodotus] |- Form Login Authentication Failure Handler,  Can not find the exception name [{}] in dictionary, please do optimize ", errorMessage);
        }
        saveException(request, errorMessage);
        if (isUseForward()) {
            log.debug("Forwarding to " + this.defaultFailureUrl);
            request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
        } else {
            this.redirectStrategy.sendRedirect(request, response, this.defaultFailureUrl);
        }
    }

    protected final void saveException(HttpServletRequest request, String message) {
        if (isUseForward()) {
            request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", message);
            return;
        }
        HttpSession session = SessionUtils.getSession(request);
        if (session != null || isAllowSessionCreation()) {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", message);
        }
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), () -> {
            return "'" + defaultFailureUrl + "' is not a valid redirect URL";
        });
        this.defaultFailureUrl = defaultFailureUrl;
    }

    protected boolean isUseForward() {
        return this.forwardToDestination;
    }

    public void setUseForward(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return this.redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected boolean isAllowSessionCreation() {
        return this.allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }
}
