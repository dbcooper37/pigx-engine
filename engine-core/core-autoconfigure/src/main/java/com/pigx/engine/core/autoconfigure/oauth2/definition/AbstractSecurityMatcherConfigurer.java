package com.pigx.engine.autoconfigure.oauth2.definition;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.domain.HerodotusRequest;
import com.pigx.engine.core.definition.constant.SecurityResources;
import com.pigx.engine.core.definition.utils.ListUtils;
import com.pigx.engine.core.identity.domain.HerodotusSecurityAttribute;
import com.pigx.engine.core.identity.enums.PermissionExpression;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/definition/AbstractSecurityMatcherConfigurer.class */
public abstract class AbstractSecurityMatcherConfigurer {
    private final OAuth2AuthorizationProperties authorizationProperties;
    private final List<String> staticResources;
    private final List<String> permitAllResources;
    private final List<String> hasAuthenticatedResources;
    private final Map<HerodotusRequest, List<HerodotusSecurityAttribute>> permitAllAttributes;

    protected AbstractSecurityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
        this.staticResources = ListUtils.merge(authorizationProperties.getMatcher().getStaticResources(), SecurityResources.DEFAULT_IGNORED_STATIC_RESOURCES);
        this.permitAllResources = ListUtils.merge(authorizationProperties.getMatcher().getPermitAll(), SecurityResources.DEFAULT_PERMIT_ALL_RESOURCES);
        this.hasAuthenticatedResources = ListUtils.merge(authorizationProperties.getMatcher().getHasAuthenticated(), SecurityResources.DEFAULT_HAS_AUTHENTICATED_RESOURCES);
        this.permitAllAttributes = createPermitAllAttributes(this.permitAllResources);
    }

    private Map<HerodotusRequest, List<HerodotusSecurityAttribute>> createPermitAllAttributes(List<String> permitAllResources) {
        if (CollectionUtils.isNotEmpty(permitAllResources)) {
            Map<HerodotusRequest, List<HerodotusSecurityAttribute>> result = new LinkedHashMap<>();
            permitAllResources.forEach(item -> {
                result.put(new HerodotusRequest(item), List.of(new HerodotusSecurityAttribute(PermissionExpression.PERMIT_ALL.getValue())));
            });
            return result;
        }
        return new LinkedHashMap();
    }

    public boolean isStrictMode() {
        return this.authorizationProperties.getStrict().booleanValue();
    }

    protected List<String> getStaticResources() {
        return this.staticResources;
    }

    protected List<String> getPermitAllResources() {
        return this.permitAllResources;
    }

    protected List<String> getHasAuthenticatedResources() {
        return this.hasAuthenticatedResources;
    }

    public Map<HerodotusRequest, List<HerodotusSecurityAttribute>> getPermitAllAttributes() {
        return this.permitAllAttributes;
    }
}
