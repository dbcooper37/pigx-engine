package com.pigx.engine.core.autoconfigure.oauth2.definition;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.exception.GlobalExceptionHandler;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;


public class SecurityGlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SecurityGlobalExceptionHandler.class);

    private static final Map<String, Feedback> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCESS_DENIED, ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INSUFFICIENT_SCOPE, ErrorCodes.INSUFFICIENT_SCOPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_CLIENT, ErrorCodes.INVALID_CLIENT);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_GRANT, ErrorCodes.INVALID_GRANT);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_REDIRECT_URI, ErrorCodes.INVALID_REDIRECT_URI);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_REQUEST, ErrorCodes.INVALID_REQUEST);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_SCOPE, ErrorCodes.INVALID_SCOPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_TOKEN, ErrorCodes.INVALID_TOKEN);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.SERVER_ERROR, ErrorCodes.SERVER_ERROR);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.TEMPORARILY_UNAVAILABLE, ErrorCodes.TEMPORARILY_UNAVAILABLE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNAUTHORIZED_CLIENT, ErrorCodes.UNAUTHORIZED_CLIENT);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_GRANT_TYPE, ErrorCodes.UNSUPPORTED_GRANT_TYPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_RESPONSE_TYPE, ErrorCodes.UNSUPPORTED_RESPONSE_TYPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_TOKEN_TYPE, ErrorCodes.UNSUPPORTED_TOKEN_TYPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_EXPIRED, ErrorCodes.ACCOUNT_EXPIRED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.BAD_CREDENTIALS, ErrorCodes.BAD_CREDENTIALS);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.CREDENTIALS_EXPIRED, ErrorCodes.CREDENTIALS_EXPIRED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_DISABLED, ErrorCodes.ACCOUNT_DISABLED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_LOCKED, ErrorCodes.ACCOUNT_LOCKED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_ENDPOINT_LIMITED, ErrorCodes.ACCOUNT_ENDPOINT_LIMITED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.USERNAME_NOT_FOUND, ErrorCodes.USERNAME_NOT_FOUND);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.SESSION_EXPIRED, ErrorCodes.SESSION_EXPIRED);
    }

    public static Result<String> resolveException(Exception ex, String path) {
        return GlobalExceptionHandler.resolveException(ex, path);
    }

    private static Result<String> handle(Exception exception, String path, String key, BiFunction<Feedback, String, Result<String>> toResult) {
        Optional<Feedback> optional = Optional.ofNullable(EXCEPTION_DICTIONARY.get(key));
        return optional
                .map(feedback -> toResult.apply(feedback, key))
                .orElseGet(() -> resolveException(exception, path));
    }

    private static Result<String> handleAuthenticationException(AuthenticationException authenticationException, String path) {
        Throwable throwable = authenticationException.getCause();
        if (ObjectUtils.isNotEmpty(throwable)) {
            // 此处是判断 throwable.getClass() 的父类是不是 PlatformRuntimeException
            // 注意：父类 PlatformRuntimeException.class 放左侧，子类放右侧。
            if (PlatformRuntimeException.class.isAssignableFrom(throwable.getClass())) {
                PlatformRuntimeException platformRuntimeException = (PlatformRuntimeException) throwable;
                Result<String> result = platformRuntimeException.getResult();
                return result.path(path);
            }
        }
        return resolveException(authenticationException, path);
    }

    /**
     * 静态解析认证异常
     *
     * @param exception 错误信息
     * @return Result 对象
     */
    public static Result<String> resolveSecurityException(Exception exception, String path) {

        Exception reason = new Exception();
        if (exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {
            OAuth2Error oAuth2Error = oAuth2AuthenticationException.getError();
            if (EXCEPTION_DICTIONARY.containsKey(oAuth2Error.getErrorCode())) {
                Feedback feedback = EXCEPTION_DICTIONARY.get(oAuth2Error.getErrorCode());
                Result<String> result = Result.failure(feedback, oAuth2Error.getErrorCode());
                result.path(oAuth2Error.getUri());
                result.stackTrace(exception.getStackTrace());
                result.detail(exception.getMessage());
                return result;
            }
        } else if (exception instanceof InternalAuthenticationServiceException internalAuthenticationServiceException) {
            return handleAuthenticationException(internalAuthenticationServiceException, path);
        } else if (exception instanceof InsufficientAuthenticationException insufficientAuthenticationException) {
            return handleAuthenticationException(insufficientAuthenticationException, path);
        } else {
            String exceptionName = exception.getClass().getSimpleName();
            if (StringUtils.isNotEmpty(exceptionName) && EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
                Feedback feedback = EXCEPTION_DICTIONARY.get(exceptionName);
                return Result.failure(feedback);
            } else {
                reason = exception;
            }
        }

        return resolveException(reason, path);
    }
}
