package com.pigx.engine.assistant.captcha.renderer.hutool;

import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.ShearCaptcha;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/hutool/ShearCaptchaRenderer.class */
public class ShearCaptchaRenderer extends AbstractGraphicRenderer {
    public ShearCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        ShearCaptcha shearCaptcha = CaptchaUtil.ofShearCaptcha(getWidth(), getHeight(), getLength(), 4);
        shearCaptcha.setFont(getFont());
        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(shearCaptcha.getImageBase64Data());
        metadata.setCharacters(shearCaptcha.getCode());
        return metadata;
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.HUTOOL_SHEAR.getConstant();
    }
}
