package com.pigx.engine.core.definition.domain.view.vue;

import com.fasterxml.jackson.annotation.JsonProperty;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/view/vue/ParentMeta.class */
public class ParentMeta extends BaseMeta {

    @JsonProperty("isHideAllChild")
    private Boolean hideAllChild = false;

    public Boolean getHideAllChild() {
        return this.hideAllChild;
    }

    public void setHideAllChild(Boolean hideAllChild) {
        this.hideAllChild = hideAllChild;
    }
}
