package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;


public class OAuth2ExceptionHandlingConfigurerCustomizer implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {

    private final ThymeleafTemplateHandler templateHandler;

    public OAuth2ExceptionHandlingConfigurerCustomizer(ThymeleafTemplateHandler templateHandler) {
        this.templateHandler = templateHandler;
    }

    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        configurer
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint(templateHandler))
                .accessDeniedHandler(new HerodotusAccessDeniedHandler(templateHandler));
    }
}

