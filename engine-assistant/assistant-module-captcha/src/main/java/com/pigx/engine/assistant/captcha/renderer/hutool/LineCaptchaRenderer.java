package com.pigx.engine.assistant.captcha.renderer.hutool;

import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.LineCaptcha;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/hutool/LineCaptchaRenderer.class */
public class LineCaptchaRenderer extends AbstractGraphicRenderer {
    public LineCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        LineCaptcha lineCaptcha = CaptchaUtil.ofLineCaptcha(getWidth(), getHeight(), getLength(), 150);
        lineCaptcha.setFont(getFont());
        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(lineCaptcha.getImageBase64Data());
        metadata.setCharacters(lineCaptcha.getCode());
        return metadata;
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.HUTOOL_LINE.getConstant();
    }
}
