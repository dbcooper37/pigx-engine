package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;

import java.awt.*;


public class ChineseGifCaptchaRenderer extends AbstractGifGraphicRenderer {

    public ChineseGifCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.CHINESE_GIF.getConstant();
    }

    @Override
    protected String[] getDrawCharacters() {
        return this.getWordCharacters();
    }

    @Override
    protected Font getFont() {
        return this.getResourceProvider().getChineseFont();
    }
}
