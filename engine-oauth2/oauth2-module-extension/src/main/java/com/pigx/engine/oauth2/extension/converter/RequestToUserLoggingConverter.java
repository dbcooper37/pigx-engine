package com.pigx.engine.oauth2.extension.converter;

import com.pigx.engine.core.identity.utils.SecurityUtils;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;
import com.pigx.engine.web.core.servlet.utils.HeaderUtils;
import cn.hutool.v7.http.useragent.UserAgent;
import cn.hutool.v7.http.useragent.UserAgentUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;

/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/converter/RequestToUserLoggingConverter.class */
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

    public OAuth2UserLogging convert(HttpServletRequest source) {
        OAuth2UserLogging target = new OAuth2UserLogging();
        target.setPrincipalName(this.principal);
        target.setClientId(this.clientId);
        target.setIp(HeaderUtils.getIp(source));
        target.setOperation(this.operation);
        withUserAgent(target, source);
        return target;
    }

    private void withUserAgent(OAuth2UserLogging target, HttpServletRequest source) {
        UserAgent userAgent = UserAgentUtil.parse(source.getHeader("User-Agent"));
        if (ObjectUtils.isNotEmpty(userAgent)) {
            target.setMobile(Boolean.valueOf(userAgent.isMobile()));
            target.setOsName(userAgent.getOs().getName());
            target.setBrowserName(userAgent.getBrowser().getName());
            target.setMobileBrowser(Boolean.valueOf(userAgent.getBrowser().isMobile()));
            target.setEngineName(userAgent.getEngine().getName());
            target.setMobilePlatform(Boolean.valueOf(userAgent.getPlatform().isMobile()));
            target.setIphoneOrIpod(Boolean.valueOf(userAgent.getPlatform().isIPhoneOrIPod()));
            target.setIpad(Boolean.valueOf(userAgent.getPlatform().isIPad()));
            target.setIos(Boolean.valueOf(userAgent.getPlatform().isIos()));
            target.setAndroid(Boolean.valueOf(userAgent.getPlatform().isAndroid()));
        }
    }
}
