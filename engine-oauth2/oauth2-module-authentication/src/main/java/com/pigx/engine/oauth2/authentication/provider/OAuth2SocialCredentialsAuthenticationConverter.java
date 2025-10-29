package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.oauth2.authentication.customizer.HerodotusGrantType;
import com.pigx.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;


public class OAuth2SocialCredentialsAuthenticationConverter extends AbstractAuthenticationConverter {

    public OAuth2SocialCredentialsAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        super(httpCryptoProcessor);
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!HerodotusGrantType.SOCIAL.getValue().equals(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // scope (OPTIONAL)
        String scope = OAuth2EndpointUtils.checkOptionalParameter(parameters, OAuth2ParameterNames.SCOPE);

        // source (REQUIRED)
        String source = OAuth2EndpointUtils.checkRequiredParameter(parameters, SystemConstants.SOURCE);
        // others (REQUIRED)
        // TODO：2022-03-31 这里主要是作为参数的检查，社交登录内容比较多，后续根据实际情况添加
        if (StringUtils.hasText(source)) {
            AccountCategory accountCategory = AccountCategory.getAccountType(source);
            if (ObjectUtils.isNotEmpty(accountCategory)) {
                switch (accountCategory.getHandler()) {
                    case AccountCategory.PHONE_NUMBER_HANDLER:
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "mobile");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "code");
                        break;
                    case AccountCategory.WECHAT_MINI_APP_HANDLER:
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "appId");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "sessionKey");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "encryptedData");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "iv");
                        break;
                    default:
                        break;
                }
            }
        }

        Map<String, Object> additionalParameters = getAdditionalParameters(request, parameters);
        // Validate DPoP Proof HTTP Header (if available)
        OAuth2EndpointUtils.validateAndAddDPoPParametersIfAvailable(request, additionalParameters);

        return new OAuth2SocialCredentialsAuthenticationToken(getClientPrincipal(), getRequestedScopes(scope), additionalParameters);
    }
}
