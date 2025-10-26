package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.domain.Result;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/exception/GlobalExceptionHandler.class */
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Map<String, Feedback> EXCEPTION_DICTIONARY = new HashMap();

    static {
        EXCEPTION_DICTIONARY.put("AccessDeniedException", ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put("AuthorizationDeniedException", ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put("BadSqlGrammarException", ErrorCodes.BAD_SQL_GRAMMAR);
        EXCEPTION_DICTIONARY.put("BindException", ErrorCodes.METHOD_ARGUMENT_NOT_VALID);
        EXCEPTION_DICTIONARY.put("CookieTheftException", ErrorCodes.COOKIE_THEFT);
        EXCEPTION_DICTIONARY.put("DataIntegrityViolationException", ErrorCodes.DATA_INTEGRITY_VIOLATION);
        EXCEPTION_DICTIONARY.put("HttpMediaTypeNotAcceptableException", ErrorCodes.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE);
        EXCEPTION_DICTIONARY.put("HttpMessageNotReadableException", ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
        EXCEPTION_DICTIONARY.put("HttpRequestMethodNotSupportedException", ErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
        EXCEPTION_DICTIONARY.put("IllegalArgumentException", ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION);
        EXCEPTION_DICTIONARY.put("InsufficientAuthenticationException", ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put("InvalidCookieException", ErrorCodes.INVALID_COOKIE);
        EXCEPTION_DICTIONARY.put("IOException", ErrorCodes.IO_EXCEPTION);
        EXCEPTION_DICTIONARY.put("MethodArgumentNotValidException", ErrorCodes.METHOD_ARGUMENT_NOT_VALID);
        EXCEPTION_DICTIONARY.put("MissingServletRequestParameterException", ErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
        EXCEPTION_DICTIONARY.put("NoResourceFoundException", ErrorCodes.NO_RESOURCE_FOUND_EXCEPTION);
        EXCEPTION_DICTIONARY.put("NullPointerException", ErrorCodes.NULL_POINTER_EXCEPTION);
        EXCEPTION_DICTIONARY.put("ProviderNotFoundException", ErrorCodes.PROVIDER_NOT_FOUND);
        EXCEPTION_DICTIONARY.put("RedisPipelineException", ErrorCodes.PIPELINE_INVALID_COMMANDS);
        EXCEPTION_DICTIONARY.put("TypeMismatchException", ErrorCodes.TYPE_MISMATCH_EXCEPTION);
        EXCEPTION_DICTIONARY.put("TransactionRollbackException", ErrorCodes.TRANSACTION_ROLLBACK);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Result<String> resolveException(Exception ex, String path) {
        log.trace("[Herodotus] |- Path : [{}], Exception：", path, ex);
        if (ex instanceof HerodotusException) {
            HerodotusException exception = (HerodotusException) ex;
            Result<String> result = exception.getResult();
            result.path(path);
            log.error("[Herodotus] |- Error is : {}", result);
            return result;
        }
        Result<String> result2 = Result.failure();
        String exceptionName = ex.getClass().getSimpleName();
        if (StringUtils.isNotEmpty(exceptionName) && EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
            Feedback feedback = EXCEPTION_DICTIONARY.get(exceptionName);
            result2 = Result.failure(feedback, exceptionName);
        } else {
            log.warn("[Herodotus] |- Can not find the exception name [{}] in dictionary, please do optimize ", exceptionName);
        }
        result2.path(path);
        result2.stackTrace(ex.getStackTrace());
        result2.detail(ex.getMessage());
        log.error("[Herodotus] |- Error is : {}", result2, ex);
        return result2;
    }
}
