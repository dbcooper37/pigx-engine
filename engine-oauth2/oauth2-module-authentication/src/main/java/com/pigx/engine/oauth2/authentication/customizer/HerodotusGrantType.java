package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.core.definition.constant.SystemConstants;
import org.springframework.security.oauth2.core.AuthorizationGrantType;


public interface HerodotusGrantType {

    AuthorizationGrantType SOCIAL = new AuthorizationGrantType(SystemConstants.SOCIAL_CREDENTIALS);

    AuthorizationGrantType PASSWORD = new AuthorizationGrantType(SystemConstants.PASSWORD);
}
