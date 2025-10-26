package com.pigx.engine.assistant.captcha.renderer.behavior;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.captcha.Coordinate;
import cn.hutool.v7.core.util.RandomUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/behavior/WordClickObfuscator.class */
public class WordClickObfuscator {
    private final List<Coordinate> coordinates = new ArrayList();
    private final List<String> words = new ArrayList();
    private String wordString;

    public WordClickObfuscator(List<String> originalWords, List<Coordinate> originalCoordinates) {
        execute(originalWords, originalCoordinates);
    }

    private void execute(List<String> originalWords, List<Coordinate> originalCoordinates) {
        int[] indexes = RandomUtil.randomInts(originalWords.size());
        Arrays.stream(indexes).forEach(value -> {
            this.words.add(this.words.size(), (String) originalWords.get(value));
            this.coordinates.add(this.coordinates.size(), (Coordinate) originalCoordinates.get(value));
        });
        this.wordString = StringUtils.join(getWords(), SymbolConstants.COMMA);
    }

    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

    public List<String> getWords() {
        return this.words;
    }

    public String getWordString() {
        return this.wordString;
    }
}
