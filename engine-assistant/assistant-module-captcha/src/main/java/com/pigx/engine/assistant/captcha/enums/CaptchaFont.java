package com.pigx.engine.assistant.captcha.enums;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/enums/CaptchaFont.class */
public enum CaptchaFont {
    ACTION("Action.ttf"),
    BEATAE("Beatae.ttf"),
    EPILOG("Epilog.ttf"),
    FRESNEL("Fresnel.ttf"),
    HEADACHE("Headache.ttf"),
    LEXOGRAPHER("Lexographer.ttf"),
    PREFIX("Prefix.ttf"),
    PROG_BOT("ProgBot.ttf"),
    ROBOT_TEACHER("RobotTeacher.ttf"),
    SCANDAL("Scandal.ttf");

    private final String fontName;

    CaptchaFont(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return this.fontName;
    }
}
