package com.pigx.engine.assistant.captcha.enums;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/enums/FontStyle.class */
public enum FontStyle {
    PLAIN(0),
    BOLD(1),
    ITALIC(2);

    private final int mapping;

    FontStyle(int mapping) {
        this.mapping = mapping;
    }

    public int getMapping() {
        return this.mapping;
    }
}
