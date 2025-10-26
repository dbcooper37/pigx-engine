package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusGrantType;
import com.pigx.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.util.MultiValueMap;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/OAuth2ResourceOwnerPasswordAuthenticationConverter.class */
public final class OAuth2ResourceOwnerPasswordAuthenticationConverter extends AbstractAuthenticationConverter {
    public OAuth2ResourceOwnerPasswordAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        super(httpCryptoProcessor);
    }

    @Nullable
    public Authentication convert(HttpServletRequest request) throws OAuth2AuthenticationException {
        String grantType = request.getParameter("grant_type");
        if (!HerodotusGrantType.PASSWORD.getValue().equals(grantType)) {
            return null;
        }
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        String scope = OAuth2EndpointUtils.checkOptionalParameter(parameters, "scope");
        OAuth2EndpointUtils.checkRequiredParameter(parameters, SystemConstants.USERNAME);
        OAuth2EndpointUtils.checkRequiredParameter(parameters, SystemConstants.PASSWORD);
        Map<String, Object> additionalParameters = getAdditionalParameters(request, parameters);
        OAuth2EndpointUtils.validateAndAddDPoPParametersIfAvailable(request, additionalParameters);
        return new OAuth2ResourceOwnerPasswordAuthenticationToken(getClientPrincipal(), getRequestedScopes(scope), additionalParameters);
    }
}
