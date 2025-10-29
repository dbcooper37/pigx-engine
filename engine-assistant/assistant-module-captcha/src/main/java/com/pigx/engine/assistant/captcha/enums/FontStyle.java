package com.pigx.engine.assistant.captcha.enums;

import java.awt.*;


public enum FontStyle {

    /**
     * 字体风格
     */
    PLAIN(Font.PLAIN),
    BOLD(Font.BOLD),
    ITALIC(Font.ITALIC);

    private final int mapping;

    FontStyle(int mapping) {
        this.mapping = mapping;
    }

    public int getMapping() {
        return mapping;
    }
}
