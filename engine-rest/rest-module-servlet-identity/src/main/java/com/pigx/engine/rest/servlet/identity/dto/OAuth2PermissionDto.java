package com.pigx.engine.rest.servlet.identity.dto;

import com.pigx.engine.core.definition.domain.AbstractDto;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "OAuth2 权限请求 Dto")
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/dto/OAuth2PermissionDto.class */
public class OAuth2PermissionDto extends AbstractDto {

    @NotNull(message = "权限ID不能为空")
    @Schema(name = "权限ID")
    private String permissionId;

    @NotNull(message = "权限代码不能为空")
    @Schema(name = "权限代码")
    private String permissionCode;

    @NotNull(message = "服务ID不能为空")
    @Schema(name = "服务ID")
    private String permissionName;

    public String getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return this.permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("permissionId", this.permissionId).add("permissionCode", this.permissionCode).add("permissionName", this.permissionName).toString();
    }
}
