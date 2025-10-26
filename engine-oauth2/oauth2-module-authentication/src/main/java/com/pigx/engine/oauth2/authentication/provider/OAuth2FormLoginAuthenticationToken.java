package com.pigx.engine.oauth2.authentication.provider;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/OAuth2FormLoginAuthenticationToken.class */
public class OAuth2FormLoginAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public OAuth2FormLoginAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OAuth2FormLoginAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
