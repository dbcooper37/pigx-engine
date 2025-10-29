package com.pigx.engine.core.autoconfigure.oauth2;

import com.pigx.engine.core.autoconfigure.oauth2.constant.TokenFormat;
import com.pigx.engine.core.definition.constant.BaseConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = BaseConstants.PROPERTY_OAUTH2_AUTHORIZATION)
public class OAuth2AuthorizationProperties {

    /**
     * Token 校验是采用远程方式还是本地方式。
     */
    private TokenFormat tokenFormat = TokenFormat.OPAQUE;

    /**
     * 是否使用严格模式。严格模式一定要求有权限，非严格模式没有权限管控的接口，只要认证通过也可以使用。
     */
    private Boolean strict = true;

    /**
     * 指定 Request Matcher 静态安全规则
     */
    private Matcher matcher = new Matcher();

    public TokenFormat getTokenFormat() {
        return tokenFormat;
    }

    public void setTokenFormat(TokenFormat tokenFormat) {
        this.tokenFormat = tokenFormat;
    }

    public Boolean getStrict() {
        return strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    /**
     * 用于手动的指定 Request Matcher 安全规则。
     * <p>
     * permitAll 比较常用，因此先只增加该项。后续可根据需要添加
     */
    public static class Matcher {
        /**
         * 静态资源过滤
         */
        private List<String> staticResources;
        /**
         * Security "permitAll" 权限列表。
         */
        private List<String> permitAll;
        /**
         * 只校验是否请求中包含Token，不校验Token中是否包含该权限的资源
         */
        private List<String> hasAuthenticated;

        public List<String> getStaticResources() {
            return staticResources;
        }

        public void setStaticResources(List<String> staticResources) {
            this.staticResources = staticResources;
        }

        public List<String> getPermitAll() {
            return permitAll;
        }

        public void setPermitAll(List<String> permitAll) {
            this.permitAll = permitAll;
        }

        public List<String> getHasAuthenticated() {
            return hasAuthenticated;
        }

        public void setHasAuthenticated(List<String> hasAuthenticated) {
            this.hasAuthenticated = hasAuthenticated;
        }
    }
}
