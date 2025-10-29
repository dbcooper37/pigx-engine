package com.pigx.engine.rest.servlet.identity.dto;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.domain.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


@Schema(name = "机要传递实体")
public class SessionExchange extends AbstractDto {

    @NotBlank(message = "confidential参数不能为空")
    @Schema(name = "用后端RSA/SM2 PublicKey加密的前端RSA/SM2 PublicKey")
    private String publicKey;

    @NotBlank(message = "Session Key不能为空")
    @Schema(name = "未登录前端身份标识")
    private String sessionId;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("publicKey", publicKey)
                .add("sessionId", sessionId)
                .toString();
    }
}
