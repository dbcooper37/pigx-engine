package com.pigx.engine.assistant.captcha.enums;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/enums/CaptchaResource.class */
public enum CaptchaResource {
    JIGSAW_ORIGINAL("Jigsaw original image", "滑动拼图底图"),
    JIGSAW_TEMPLATE("Jigsaw template image", "滑动拼图滑块底图"),
    WORD_CLICK("Word click image", "文字点选底图");

    private final String content;
    private final String description;

    CaptchaResource(String type, String description) {
        this.content = type;
        this.description = description;
    }

    public String getContent() {
        return this.content;
    }

    public String getDescription() {
        return this.description;
    }
}
