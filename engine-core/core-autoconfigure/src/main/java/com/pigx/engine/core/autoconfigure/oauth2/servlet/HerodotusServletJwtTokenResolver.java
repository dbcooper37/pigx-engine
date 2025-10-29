package com.pigx.engine.core.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.definition.UserPrincipalConverter;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import com.pigx.engine.core.identity.oauth2.BearerTokenResolver;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;


public class HerodotusServletJwtTokenResolver implements BearerTokenResolver {

    private final JwtDecoder jwtDecoder;

    private static final Logger log = LoggerFactory.getLogger(HerodotusServletJwtTokenResolver.class);

    public HerodotusServletJwtTokenResolver(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public UserPrincipal resolve(String token) {

        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token can not be null");
        }

        Jwt jwt = getJwt(token);
        if (ObjectUtils.isNotEmpty(jwt)) {
            UserPrincipal userPrincipal = UserPrincipalConverter.toUserPrincipal(jwt);
            log.debug("[PIGXD] |- Resolve JWT token to principal details [{}]", userPrincipal);
            return userPrincipal;
        }

        return null;
    }

    private Jwt getJwt(String token) {
        try {
            return this.jwtDecoder.decode(token);
        } catch (BadJwtException failed) {
            log.error("[PIGXD] |- Failed to decode since the JWT was invalid");
        } catch (JwtException failed) {
            log.error("[PIGXD] |- Failed to decode JWT, catch exception", failed);
        }

        return null;
    }
}
