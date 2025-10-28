package com.pigx.engine.rest.servlet.identity.dto;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Schema(name = "OAuth2 范围请求 Dto")
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/dto/OAuth2ScopeDto.class */
public class OAuth2ScopeDto {

    @NotNull(message = "范围ID不能为空")
    @Schema(name = "范围ID")
    private String scopeId;

    @Schema(name = "范围权限列表")
    private Set<OAuth2PermissionDto> permissions = new HashSet();

    public String getScopeId() {
        return this.scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public Set<OAuth2PermissionDto> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<OAuth2PermissionDto> permissions) {
        this.permissions = permissions;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("scopeId", this.scopeId).toString();
    }
}
