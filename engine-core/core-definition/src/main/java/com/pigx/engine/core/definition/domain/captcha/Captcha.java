package com.pigx.engine.core.definition.domain.captcha;

import com.pigx.engine.core.definition.domain.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/captcha/Captcha.class */
public abstract class Captcha extends AbstractDto {

    @Schema(name = "验证码身份")
    private String identity;

    @Schema(name = "验证码类别")
    private String category;

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
