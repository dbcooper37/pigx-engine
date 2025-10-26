package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusGrantType;
import com.pigx.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/OAuth2SocialCredentialsAuthenticationConverter.class */
public class OAuth2SocialCredentialsAuthenticationConverter extends AbstractAuthenticationConverter {
    public OAuth2SocialCredentialsAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        super(httpCryptoProcessor);
    }

    public Authentication convert(HttpServletRequest request) throws OAuth2AuthenticationException {
        String grantType = request.getParameter("grant_type");
        if (!HerodotusGrantType.SOCIAL.getValue().equals(grantType)) {
            return null;
        }
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        String scope = OAuth2EndpointUtils.checkOptionalParameter(parameters, "scope");
        String source = OAuth2EndpointUtils.checkRequiredParameter(parameters, SystemConstants.SOURCE);
        if (StringUtils.hasText(source)) {
            AccountCategory accountCategory = AccountCategory.getAccountType(source);
            if (ObjectUtils.isNotEmpty(accountCategory)) {
                switch (accountCategory.getHandler()) {
                    case "PHONE_NUMBER":
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "mobile");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, SystemConstants.CODE);
                        break;
                    case "WECHAT_MINI_APP":
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "appId");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "sessionKey");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "encryptedData");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "iv");
                        break;
                }
            }
        }
        Map<String, Object> additionalParameters = getAdditionalParameters(request, parameters);
        OAuth2EndpointUtils.validateAndAddDPoPParametersIfAvailable(request, additionalParameters);
        return new OAuth2SocialCredentialsAuthenticationToken(getClientPrincipal(), getRequestedScopes(scope), additionalParameters);
    }
}
