package com.pigx.engine.core.autoconfigure.oauth2.definition;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.domain.HerodotusRequest;
import com.pigx.engine.core.definition.constant.SecurityResources;
import com.pigx.engine.core.definition.utils.ListUtils;
import com.pigx.engine.core.identity.domain.HerodotusSecurityAttribute;
import com.pigx.engine.core.identity.enums.PermissionExpression;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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
        this.permitAllAttributes = createPermitAllAttributes(permitAllResources);
    }

    /**
     * 获取 SecurityFilterChain 中配置的 RequestMatchers 信息。
     * <p>
     * RequestMatchers 可以理解为“静态配置”，将其与平台后端的“动态配置”有机结合。同时，防止因动态配置导致的静态配置失效的问题。
     * <p>
     * 目前只处理了 permitAll 类型。其它内容根据后续使用情况再行添加。
     *
     * @return RequestMatchers 中配置的权限数据
     */
    private Map<HerodotusRequest, List<HerodotusSecurityAttribute>> createPermitAllAttributes(List<String> permitAllResources) {
        if (CollectionUtils.isNotEmpty(permitAllResources)) {
            Map<HerodotusRequest, List<HerodotusSecurityAttribute>> result = new LinkedHashMap<>();
            permitAllResources.forEach(item -> {
                result.put(new HerodotusRequest(item), List.of(new HerodotusSecurityAttribute(PermissionExpression.PERMIT_ALL.getValue())));
            });
            return result;
        }
        return new LinkedHashMap<>();
    }

    public boolean isStrictMode() {
        return authorizationProperties.getStrict();
    }

    protected List<String> getStaticResources() {
        return staticResources;
    }

    protected List<String> getPermitAllResources() {
        return permitAllResources;
    }

    protected List<String> getHasAuthenticatedResources() {
        return hasAuthenticatedResources;
    }

    public Map<HerodotusRequest, List<HerodotusSecurityAttribute>> getPermitAllAttributes() {
        return permitAllAttributes;
    }
}
