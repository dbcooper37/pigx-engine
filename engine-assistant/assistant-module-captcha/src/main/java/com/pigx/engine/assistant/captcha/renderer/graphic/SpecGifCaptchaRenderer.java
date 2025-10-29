package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;


public class SpecGifCaptchaRenderer extends AbstractGifGraphicRenderer {

    public SpecGifCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.SPEC_GIF.getConstant();
    }

    @Override
    protected String[] getDrawCharacters() {
        return this.getCharCharacters();
    }
}
