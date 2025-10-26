package com.pigx.engine.assistant.captcha.renderer.behavior;

import com.pigx.engine.core.definition.domain.captcha.Captcha;
import com.google.common.base.MoreObjects;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/behavior/WordClickCaptcha.class */
public class WordClickCaptcha extends Captcha {
    private String wordClickImageBase64;
    private String words;
    private Integer wordsCount;

    public String getWordClickImageBase64() {
        return this.wordClickImageBase64;
    }

    public void setWordClickImageBase64(String wordClickImageBase64) {
        this.wordClickImageBase64 = wordClickImageBase64;
    }

    public String getWords() {
        return this.words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Integer getWordsCount() {
        return this.wordsCount;
    }

    public void setWordsCount(Integer wordsCount) {
        this.wordsCount = wordsCount;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("wordClickImageBase64", this.wordClickImageBase64).add("words", this.words).add("wordsCount", this.wordsCount).toString();
    }
}
