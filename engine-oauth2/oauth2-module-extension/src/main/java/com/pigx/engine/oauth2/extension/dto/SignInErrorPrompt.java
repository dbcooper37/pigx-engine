package com.pigx.engine.oauth2.extension.dto;

import com.pigx.engine.core.definition.domain.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "登录错误提示信息")
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/dto/SignInErrorPrompt.class */
public class SignInErrorPrompt implements BaseDto {

    @NotBlank(message = "登录用户名不能为空")
    @Schema(name = "登录用户名", title = "必须是有效的用户名")
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
