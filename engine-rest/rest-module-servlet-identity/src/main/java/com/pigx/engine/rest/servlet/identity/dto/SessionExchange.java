package com.pigx.engine.rest.servlet.identity.dto;

import com.pigx.engine.core.definition.domain.AbstractDto;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "机要传递实体")
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/dto/SessionExchange.class */
public class SessionExchange extends AbstractDto {

    @NotBlank(message = "confidential参数不能为空")
    @Schema(name = "用后端RSA/SM2 PublicKey加密的前端RSA/SM2 PublicKey")
    private String publicKey;

    @NotBlank(message = "Session Key不能为空")
    @Schema(name = "未登录前端身份标识")
    private String sessionId;

    public String getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("publicKey", this.publicKey).add("sessionId", this.sessionId).toString();
    }
}
