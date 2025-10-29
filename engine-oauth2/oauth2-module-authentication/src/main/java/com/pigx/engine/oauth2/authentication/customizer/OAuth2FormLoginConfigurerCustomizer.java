package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;


public class OAuth2FormLoginConfigurerCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {

    private final OAuth2AuthenticationProperties authenticationProperties;

    public OAuth2FormLoginConfigurerCustomizer(OAuth2AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> configurer) {
        configurer
                .loginPage(getFormLogin().getLoginPageUrl())
                .usernameParameter(getFormLogin().getUsernameParameter())
                .passwordParameter(getFormLogin().getPasswordParameter());

        if (StringUtils.isNotBlank(getFormLogin().getFailureUrl())) {
            configurer.failureForwardUrl(getFormLogin().getFailureUrl());
        }
        if (StringUtils.isNotBlank(getFormLogin().getAuthenticationUrl())) {
            configurer.successForwardUrl(getFormLogin().getAuthenticationUrl());
        }
    }

    private OAuth2AuthenticationProperties.FormLogin getFormLogin() {
        return authenticationProperties.getFormLogin();
    }
}
