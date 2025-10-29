package com.pigx.engine.assistant.captcha.enums;


public enum CaptchaFont {
    /**
     * 内置字体类型
     */
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
        return fontName;
    }
}
