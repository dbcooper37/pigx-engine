package com.pigx.engine.oauth2.extension.converter;

import cn.hutool.v7.http.useragent.UserAgent;
import cn.hutool.v7.http.useragent.UserAgentUtil;
import com.google.common.net.HttpHeaders;
import com.pigx.engine.core.identity.utils.SecurityUtils;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;
import com.pigx.engine.web.core.servlet.utils.HeaderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;


public class RequestToUserLoggingConverter implements Converter<HttpServletRequest, OAuth2UserLogging> {

    private final String principal;
    private final String clientId;
    private final String operation;

    public RequestToUserLoggingConverter(OAuth2AccessTokenAuthenticationToken token) {
        this(SecurityUtils.getUsername(token), token.getRegisteredClient().getId(), "登录系统");
    }

    public RequestToUserLoggingConverter(OAuth2Authorization authorization) {
        this(authorization.getPrincipalName(), authorization.getRegisteredClientId(), "退出系统");
    }

    private RequestToUserLoggingConverter(String principal, String clientId, String operation) {
        this.principal = principal;
        this.clientId = clientId;
        this.operation = operation;
    }

    @Override
    public OAuth2UserLogging convert(HttpServletRequest source) {

        OAuth2UserLogging target = new OAuth2UserLogging();
        target.setPrincipalName(principal);
        target.setClientId(clientId);
        target.setIp(HeaderUtils.getIp(source));
        target.setOperation(operation);

        withUserAgent(target, source);

        return target;
    }

    private void withUserAgent(OAuth2UserLogging target, HttpServletRequest source) {
        UserAgent userAgent = UserAgentUtil.parse(source.getHeader(HttpHeaders.USER_AGENT));
        if (ObjectUtils.isNotEmpty(userAgent)) {
            target.setMobile(userAgent.isMobile());
            target.setOsName(userAgent.getOs().getName());
            target.setBrowserName(userAgent.getBrowser().getName());
            target.setMobileBrowser(userAgent.getBrowser().isMobile());
            target.setEngineName(userAgent.getEngine().getName());
            target.setMobilePlatform(userAgent.getPlatform().isMobile());
            target.setIphoneOrIpod(userAgent.getPlatform().isIPhoneOrIPod());
            target.setIpad(userAgent.getPlatform().isIPad());
            target.setIos(userAgent.getPlatform().isIos());
            target.setAndroid(userAgent.getPlatform().isAndroid());
        }
    }
}
