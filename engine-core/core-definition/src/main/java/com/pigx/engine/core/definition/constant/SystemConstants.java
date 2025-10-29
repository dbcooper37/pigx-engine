package com.pigx.engine.core.definition.constant;

import cn.hutool.v7.core.date.DateFormatPool;


public interface SystemConstants {

    String NONE = "none";

    /**
     * 默认租户ID
     */
    String TENANT_ID = "public";
    /**
     * 默认树形结构根节点
     */
    String TREE_ROOT_ID = SymbolConstants.ZERO;
    /**
     * 默认的时间日期格式
     */
    String DATE_TIME_FORMAT = DateFormatPool.NORM_DATETIME_PATTERN;

    /* ---------- 系统信息 ---------- */

    String DN_OU = "Herodotus Cloud";
    String WEBSITE = "https://www.herodotus.vip";
    String COPYRIGHT_DETAILS = "AGPL-3.0 Licensed | Copyright © 2020-2030 码 匠 君";
    String SYSTEM_NAME = DN_OU;
    String PACKAGE_NAME = "com.pigx";
    String OPEN_API_SECURITY_SCHEME_BEARER_NAME = "HERODOTUS_AUTH";


    /* ---------- OAuth2 相关常量 ---------- */

    String BEARER_TYPE = "Bearer";
    String BEARER_TOKEN = BEARER_TYPE + SymbolConstants.SPACE;
    String BASIC_TYPE = "Basic";
    String BASIC_TOKEN = BASIC_TYPE + SymbolConstants.SPACE;

    /**
     * OAuth2 Default Endpoint
     */
    String OAUTH2_AUTHORIZATION_ENDPOINT = "/oauth2/authorize";
    String OAUTH2_PUSHED_AUTHORIZATION_REQUEST_ENDPOINT = "/oauth2/par";
    String OAUTH2_TOKEN_ENDPOINT = "/oauth2/token";
    String OAUTH2_TOKEN_REVOCATION_ENDPOINT = "/oauth2/revoke";
    String OAUTH2_TOKEN_INTROSPECTION_ENDPOINT = "/oauth2/introspect";
    String OAUTH2_DEVICE_AUTHORIZATION_ENDPOINT = "/oauth2/device_authorization";
    String OAUTH2_DEVICE_VERIFICATION_ENDPOINT = "/oauth2/device_verification";
    String OAUTH2_JWK_SET_ENDPOINT = "/oauth2/jwks";
    String OIDC_CLIENT_REGISTRATION_ENDPOINT = "/connect/register";
    String OIDC_LOGOUT_ENDPOINT = "/connect/logout";
    String OIDC_USER_INFO_ENDPOINT = "/userinfo";

    String OAUTH2_AUTHORIZATION_CONSENT_URI = "/oauth2/consent";
    String OAUTH2_DEVICE_ACTIVATION_URI = "/oauth2/device_activation";
    String OAUTH2_DEVICE_VERIFICATION_SUCCESS_URI = "/device_activated";
    String OAUTH2_DEVICE_VERIFICATION_FAILURE_URI = "/device_activation_failure";

    String KEY__USER_PRINCIPAL = "USER_PRINCIPAL";

    /**
     * Oauth2 模式类型
     */
    String PASSWORD = "password";
    String SOCIAL_CREDENTIALS = "social_credentials";
    /**
     * Security User 相关属性
     */
    String CODE = "code";
    String USERNAME = "username";
    String ROLES = "roles";
    String AUTHORITIES = "authorities";
    String EMPLOYEE_ID = "employeeId";
    String AVATAR = "avatar";
    String PRINCIPAL = "principal";
    String SOURCE = "source";
    String LICENSE = "license";
    String COPYRIGHT = "copyright";
    /**
     * 从 OidcScope 中拷贝的默认 Scope 方便以后使用
     */
    String SCOPE_OPENID = "openid";
    String SCOPE_EMAIL = "email";
    String SCOPE_PROFILE = "profile";
    String SCOPE_ADDRESS = "address";
    String SCOPE_PHONE = "phone";
    /**
     * for OpenID Connect 1.0 Dynamic Client Registration
     */
    String SCOPE_CLIENT_CREATE = "client.create";
    /**
     * for OpenID Connect 1.0 Dynamic Client Configuration
     */
    String SCOPE_CLIENT_READ = "client.read";

}
