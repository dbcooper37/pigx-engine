package com.pigx.engine.oauth2.authentication.configurer;

import com.pigx.engine.core.foundation.support.captcha.CaptchaRendererFactory;
import com.pigx.engine.oauth2.authentication.provider.OAuth2FormLoginAuthenticationProvider;
import com.pigx.engine.oauth2.authentication.response.OAuth2FormLoginAuthenticationFailureHandler;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;


public class OAuth2FormLoginSecureConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<OAuth2FormLoginSecureConfigurer<H>, H> {

    private final UserDetailsService userDetailsService;
    private final OAuth2AuthenticationProperties authenticationProperties;
    private final CaptchaRendererFactory captchaRendererFactory;
    private final HttpCryptoProcessor httpCryptoProcessor;

    public OAuth2FormLoginSecureConfigurer(UserDetailsService userDetailsService, OAuth2AuthenticationProperties authenticationProperties, CaptchaRendererFactory captchaRendererFactory, HttpCryptoProcessor httpCryptoProcessor) {
        this.userDetailsService = userDetailsService;
        this.authenticationProperties = authenticationProperties;
        this.captchaRendererFactory = captchaRendererFactory;
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public void configure(H httpSecurity) throws Exception {

        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        SecurityContextRepository securityContextRepository = httpSecurity.getSharedObject(SecurityContextRepository.class);

        OAuth2FormLoginAuthenticationFilter filter = getOAuth2FormLoginAuthenticationFilter(authenticationManager, this.httpCryptoProcessor, securityContextRepository);

        OAuth2FormLoginAuthenticationProvider provider = new OAuth2FormLoginAuthenticationProvider(captchaRendererFactory, userDetailsService);
        provider.setHideUserNotFoundExceptions(false);

        httpSecurity
                .authenticationProvider(provider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    private OAuth2FormLoginAuthenticationFilter getOAuth2FormLoginAuthenticationFilter(AuthenticationManager authenticationManager, HttpCryptoProcessor httpCryptoProcessor, SecurityContextRepository securityContextRepository) {
        OAuth2FormLoginAuthenticationFilter filter = new OAuth2FormLoginAuthenticationFilter(authenticationManager, httpCryptoProcessor);
        filter.setUsernameParameter(getFormLogin().getUsernameParameter());
        filter.setPasswordParameter(getFormLogin().getPasswordParameter());
        filter.setAuthenticationDetailsSource(new OAuth2FormLoginWebAuthenticationDetailSource(authenticationProperties, httpCryptoProcessor));
        filter.setAuthenticationFailureHandler(new OAuth2FormLoginAuthenticationFailureHandler(getFormLogin().getFailureUrl()));
        filter.setSecurityContextRepository(securityContextRepository);
        return filter;
    }

    private OAuth2AuthenticationProperties.FormLogin getFormLogin() {
        return authenticationProperties.getFormLogin();
    }
}
