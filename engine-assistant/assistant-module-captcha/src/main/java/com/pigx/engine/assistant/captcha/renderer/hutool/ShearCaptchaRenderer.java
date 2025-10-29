package com.pigx.engine.assistant.captcha.renderer.hutool;

import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.ShearCaptcha;
import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;


public class ShearCaptchaRenderer extends AbstractGraphicRenderer {

    public ShearCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override
    public Metadata draw() {
        ShearCaptcha shearCaptcha = CaptchaUtil.ofShearCaptcha(this.getWidth(), this.getHeight(), this.getLength(), 4);
        shearCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(shearCaptcha.getImageBase64Data());
        metadata.setCharacters(shearCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_SHEAR.getConstant();
    }
}
