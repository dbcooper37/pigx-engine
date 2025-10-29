package com.pigx.engine.oauth2.authentication.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class OAuth2FormLoginAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public OAuth2FormLoginAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OAuth2FormLoginAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
