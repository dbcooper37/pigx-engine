package com.pigx.engine.assistant.captcha.renderer.behavior;

import com.pigx.engine.assistant.captcha.constant.CaptchaConstants;
import com.pigx.engine.assistant.captcha.enums.FontStyle;
import com.pigx.engine.assistant.captcha.provider.RandomProvider;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Captcha;
import com.pigx.engine.core.definition.domain.captcha.Coordinate;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.definition.domain.captcha.Verification;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaHasExpiredException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaMismatchException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaParameterIllegalException;
import cn.hutool.v7.core.data.id.IdUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/behavior/WordClickCaptchaRenderer.class */
public class WordClickCaptchaRenderer extends AbstractBehaviorRenderer<String, List<Coordinate>> {
    private WordClickCaptcha wordClickCaptcha;

    public WordClickCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider, CaptchaConstants.CACHE_NAME_CAPTCHA_WORD_CLICK);
    }

    private Font getFont() {
        int fontSize = getCaptchaProperties().getWordClick().getFontSize().intValue();
        String fontName = getCaptchaProperties().getWordClick().getFontName();
        FontStyle fontStyle = getCaptchaProperties().getWordClick().getFontStyle();
        return getResourceProvider().getFont(fontName, fontSize, fontStyle);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.WORD_CLICK.getConstant();
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public List<Coordinate> nextStamp(String key) {
        Metadata metadata = draw();
        WordClickObfuscator wordClickObfuscator = new WordClickObfuscator(metadata.getWords(), metadata.getCoordinates());
        WordClickCaptcha wordClickCaptcha = new WordClickCaptcha();
        wordClickCaptcha.setIdentity(key);
        wordClickCaptcha.setWordClickImageBase64(metadata.getWordClickImageBase64());
        wordClickCaptcha.setWords(wordClickObfuscator.getWordString());
        wordClickCaptcha.setWordsCount(Integer.valueOf(metadata.getWords().size()));
        this.wordClickCaptcha = wordClickCaptcha;
        return wordClickObfuscator.getCoordinates();
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Captcha getCapcha(String key) {
        String identity = key;
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        }
        create(identity);
        return this.wordClickCaptcha;
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public boolean verify(Verification verification) {
        if (ObjectUtils.isEmpty(verification) || CollectionUtils.isEmpty(verification.getCoordinates())) {
            throw new CaptchaParameterIllegalException("Parameter Stamp value is null");
        }
        List<Coordinate> store = get(verification.getIdentity());
        if (CollectionUtils.isEmpty(store)) {
            throw new CaptchaHasExpiredException("Stamp is invalid!");
        }
        delete(verification.getIdentity());
        List<Coordinate> real = verification.getCoordinates();
        for (int i = 0; i < store.size(); i++) {
            if (isDeflected(real.get(i).getX(), store.get(i).getX(), getFontSize()) || isDeflected(real.get(i).getX(), store.get(i).getX(), getFontSize())) {
                throw new CaptchaMismatchException();
            }
        }
        return true;
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        BufferedImage backgroundImage = getResourceProvider().getRandomWordClickImage();
        int wordCount = getCaptchaProperties().getWordClick().getWordCount().intValue();
        List<String> words = RandomProvider.randomWords(wordCount);
        Graphics backgroundGraphics = backgroundImage.getGraphics();
        int backgroundImageWidth = backgroundImage.getWidth();
        int backgroundImageHeight = backgroundImage.getHeight();
        List<Coordinate> coordinates = (List) IntStream.range(0, words.size()).mapToObj(index -> {
            return drawWord(backgroundGraphics, backgroundImageWidth, backgroundImageHeight, index, wordCount, (String) words.get(index));
        }).collect(Collectors.toList());
        addWatermark(backgroundGraphics, backgroundImageWidth, backgroundImageHeight);
        BufferedImage combinedImage = new BufferedImage(backgroundImageWidth, backgroundImageHeight, 1);
        Graphics combinedGraphics = combinedImage.getGraphics();
        combinedGraphics.drawImage(backgroundImage, 0, 0, (ImageObserver) null);
        int excludeWordIndex = RandomProvider.randomInt(1, wordCount) - 1;
        words.remove(excludeWordIndex);
        coordinates.remove(excludeWordIndex);
        Metadata metadata = new Metadata();
        metadata.setWordClickImageBase64(toBase64(backgroundImage));
        metadata.setCoordinates(coordinates);
        metadata.setWords(words);
        return metadata;
    }

    private Coordinate drawWord(Graphics graphics, int width, int height, int index, int wordCount, String word) {
        Coordinate coordinate = randomWordCoordinate(width, height, index, wordCount);
        if (getCaptchaProperties().getWordClick().isRandomColor()) {
            graphics.setColor(new Color(RandomProvider.randomInt(1, 255), RandomProvider.randomInt(1, 255), RandomProvider.randomInt(1, 255)));
        } else {
            graphics.setColor(Color.BLACK);
        }
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(RandomProvider.randomInt(-45, 45)), 0.0d, 0.0d);
        Font rotatedFont = getFont().deriveFont(affineTransform);
        graphics.setFont(rotatedFont);
        graphics.drawString(word, coordinate.getX(), coordinate.getY());
        return coordinate;
    }

    private int getFontSize() {
        return getCaptchaProperties().getWordClick().getFontSize().intValue();
    }

    private int getHalfFontSize() {
        return getFontSize() / 2;
    }

    private Coordinate randomWordCoordinate(int backgroundImageWidth, int backgroundImageHeight, int wordIndex, int wordCount) {
        int x;
        int wordSize = getFontSize();
        int halfWordSize = getHalfFontSize();
        int averageWidth = backgroundImageWidth / (wordCount + 1);
        if (averageWidth < halfWordSize) {
            x = RandomProvider.randomInt(getStartInclusive(halfWordSize), backgroundImageWidth);
        } else if (wordIndex == 0) {
            x = RandomProvider.randomInt(getStartInclusive(halfWordSize), getEndExclusive(wordIndex, averageWidth, halfWordSize));
        } else {
            x = RandomProvider.randomInt((averageWidth * wordIndex) + halfWordSize, getEndExclusive(wordIndex, averageWidth, halfWordSize));
        }
        int y = RandomProvider.randomInt(wordSize, backgroundImageHeight - wordSize);
        return new Coordinate(x, y);
    }

    private int getStartInclusive(int halfWordSize) {
        return 1 + halfWordSize;
    }

    private int getEndExclusive(int wordIndex, int averageWidth, int halfWordSize) {
        return (averageWidth * (wordIndex + 1)) - halfWordSize;
    }
}
