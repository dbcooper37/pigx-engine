package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.utils.ListUtils;
import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import com.pigx.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import com.pigx.engine.web.core.exception.SessionInvalidException;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import java.util.*;


public abstract class AbstractAuthenticationConverter implements AuthenticationConverter {

    private final HttpCryptoProcessor httpCryptoProcessor;

    public AbstractAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    protected String[] decrypt(HttpServletRequest request, String sessionId, List<String> parameters) {
        if (SessionUtils.isCryptoEnabled(request, sessionId) && CollectionUtils.isNotEmpty(parameters)) {
            List<String> result = parameters.stream().map(item -> decrypt(request, sessionId, item)).toList();
            return ListUtils.toStringArray(result);
        }

        return ListUtils.toStringArray(parameters);
    }

    protected String decrypt(HttpServletRequest request, String sessionId, String parameter) {
        if (SessionUtils.isCryptoEnabled(request, sessionId) && StringUtils.isNotBlank(parameter)) {
            try {
                return httpCryptoProcessor.decrypt(sessionId, parameter);
            } catch (SessionInvalidException e) {
                OAuth2EndpointUtils.throwError(
                        OAuth2ErrorKeys.SESSION_EXPIRED,
                        e.getMessage(),
                        OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
            }
        }
        return parameter;
    }

    protected Authentication getClientPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    protected Map<String, Object> getAdditionalParameters(HttpServletRequest request, MultiValueMap<String, String> parameters) {

        String sessionId = SessionUtils.analyseSessionId(request);

        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                    !key.equals(OAuth2ParameterNames.SCOPE)) {
                additionalParameters.put(key, (value.size() == 1) ? decrypt(request, sessionId, value.get(0)) : decrypt(request, sessionId, value));
            }
        });

        return additionalParameters;
    }

    protected Set<String> getRequestedScopes(String scope) {

        Set<String> requestedScopes = null;
        if (org.springframework.util.StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(org.springframework.util.StringUtils.delimitedListToStringArray(scope, " ")));
        }

        return requestedScopes;
    }
}
