package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.SecretKey;
import com.pigx.engine.core.definition.enums.Protocol;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import cn.hutool.v7.core.codec.binary.Base64;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

@Controller
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OAuth2SignInController.class */
public class OAuth2SignInController {
    private final OAuth2AuthenticationProperties authenticationProperties;
    private final SessionProperties sessionProperties;
    private final HttpCryptoProcessor httpCryptoProcessor;

    public OAuth2SignInController(OAuth2AuthenticationProperties authenticationProperties, SessionProperties sessionProperties, HttpCryptoProcessor httpCryptoProcessor) {
        this.authenticationProperties = authenticationProperties;
        this.sessionProperties = sessionProperties;
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public ModelAndView login(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");
        boolean loginError = isErrorPage(request);
        boolean logoutSuccess = isLogoutSuccess(request);
        String errorMessage = getErrorMessage(request);
        modelAndView.addObject("vulgar_tycoon", getFormLogin().getUsernameParameter());
        modelAndView.addObject("beast", getFormLogin().getPasswordParameter());
        modelAndView.addObject("anubis", getFormLogin().getRememberMeParameter());
        modelAndView.addObject("graphic", getFormLogin().getCaptchaParameter());
        modelAndView.addObject("show_verification_code", getFormLogin().getCaptchaEnabled());
        modelAndView.addObject("forgot_password_url", getFormLogin().getForgotPasswordUrl());
        modelAndView.addObject("registration_ur", getFormLogin().getRegistrationUrl());
        modelAndView.addObject("show_registration_url", getFormLogin().isRegistrationEnabled());
        modelAndView.addObject("show_forgot_password_url", getFormLogin().isForgotPasswordEnabled());
        modelAndView.addObject("verification_category", getFormLogin().getCategory());
        modelAndView.addObject("login_error", Boolean.valueOf(loginError));
        modelAndView.addObject("logout_success", Boolean.valueOf(logoutSuccess));
        modelAndView.addObject("message", StringUtils.isNotBlank(errorMessage) ? HtmlUtils.htmlEscape(errorMessage) : null);
        modelAndView.addObject("contextPath", request.getContextPath());
        processSecretKey(modelAndView, request, response);
        return modelAndView;
    }

    public void processSecretKey(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        SecretKey secretKey;
        String sessionId;
        String sessionId2 = SessionUtils.analyseSessionId(request);
        if (StringUtils.isBlank(sessionId2)) {
            secretKey = this.httpCryptoProcessor.createSecretKey(null, this.sessionProperties.getTimeout());
            sessionId = secretKey.getIdentity();
            ResponseCookie cookie = ResponseCookie.from("SESSION", Base64.encode(secretKey.getIdentity())).path(request.getContextPath() + "/").httpOnly(true).secure(Strings.CI.equals(Protocol.HTTPS.getPrefix(), request.getScheme())).sameSite("Lax").maxAge(this.authenticationProperties.getFormLogin().getCookieMaxAge()).build();
            response.setHeader("Set-Cookie", cookie.toString());
        } else {
            secretKey = this.httpCryptoProcessor.createSecretKey(sessionId2, this.sessionProperties.getTimeout());
            sessionId = secretKey.getIdentity();
        }
        modelAndView.addObject("soup_spoon", secretKey.getPublicKey());
        modelAndView.addObject("sessionId", sessionId);
    }

    private OAuth2AuthenticationProperties.FormLogin getFormLogin() {
        return this.authenticationProperties.getFormLogin();
    }

    private boolean isLogoutSuccess(HttpServletRequest request) {
        String logoutSuccessUrl = getFormLogin().getLogoutSuccessUrl();
        return StringUtils.isNotBlank(logoutSuccessUrl) && matches(request, logoutSuccessUrl);
    }

    private boolean isErrorPage(HttpServletRequest request) {
        return matches(request, getFormLogin().getFailureUrl());
    }

    private String getErrorMessage(HttpServletRequest request) {
        HttpSession session = SessionUtils.getSession(request);
        if (ObjectUtils.isNotEmpty(session)) {
            String message = (String) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (ObjectUtils.isNotEmpty(message)) {
                return message;
            }
            return null;
        }
        return null;
    }

    private boolean matches(HttpServletRequest request, String url) {
        if (!"GET".equals(request.getMethod()) || url == null) {
            return false;
        }
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(59);
        if (pathParamIndex > 0) {
            uri = uri.substring(0, pathParamIndex);
        }
        if (request.getQueryString() != null) {
            uri = uri + "?" + request.getQueryString();
        }
        if (SymbolConstants.BLANK.equals(request.getContextPath())) {
            return uri.equals(url);
        }
        return uri.equals(request.getContextPath() + url);
    }
}
