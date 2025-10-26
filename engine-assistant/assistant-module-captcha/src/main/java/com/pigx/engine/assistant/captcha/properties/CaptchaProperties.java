package com.pigx.engine.assistant.captcha.properties;

import com.pigx.engine.assistant.captcha.constant.CaptchaConstants;
import com.pigx.engine.assistant.captcha.enums.CaptchaCharacter;
import com.pigx.engine.assistant.captcha.enums.CaptchaFont;
import com.pigx.engine.assistant.captcha.enums.FontStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = CaptchaConstants.PROPERTY_ASSISTANT_CAPTCHA)
/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/properties/CaptchaProperties.class */
public class CaptchaProperties {
    private Graphics graphics = new Graphics();
    private Watermark watermark = new Watermark();
    private Jigsaw jigsaw = new Jigsaw();
    private WordClick wordClick = new WordClick();

    public Graphics getGraphics() {
        return this.graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public Watermark getWatermark() {
        return this.watermark;
    }

    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }

    public Jigsaw getJigsaw() {
        return this.jigsaw;
    }

    public void setJigsaw(Jigsaw jigsaw) {
        this.jigsaw = jigsaw;
    }

    public WordClick getWordClick() {
        return this.wordClick;
    }

    public void setWordClick(WordClick wordClick) {
        this.wordClick = wordClick;
    }

    /* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/properties/CaptchaProperties$Graphics.class */
    public static class Graphics {
        private int length = 5;
        private int width = 130;
        private int height = 48;
        private int complexity = 2;
        private CaptchaCharacter letter = CaptchaCharacter.NUM_AND_CHAR;
        private CaptchaFont font = CaptchaFont.LEXOGRAPHER;

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public CaptchaFont getFont() {
            return this.font;
        }

        public void setFont(CaptchaFont captchaFont) {
            this.font = captchaFont;
        }

        public CaptchaCharacter getLetter() {
            return this.letter;
        }

        public void setLetter(CaptchaCharacter letter) {
            this.letter = letter;
        }

        public int getComplexity() {
            return this.complexity;
        }

        public void setComplexity(int complexity) {
            this.complexity = complexity;
        }
    }

    /* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/properties/CaptchaProperties$Watermark.class */
    public static class Watermark {
        private String content = "PigX Cloud";
        private String fontName = "WenQuanZhengHei.ttf";
        private FontStyle fontStyle = FontStyle.BOLD;
        private Integer fontSize = 25;

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFontName() {
            return this.fontName;
        }

        public void setFontName(String fontName) {
            this.fontName = fontName;
        }

        public Integer getFontSize() {
            return this.fontSize;
        }

        public void setFontSize(Integer fontSize) {
            this.fontSize = fontSize;
        }

        public FontStyle getFontStyle() {
            return this.fontStyle;
        }

        public void setFontStyle(FontStyle fontStyle) {
            this.fontStyle = fontStyle;
        }
    }

    /* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/properties/CaptchaProperties$Jigsaw.class */
    public static class Jigsaw {
        private String originalResource = "classpath*:images/jigsaw/original/*.png";
        private String templateResource = "classpath*:images/jigsaw/template/*.png";
        private Integer interference = 0;
        private Integer deviation = 5;

        public String getOriginalResource() {
            return this.originalResource;
        }

        public void setOriginalResource(String originalResource) {
            this.originalResource = originalResource;
        }

        public String getTemplateResource() {
            return this.templateResource;
        }

        public void setTemplateResource(String templateResource) {
            this.templateResource = templateResource;
        }

        public Integer getInterference() {
            return this.interference;
        }

        public void setInterference(Integer interference) {
            this.interference = interference;
        }

        public Integer getDeviation() {
            return this.deviation;
        }

        public void setDeviation(Integer deviation) {
            this.deviation = deviation;
        }
    }

    /* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/properties/CaptchaProperties$WordClick.class */
    public static class WordClick {
        private String imageResource = "classpath*:images/word-click/*.png";
        private Integer wordCount = 5;
        private boolean randomColor = true;
        private FontStyle fontStyle = FontStyle.BOLD;
        private String fontName = "WenQuanZhengHei.ttf";
        private Integer fontSize = 25;

        public String getImageResource() {
            return this.imageResource;
        }

        public void setImageResource(String imageResource) {
            this.imageResource = imageResource;
        }

        public Integer getWordCount() {
            return this.wordCount;
        }

        public void setWordCount(Integer wordCount) {
            this.wordCount = wordCount;
        }

        public Integer getFontSize() {
            return this.fontSize;
        }

        public void setFontSize(Integer fontSize) {
            this.fontSize = fontSize;
        }

        public boolean isRandomColor() {
            return this.randomColor;
        }

        public void setRandomColor(boolean randomColor) {
            this.randomColor = randomColor;
        }

        public String getFontName() {
            return this.fontName;
        }

        public void setFontName(String fontName) {
            this.fontName = fontName;
        }

        public FontStyle getFontStyle() {
            return this.fontStyle;
        }

        public void setFontStyle(FontStyle fontStyle) {
            this.fontStyle = fontStyle;
        }
    }
}
