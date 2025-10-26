package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.core.definition.constant.SystemConstants;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/customizer/HerodotusGrantType.class */
public interface HerodotusGrantType {
    public static final AuthorizationGrantType SOCIAL = new AuthorizationGrantType(SystemConstants.SOCIAL_CREDENTIALS);
    public static final AuthorizationGrantType PASSWORD = new AuthorizationGrantType(SystemConstants.PASSWORD);
}
