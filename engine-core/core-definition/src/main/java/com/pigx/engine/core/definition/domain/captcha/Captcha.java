package com.pigx.engine.core.definition.domain.captcha;

import com.pigx.engine.core.definition.domain.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;


public abstract class Captcha extends AbstractDto {

    @Schema(name = "验证码身份")
    private String identity;

    @Schema(name = "验证码类别")
    private String category;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
