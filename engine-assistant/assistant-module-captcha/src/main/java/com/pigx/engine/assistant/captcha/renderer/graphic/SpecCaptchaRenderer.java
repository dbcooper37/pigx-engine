package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/graphic/SpecCaptchaRenderer.class */
public class SpecCaptchaRenderer extends AbstractPngGraphicRenderer {
    public SpecCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.SPEC.getConstant();
    }

    @Override // com.pigx.engine.assistant.captcha.renderer.graphic.AbstractBaseGraphicRenderer
    protected String[] getDrawCharacters() {
        return getCharCharacters();
    }
}
