package com.pigx.engine.assistant.captcha.renderer.behavior;

import com.pigx.engine.core.definition.domain.captcha.Captcha;


public class JigsawCaptcha extends Captcha {

    private String originalImageBase64;

    private String sliderImageBase64;

    public String getOriginalImageBase64() {
        return originalImageBase64;
    }

    public void setOriginalImageBase64(String originalImageBase64) {
        this.originalImageBase64 = originalImageBase64;
    }

    public String getSliderImageBase64() {
        return sliderImageBase64;
    }

    public void setSliderImageBase64(String sliderImageBase64) {
        this.sliderImageBase64 = sliderImageBase64;
    }
}
