package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import java.awt.Font;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/graphic/ChineseGifCaptchaRenderer.class */
public class ChineseGifCaptchaRenderer extends AbstractGifGraphicRenderer {
    public ChineseGifCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.CHINESE_GIF.getConstant();
    }

    @Override // com.pigx.engine.assistant.captcha.renderer.graphic.AbstractBaseGraphicRenderer
    protected String[] getDrawCharacters() {
        return getWordCharacters();
    }

    @Override // com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer
    protected Font getFont() {
        return getResourceProvider().getChineseFont();
    }
}
