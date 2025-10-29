package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;


public class OAuth2ExceptionHandlingConfigurerCustomizer implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {

    private final OAuth2AuthenticationProperties authenticationProperties;

    public OAuth2ExceptionHandlingConfigurerCustomizer(OAuth2AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        configurer
                .defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint(authenticationProperties.getFormLogin().getLoginPageUrl()),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                );
    }
}

