package com.pigx.engine.oauth2.authentication.utils;

import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/utils/OAuth2EndpointUtils.class */
public class OAuth2EndpointUtils {
    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private OAuth2EndpointUtils() {
    }

    public static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                linkedMultiValueMap.add(key, value);
            }
        });
        return linkedMultiValueMap;
    }

    public static void throwError(String errorCode, String parameterName) throws OAuth2AuthenticationException {
        throwError(errorCode, parameterName, ACCESS_TOKEN_REQUEST_ERROR_URI);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    public static void throwError(String errorCode, String parameterName, String errorUri) throws OAuth2AuthenticationException {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OAuth2AuthenticationException(error);
    }

    private static boolean checkRequired(MultiValueMap<String, String> parameters, String parameterName, String parameterValue) {
        return (StringUtils.hasText(parameterValue) && ((List) parameters.get(parameterName)).size() == 1) ? false : true;
    }

    private static boolean checkOptional(MultiValueMap<String, String> parameters, String parameterName, String parameterValue) {
        return StringUtils.hasText(parameterValue) && ((List) parameters.get(parameterName)).size() != 1;
    }

    public static String checkParameter(MultiValueMap<String, String> parameters, String parameterName, boolean isRequired, String errorCode, String errorUri) throws OAuth2AuthenticationException {
        String value = (String) parameters.getFirst(parameterName);
        if (isRequired) {
            if (checkRequired(parameters, parameterName, value)) {
                throwError(errorCode, parameterName, errorUri);
            }
        } else if (checkOptional(parameters, parameterName, value)) {
            throwError(errorCode, parameterName, errorUri);
        }
        return value;
    }

    public static String checkRequiredParameter(MultiValueMap<String, String> parameters, String parameterName, String errorCode, String errorUri) {
        return checkParameter(parameters, parameterName, true, errorCode, errorUri);
    }

    public static String checkRequiredParameter(MultiValueMap<String, String> parameters, String parameterName, String errorCode) {
        return checkRequiredParameter(parameters, parameterName, errorCode, ACCESS_TOKEN_REQUEST_ERROR_URI);
    }

    public static String checkRequiredParameter(MultiValueMap<String, String> parameters, String parameterName) {
        return checkRequiredParameter(parameters, parameterName, OAuth2ErrorKeys.INVALID_REQUEST);
    }

    public static String checkOptionalParameter(MultiValueMap<String, String> parameters, String parameterName, String errorCode, String errorUri) {
        return checkParameter(parameters, parameterName, false, errorCode, errorUri);
    }

    public static String checkOptionalParameter(MultiValueMap<String, String> parameters, String parameterName, String errorCode) {
        return checkOptionalParameter(parameters, parameterName, errorCode, ACCESS_TOKEN_REQUEST_ERROR_URI);
    }

    public static String checkOptionalParameter(MultiValueMap<String, String> parameters, String parameterName) {
        return checkOptionalParameter(parameters, parameterName, OAuth2ErrorKeys.INVALID_REQUEST);
    }

    public static void validateAndAddDPoPParametersIfAvailable(HttpServletRequest request, Map<String, Object> additionalParameters) throws OAuth2AuthenticationException {
        String dPoPProofHeaderName = OAuth2AccessToken.TokenType.DPOP.getValue();
        String dPoPProof = request.getHeader(dPoPProofHeaderName);
        if (StringUtils.hasText(dPoPProof)) {
            if (Collections.list(request.getHeaders(dPoPProofHeaderName)).size() != 1) {
                throwError(OAuth2ErrorKeys.INVALID_REQUEST, dPoPProofHeaderName, ACCESS_TOKEN_REQUEST_ERROR_URI);
                return;
            }
            additionalParameters.put("dpop_proof", dPoPProof);
            additionalParameters.put("dpop_method", request.getMethod());
            additionalParameters.put("dpop_target_uri", request.getRequestURL().toString());
        }
    }
}
