package com.pigx.engine.oauth2.authentication.response;

import com.pigx.engine.oauth2.core.exception.AccountEndpointLimitedException;
import com.pigx.engine.oauth2.core.exception.SessionExpiredException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/response/DefaultOAuth2AuthenticationEventPublisher.class */
public class DefaultOAuth2AuthenticationEventPublisher extends DefaultAuthenticationEventPublisher {
    public DefaultOAuth2AuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }

    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        super.publishAuthenticationFailure(convert(exception), authentication);
    }

    private AuthenticationException convert(AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2AuthenticationException authenticationException = (OAuth2AuthenticationException) exception;
            OAuth2Error error = authenticationException.getError();
            switch (error.getErrorCode()) {
                case "AccountExpiredException":
                    return new AccountExpiredException(exception.getMessage(), exception.getCause());
                case "CredentialsExpiredException":
                    return new CredentialsExpiredException(exception.getMessage(), exception.getCause());
                case "DisabledException":
                    return new DisabledException(exception.getMessage(), exception.getCause());
                case "LockedException":
                    return new LockedException(exception.getMessage(), exception.getCause());
                case "AccountEndpointLimitedException":
                    return new AccountEndpointLimitedException(exception.getMessage(), exception.getCause());
                case "UsernameNotFoundException":
                    return new UsernameNotFoundException(exception.getMessage(), exception.getCause());
                case "SessionExpiredException":
                    return new SessionExpiredException(exception.getMessage(), exception.getCause());
                default:
                    return new BadCredentialsException(exception.getMessage(), exception.getCause());
            }
        }
        return exception;
    }
}
