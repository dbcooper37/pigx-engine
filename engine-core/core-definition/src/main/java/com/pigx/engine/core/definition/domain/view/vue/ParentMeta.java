package com.pigx.engine.core.definition.domain.view.vue;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ParentMeta extends BaseMeta {

    @JsonProperty("isHideAllChild")
    private Boolean hideAllChild = false;

    public Boolean getHideAllChild() {
        return hideAllChild;
    }

    public void setHideAllChild(Boolean hideAllChild) {
        this.hideAllChild = hideAllChild;
    }
}
