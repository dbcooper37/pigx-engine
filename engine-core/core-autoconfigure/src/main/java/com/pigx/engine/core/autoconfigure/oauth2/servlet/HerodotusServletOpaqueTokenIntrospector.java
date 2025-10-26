package com.pigx.engine.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/servlet/HerodotusServletOpaqueTokenIntrospector.class */
public class HerodotusServletOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>() { // from class: com.pigx.engine.core.autoconfigure.oauth2.servlet.HerodotusServletOpaqueTokenIntrospector.1
    };
    private final Log logger;
    private final RestOperations restOperations;
    private Converter<String, RequestEntity<?>> requestEntityConverter;

    public HerodotusServletOpaqueTokenIntrospector(OAuth2ResourceServerProperties resourceServerProperties) {
        this(getIntrospectionUri(resourceServerProperties), resourceServerProperties.getOpaquetoken().getClientId(), resourceServerProperties.getOpaquetoken().getClientSecret());
    }

    public HerodotusServletOpaqueTokenIntrospector(String introspectionUri, String clientId, String clientSecret) {
        this.logger = LogFactory.getLog(getClass());
        Assert.notNull(introspectionUri, "introspectionUri cannot be null");
        Assert.notNull(clientId, "clientId cannot be null");
        Assert.notNull(clientSecret, "clientSecret cannot be null");
        this.requestEntityConverter = defaultRequestEntityConverter(URI.create(introspectionUri));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(clientId, clientSecret));
        this.restOperations = restTemplate;
    }

    public HerodotusServletOpaqueTokenIntrospector(String introspectionUri, RestOperations restOperations) {
        this.logger = LogFactory.getLog(getClass());
        Assert.notNull(introspectionUri, "introspectionUri cannot be null");
        Assert.notNull(restOperations, "restOperations cannot be null");
        this.requestEntityConverter = defaultRequestEntityConverter(URI.create(introspectionUri));
        this.restOperations = restOperations;
    }

    private static String getIntrospectionUri(OAuth2ResourceServerProperties resourceServerProperties) {
        String introspectionUri = ServiceContextHolder.getTokenIntrospectionUri();
        String configIntrospectionUri = resourceServerProperties.getOpaquetoken().getIntrospectionUri();
        if (StringUtils.isNotBlank(configIntrospectionUri)) {
            introspectionUri = configIntrospectionUri;
        }
        return introspectionUri;
    }

    private Converter<String, RequestEntity<?>> defaultRequestEntityConverter(URI introspectionUri) {
        return token -> {
            HttpHeaders headers = requestHeaders();
            MultiValueMap<String, String> body = requestBody(token);
            return new RequestEntity(body, headers, HttpMethod.POST, introspectionUri);
        };
    }

    private HttpHeaders requestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private MultiValueMap<String, String> requestBody(String token) {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
        linkedMultiValueMap.add("token", token);
        return linkedMultiValueMap;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException */
    public OAuth2AuthenticatedPrincipal introspect(String token) throws BadOpaqueTokenException, OAuth2IntrospectionException {
        RequestEntity<?> requestEntity = (RequestEntity) this.requestEntityConverter.convert(token);
        if (requestEntity == null) {
            throw new OAuth2IntrospectionException("requestEntityConverter returned a null entity");
        }
        ResponseEntity<Map<String, Object>> responseEntity = makeRequest(requestEntity);
        Map<String, Object> claims = adaptToNimbusResponse(responseEntity);
        return convertClaimsSet(claims);
    }

    public void setRequestEntityConverter(Converter<String, RequestEntity<?>> requestEntityConverter) {
        Assert.notNull(requestEntityConverter, "requestEntityConverter cannot be null");
        this.requestEntityConverter = requestEntityConverter;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException */
    private ResponseEntity<Map<String, Object>> makeRequest(RequestEntity<?> requestEntity) throws OAuth2IntrospectionException {
        try {
            return this.restOperations.exchange(requestEntity, STRING_OBJECT_MAP);
        } catch (Exception ex) {
            throw new OAuth2IntrospectionException(ex.getMessage(), ex);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException */
    private Map<String, Object> adaptToNimbusResponse(ResponseEntity<Map<String, Object>> responseEntity) throws BadOpaqueTokenException, OAuth2IntrospectionException {
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new OAuth2IntrospectionException("Introspection endpoint responded with " + String.valueOf(responseEntity.getStatusCode()));
        }
        Map<String, Object> claims = (Map) responseEntity.getBody();
        if (claims == null) {
            return Collections.emptyMap();
        }
        boolean active = ((Boolean) claims.compute("active", (k, v) -> {
            if (v instanceof String) {
                return Boolean.valueOf(Boolean.parseBoolean((String) v));
            }
            if (v instanceof Boolean) {
                return v;
            }
            return false;
        })).booleanValue();
        if (!active) {
            this.logger.trace("Did not validate token since it is inactive");
            throw new BadOpaqueTokenException("Provided token isn't active");
        }
        return claims;
    }

    private OAuth2AuthenticatedPrincipal convertClaimsSet(Map<String, Object> claims) {
        claims.computeIfPresent("aud", (k, v) -> {
            if (v instanceof String) {
                return Collections.singletonList(v);
            }
            return v;
        });
        claims.computeIfPresent("client_id", (k2, v2) -> {
            return v2.toString();
        });
        claims.computeIfPresent("exp", (k3, v3) -> {
            return Instant.ofEpochSecond(((Number) v3).longValue());
        });
        claims.computeIfPresent("iat", (k4, v4) -> {
            return Instant.ofEpochSecond(((Number) v4).longValue());
        });
        claims.computeIfPresent("iss", (k5, v5) -> {
            return v5.toString();
        });
        claims.computeIfPresent("nbf", (k6, v6) -> {
            return Instant.ofEpochSecond(((Number) v6).longValue());
        });
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        claims.computeIfPresent("scope", (k7, v7) -> {
            return v7.toString();
        });
        claims.computeIfPresent(SystemConstants.AUTHORITIES, (k8, v8) -> {
            if (v8 instanceof ArrayList) {
                List<String> values = (List) v8;
                values.forEach(value -> {
                    authorities.add(new HerodotusGrantedAuthority(value));
                });
            }
            return v8;
        });
        return new OAuth2IntrospectionAuthenticatedPrincipal(claims, authorities);
    }
}
