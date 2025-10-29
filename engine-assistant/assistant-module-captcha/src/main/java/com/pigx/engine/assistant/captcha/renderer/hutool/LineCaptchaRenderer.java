package com.pigx.engine.assistant.captcha.renderer.hutool;

import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.LineCaptcha;
import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;


public class LineCaptchaRenderer extends AbstractGraphicRenderer {

    public LineCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override
    public Metadata draw() {
        LineCaptcha lineCaptcha = CaptchaUtil.ofLineCaptcha(this.getWidth(), this.getHeight(), this.getLength(), 150);
        lineCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(lineCaptcha.getImageBase64Data());
        metadata.setCharacters(lineCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_LINE.getConstant();
    }
}
