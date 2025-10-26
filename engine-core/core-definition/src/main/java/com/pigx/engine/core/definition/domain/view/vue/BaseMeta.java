package com.pigx.engine.core.definition.domain.view.vue;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/view/vue/BaseMeta.class */
public class BaseMeta implements BaseEntity {
    private String title;
    private String icon;

    @JsonProperty("isNotKeepAlive")
    private Boolean notKeepAlive = false;

    @JsonProperty("isIgnoreAuth")
    private Boolean ignoreAuth = false;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getNotKeepAlive() {
        return this.notKeepAlive;
    }

    public void setNotKeepAlive(Boolean notKeepAlive) {
        this.notKeepAlive = notKeepAlive;
    }

    public Boolean getIgnoreAuth() {
        return this.ignoreAuth;
    }

    public void setIgnoreAuth(Boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }
}
