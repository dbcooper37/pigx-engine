package com.pigx.engine.rest.servlet.identity.dto;

import com.pigx.engine.core.definition.domain.AbstractDto;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "加密创建请求")
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/dto/SessionCreate.class */
public class SessionCreate extends AbstractDto {

    @NotBlank(message = "客户端ID不能为空")
    @Schema(name = "客户端ID")
    private String clientId;

    @NotBlank(message = "客户端秘钥不能为空")
    @Schema(name = "客户端秘钥")
    private String clientSecret;

    @Schema(name = "未登录前端身份标识")
    private String sessionId;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("clientId", this.clientId).add("clientSecret", this.clientSecret).add("sessionId", this.sessionId).toString();
    }
}
