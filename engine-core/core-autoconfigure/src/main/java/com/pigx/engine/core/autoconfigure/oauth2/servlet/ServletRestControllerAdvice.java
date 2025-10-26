package com.pigx.engine.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.definition.SecurityGlobalExceptionHandler;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.core.identity.exception.PlatformAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/servlet/ServletRestControllerAdvice.class */
public class ServletRestControllerAdvice {
    private static Result<String> resolveException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = SecurityGlobalExceptionHandler.resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    private static Result<String> resolveSecurityException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = SecurityGlobalExceptionHandler.resolveSecurityException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public static Result<String> restTemplateException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return resolveException(ex, request, response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public static Result<String> validationMethodArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response) {
        return validationBindException(ex, request, response);
    }

    @ExceptionHandler({BindException.class})
    public static Result<String> validationBindException(BindException ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = SecurityGlobalExceptionHandler.resolveException(ex, request.getRequestURI());
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (ObjectUtils.isNotEmpty(fieldError)) {
            result.validation(fieldError.getDefaultMessage(), fieldError.getCode(), fieldError.getField());
        }
        response.setStatus(result.getStatus());
        return result;
    }

    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class, PlatformAuthenticationException.class, OAuth2AuthenticationException.class})
    public static Result<String> authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return resolveSecurityException(ex, request, response);
    }

    @ExceptionHandler({Exception.class, PlatformRuntimeException.class})
    public static Result<String> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return resolveException(ex, request, response);
    }
}
