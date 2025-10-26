package com.pigx.engine.assistant.captcha.renderer.hutool;

import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.GifCaptcha;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/hutool/GifCaptchaRenderer.class */
public class GifCaptchaRenderer extends AbstractGraphicRenderer {
    public GifCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        GifCaptcha gifCaptcha = CaptchaUtil.ofGifCaptcha(getWidth(), getHeight(), getLength());
        gifCaptcha.setFont(getFont());
        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(gifCaptcha.getImageBase64Data());
        metadata.setCharacters(gifCaptcha.getCode());
        return metadata;
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.HUTOOL_GIF.getConstant();
    }
}
