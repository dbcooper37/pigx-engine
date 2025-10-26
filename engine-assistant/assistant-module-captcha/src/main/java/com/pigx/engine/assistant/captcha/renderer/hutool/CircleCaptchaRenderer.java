package com.pigx.engine.assistant.captcha.renderer.hutool;

import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.CircleCaptcha;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/hutool/CircleCaptchaRenderer.class */
public class CircleCaptchaRenderer extends AbstractGraphicRenderer {
    public CircleCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        CircleCaptcha circleCaptcha = CaptchaUtil.ofCircleCaptcha(getWidth(), getHeight(), getLength(), 20);
        circleCaptcha.setFont(getFont());
        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(circleCaptcha.getImageBase64Data());
        metadata.setCharacters(circleCaptcha.getCode());
        return metadata;
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.HUTOOL_CIRCLE.getConstant();
    }
}
