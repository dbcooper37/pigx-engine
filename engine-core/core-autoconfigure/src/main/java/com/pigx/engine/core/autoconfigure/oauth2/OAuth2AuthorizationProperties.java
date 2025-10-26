package com.pigx.engine.autoconfigure.oauth2;

import com.pigx.engine.core.autoconfigure.oauth2.constant.TokenFormat;
import com.pigx.engine.core.definition.constant.BaseConstants;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_OAUTH2_AUTHORIZATION)
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/OAuth2AuthorizationProperties.class */
public class OAuth2AuthorizationProperties {
    private TokenFormat tokenFormat = TokenFormat.OPAQUE;
    private Boolean strict = true;
    private Matcher matcher = new Matcher();

    public TokenFormat getTokenFormat() {
        return this.tokenFormat;
    }

    public void setTokenFormat(TokenFormat tokenFormat) {
        this.tokenFormat = tokenFormat;
    }

    public Boolean getStrict() {
        return this.strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }

    public Matcher getMatcher() {
        return this.matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/OAuth2AuthorizationProperties$Matcher.class */
    public static class Matcher {
        private List<String> staticResources;
        private List<String> permitAll;
        private List<String> hasAuthenticated;

        public List<String> getStaticResources() {
            return this.staticResources;
        }

        public void setStaticResources(List<String> staticResources) {
            this.staticResources = staticResources;
        }

        public List<String> getPermitAll() {
            return this.permitAll;
        }

        public void setPermitAll(List<String> permitAll) {
            this.permitAll = permitAll;
        }

        public List<String> getHasAuthenticated() {
            return this.hasAuthenticated;
        }

        public void setHasAuthenticated(List<String> hasAuthenticated) {
            this.hasAuthenticated = hasAuthenticated;
        }
    }
}
