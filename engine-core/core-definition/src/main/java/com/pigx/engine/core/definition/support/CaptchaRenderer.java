package com.pigx.engine.core.definition.support;

import com.pigx.engine.core.definition.domain.captcha.Captcha;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.definition.domain.captcha.Verification;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/support/CaptchaRenderer.class */
public interface CaptchaRenderer {
    Metadata draw();

    Captcha getCapcha(String key);

    boolean verify(Verification verification);

    String getCategory();
}
