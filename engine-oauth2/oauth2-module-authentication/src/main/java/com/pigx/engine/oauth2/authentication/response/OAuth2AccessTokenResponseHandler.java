package com.pigx.engine.oauth2.authentication.response;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import com.pigx.engine.core.identity.utils.SecurityUtils;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


public class OAuth2AccessTokenResponseHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AccessTokenResponseHandler.class);

    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter =
            new OAuth2AccessTokenResponseHttpMessageConverter();

    private final HttpCryptoProcessor httpCryptoProcessor;

    public OAuth2AccessTokenResponseHandler(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.debug("[PIGXD] |- OAuth2 authentication success for [{}]", request.getRequestURI());

        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication =
                (OAuth2AccessTokenAuthenticationToken) authentication;

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder =
                OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                        .tokenType(accessToken.getTokenType())
                        .scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }

        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }

        String sessionId = SessionUtils.analyseSessionId(request);
        UserPrincipal userPrincipal = SecurityUtils.getUserPrincipal(accessTokenAuthentication);

        // 如果包含 ID_TOKEN，那么前端直接解析 ID_TOKEN，从中获取用户基本信息
        if (isOidcUserInfoPattern(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        } else {
            // 如果不包含 ID_TOKEN, 那么需要利用 SessionId 将用户信息加密传递给前端，前端解密后获取用户基本信息
            if (isHerodotusUserInfoPattern(sessionId, userPrincipal)) {
                String data = Jackson2Utils.toJson(userPrincipal);
                String encryptData = httpCryptoProcessor.encrypt(sessionId, data);
                Map<String, Object> parameters = new HashMap<>(additionalParameters);
                parameters.put(SystemConstants.SCOPE_OPENID, encryptData);
                builder.additionalParameters(parameters);
            } else {
                log.warn("[PIGXD] |- OAuth2 authentication can not get use info.");
            }
        }

        // 如果 UserPrincipal 存在，则放入 Session 中方便后端直接读取
        if (isHerodotusUserInfoPattern(sessionId, userPrincipal)) {
            HttpSession session = request.getSession(false);
            if (ObjectUtils.isNotEmpty(session)) {
                log.debug("[PIGXD] |- Adding user principal to session [{}].", sessionId);
                session.setAttribute(SystemConstants.KEY__USER_PRINCIPAL, userPrincipal);
            }
        }

        OAuth2AccessTokenResponse accessTokenResponse = builder.build();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
    }

    private boolean isHerodotusUserInfoPattern(String sessionId, UserPrincipal userPrincipal) {
        return StringUtils.isNotBlank(sessionId) && ObjectUtils.isNotEmpty(userPrincipal);
    }

    private boolean isOidcUserInfoPattern(Map<String, Object> additionalParameters) {
        return MapUtils.isNotEmpty(additionalParameters) && additionalParameters.containsKey(OidcParameterNames.ID_TOKEN);
    }
}
