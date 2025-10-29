package com.pigx.engine.assistant.captcha.renderer.hutool;

import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.GifCaptcha;
import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;


public class GifCaptchaRenderer extends AbstractGraphicRenderer {

    public GifCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override
    public Metadata draw() {
        GifCaptcha gifCaptcha = CaptchaUtil.ofGifCaptcha(this.getWidth(), this.getHeight(), this.getLength());
        gifCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(gifCaptcha.getImageBase64Data());
        metadata.setCharacters(gifCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_GIF.getConstant();
    }
}
