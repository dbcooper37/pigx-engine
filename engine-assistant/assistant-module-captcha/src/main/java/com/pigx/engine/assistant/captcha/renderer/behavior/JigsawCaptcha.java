package com.pigx.engine.assistant.captcha.renderer.behavior;

import com.pigx.engine.core.definition.domain.captcha.Captcha;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/behavior/JigsawCaptcha.class */
public class JigsawCaptcha extends Captcha {
    private String originalImageBase64;
    private String sliderImageBase64;

    public String getOriginalImageBase64() {
        return this.originalImageBase64;
    }

    public void setOriginalImageBase64(String originalImageBase64) {
        this.originalImageBase64 = originalImageBase64;
    }

    public String getSliderImageBase64() {
        return this.sliderImageBase64;
    }

    public void setSliderImageBase64(String sliderImageBase64) {
        this.sliderImageBase64 = sliderImageBase64;
    }
}
