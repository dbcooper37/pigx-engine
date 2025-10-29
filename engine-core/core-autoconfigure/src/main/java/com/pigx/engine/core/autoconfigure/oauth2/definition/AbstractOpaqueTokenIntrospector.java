package com.pigx.engine.core.autoconfigure.oauth2.definition;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

import java.time.Instant;
import java.util.*;


public abstract class AbstractOpaqueTokenIntrospector {

    protected OAuth2AuthenticatedPrincipal convertClaimsSet(Map<String, Object> claims) {
        claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.AUD, (k, v) -> {
            if (v instanceof String) {
                return Collections.singletonList(v);
            }
            return v;
        });
        claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.CLIENT_ID, (k, v) -> v.toString());
        claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.EXP,
                (k, v) -> Instant.ofEpochSecond(((Number) v).longValue()));
        claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.IAT,
                (k, v) -> Instant.ofEpochSecond(((Number) v).longValue()));
        // RFC-7662 page 7 directs users to RFC-7519 for defining the values of these
        // issuer fields.
        // https://datatracker.ietf.org/doc/html/rfc7662#page-7
        //
        // RFC-7519 page 9 defines issuer fields as being 'case-sensitive' strings
        // containing
        // a 'StringOrURI', which is defined on page 5 as being any string, but strings
        // containing ':'
        // should be treated as valid URIs.
        // https://datatracker.ietf.org/doc/html/rfc7519#section-2
        //
        // It is not defined however as to whether-or-not normalized URIs should be
        // treated as the same literal
        // value. It only defines validation itself, so to avoid potential ambiguity or
        // unwanted side effects that
        // may be awkward to debug, we do not want to manipulate this value. Previous
        // versions of Spring Security
        // would *only* allow valid URLs, which is not what we wish to achieve here.
        claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.ISS, (k, v) -> v.toString());
        claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.NBF,
                (k, v) -> Instant.ofEpochSecond(((Number) v).longValue()));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.SCOPE, (k, v) -> v.toString());
        claims.computeIfPresent(SystemConstants.AUTHORITIES, (k, v) -> {
            if (v instanceof ArrayList) {
                List<String> values = (List<String>) v;
                values.forEach(value -> authorities.add(new HerodotusGrantedAuthority(value)));
            }
            return v;
        });
        return new OAuth2IntrospectionAuthenticatedPrincipal(claims, authorities);
    }
}
