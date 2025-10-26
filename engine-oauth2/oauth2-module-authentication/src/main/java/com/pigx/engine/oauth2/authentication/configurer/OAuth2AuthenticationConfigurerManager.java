package com.pigx.engine.oauth2.authentication.configurer;

import com.pigx.engine.oauth2.authentication.customizer.OAuth2ExceptionHandlingConfigurerCustomizer;
import com.pigx.engine.oauth2.authentication.customizer.OAuth2FormLoginConfigurerCustomizer;
import com.pigx.engine.oauth2.authentication.response.OAuth2AccessTokenResponseHandler;
import com.pigx.engine.oauth2.authentication.response.OAuth2AuthenticationFailureHandler;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/configurer/OAuth2AuthenticationConfigurerManager.class */
public class OAuth2AuthenticationConfigurerManager {
    private final HttpCryptoProcessor httpCryptoProcessor;
    private final OAuth2AuthenticationProperties oauth2AuthenticationProperties;
    private final OAuth2FormLoginConfigurerCustomizer oauth2FormLoginConfigurerCustomizer;
    private final OAuth2ExceptionHandlingConfigurerCustomizer oauth2ExceptionHandlingConfigurerCustomizer;
    private final OAuth2AccessTokenResponseHandler oauth2AccessTokenResponseHandler;
    private final OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;

    public OAuth2AuthenticationConfigurerManager(ThymeleafTemplateHandler thymeleafTemplateHandler, HttpCryptoProcessor httpCryptoProcessor, OAuth2AuthenticationProperties oauth2AuthenticationProperties) {
        this.httpCryptoProcessor = httpCryptoProcessor;
        this.oauth2AuthenticationProperties = oauth2AuthenticationProperties;
        this.oauth2FormLoginConfigurerCustomizer = new OAuth2FormLoginConfigurerCustomizer(oauth2AuthenticationProperties);
        this.oauth2ExceptionHandlingConfigurerCustomizer = new OAuth2ExceptionHandlingConfigurerCustomizer(oauth2AuthenticationProperties);
        this.oauth2AccessTokenResponseHandler = new OAuth2AccessTokenResponseHandler(httpCryptoProcessor);
        this.oauth2AuthenticationFailureHandler = new OAuth2AuthenticationFailureHandler(thymeleafTemplateHandler);
    }

    public HttpCryptoProcessor getHttpCryptoProcessor() {
        return this.httpCryptoProcessor;
    }

    public OAuth2AuthenticationProperties getOAuth2AuthenticationProperties() {
        return this.oauth2AuthenticationProperties;
    }

    public OAuth2FormLoginConfigurerCustomizer getOAuth2FormLoginConfigurerCustomizer() {
        return this.oauth2FormLoginConfigurerCustomizer;
    }

    public OAuth2ExceptionHandlingConfigurerCustomizer getOAuth2ExceptionHandlingConfigurerCustomizer() {
        return this.oauth2ExceptionHandlingConfigurerCustomizer;
    }

    public OAuth2AccessTokenResponseHandler getOAuth2AccessTokenResponseHandler() {
        return this.oauth2AccessTokenResponseHandler;
    }

    public OAuth2AuthenticationFailureHandler getOAuth2AuthenticationFailureHandler() {
        return this.oauth2AuthenticationFailureHandler;
    }
}
