package com.pigx.engine.oauth2.core.utils;

import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/utils/OAuth2AuthenticationUtils.class */
public class OAuth2AuthenticationUtils {
    public static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        }
        if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        }
        if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }
        if (AuthorizationGrantType.DEVICE_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.DEVICE_CODE;
        }
        if (AuthorizationGrantType.JWT_BEARER.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.JWT_BEARER;
        }
        if (AuthorizationGrantType.TOKEN_EXCHANGE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.TOKEN_EXCHANGE;
        }
        return new AuthorizationGrantType(authorizationGrantType);
    }

    public static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod) {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        }
        if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        }
        if (ClientAuthenticationMethod.CLIENT_SECRET_JWT.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_JWT;
        }
        if (ClientAuthenticationMethod.PRIVATE_KEY_JWT.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.PRIVATE_KEY_JWT;
        }
        if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.NONE;
        }
        if (ClientAuthenticationMethod.TLS_CLIENT_AUTH.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.TLS_CLIENT_AUTH;
        }
        if (ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH;
        }
        return new ClientAuthenticationMethod(clientAuthenticationMethod);
    }

    public static AuthenticationMethod resolveAuthenticationMethod(String authenticationMethod) {
        if (AuthenticationMethod.HEADER.getValue().equals(authenticationMethod)) {
            return AuthenticationMethod.HEADER;
        }
        if (AuthenticationMethod.FORM.getValue().equals(authenticationMethod)) {
            return AuthenticationMethod.FORM;
        }
        if (AuthenticationMethod.QUERY.getValue().equals(authenticationMethod)) {
            return AuthenticationMethod.QUERY;
        }
        return new AuthenticationMethod(authenticationMethod);
    }
}
