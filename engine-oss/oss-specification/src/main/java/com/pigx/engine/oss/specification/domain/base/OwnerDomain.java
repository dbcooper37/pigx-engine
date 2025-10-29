package com.pigx.engine.oss.specification.domain.base;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "所有者")
public class OwnerDomain implements BaseEntity {

    /**
     * 所有者 ID
     */
    @Schema(name = "所有者 ID")
    private String id;

    /**
     * 所有者显示名称
     */
    @Schema(name = "所有者显示名称")
    private String displayName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("displayName", displayName)
                .toString();
    }
}
