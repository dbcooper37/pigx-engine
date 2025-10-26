package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.core.definition.constant.SystemConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/customizer/HerodotusOidcUserInfoMapper.class */
public class HerodotusOidcUserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {
    private static final List<String> EMAIL_CLAIMS = Arrays.asList(SystemConstants.SCOPE_EMAIL, "email_verified");
    private static final List<String> PHONE_CLAIMS = Arrays.asList("phone_number", "phone_number_verified");
    private static final List<String> PROFILE_CLAIMS = Arrays.asList("name", "family_name", "given_name", "middle_name", "nickname", "preferred_username", SystemConstants.SCOPE_PROFILE, "picture", "website", "gender", "birthdate", "zoneinfo", "locale", "updated_at");

    private static Map<String, Object> getClaims(Map<String, Object> claims) {
        Set<String> needRemovedClaims = new HashSet<>(32);
        needRemovedClaims.add(SystemConstants.AUTHORITIES);
        Map<String, Object> requestedClaims = new HashMap<>(claims);
        Set<String> setKeySet = requestedClaims.keySet();
        Objects.requireNonNull(needRemovedClaims);
        setKeySet.removeIf((v1) -> {
            return r1.contains(v1);
        });
        return requestedClaims;
    }

    @Override // java.util.function.Function
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
        OidcUserInfoAuthenticationToken authentication = authenticationContext.getAuthentication();
        if (authentication.getPrincipal() instanceof BearerTokenAuthentication) {
            BearerTokenAuthentication principal = (BearerTokenAuthentication) authentication.getPrincipal();
            return new OidcUserInfo(getClaims(principal.getTokenAttributes()));
        }
        JwtAuthenticationToken principal2 = (JwtAuthenticationToken) authentication.getPrincipal();
        return new OidcUserInfo(getClaims(principal2.getToken().getClaims()));
    }
}
