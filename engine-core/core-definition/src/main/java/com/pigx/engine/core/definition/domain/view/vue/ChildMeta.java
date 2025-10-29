package com.pigx.engine.core.definition.domain.view.vue;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ChildMeta extends BaseMeta {

    @JsonProperty("isDetailContent")
    private Boolean detailContent;

    public Boolean getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(Boolean detailContent) {
        this.detailContent = detailContent;
    }
}
