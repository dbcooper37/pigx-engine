package com.pigx.engine.core.definition.domain.view.vue;

import com.fasterxml.jackson.annotation.JsonProperty;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/view/vue/ChildMeta.class */
public class ChildMeta extends BaseMeta {

    @JsonProperty("isDetailContent")
    private Boolean detailContent;

    public Boolean getDetailContent() {
        return this.detailContent;
    }

    public void setDetailContent(Boolean detailContent) {
        this.detailContent = detailContent;
    }
}
