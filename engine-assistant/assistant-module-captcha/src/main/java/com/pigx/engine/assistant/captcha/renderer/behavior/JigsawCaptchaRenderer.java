package com.pigx.engine.assistant.captcha.renderer.behavior;

import com.pigx.engine.assistant.captcha.constant.CaptchaConstants;
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
import cn.hutool.v7.swing.img.ImgUtil;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/behavior/JigsawCaptchaRenderer.class */
public class JigsawCaptchaRenderer extends AbstractBehaviorRenderer<String, Coordinate> {
    private static final Logger log = LoggerFactory.getLogger(JigsawCaptchaRenderer.class);
    private static final int AREA_SIZE = 3;
    private static final int AREA_ARRAY_SIZE = 9;
    private static final int BOLD = 5;
    private static final int OFFSET = 100;
    private JigsawCaptcha jigsawCaptcha;

    public JigsawCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider, CaptchaConstants.CACHE_NAME_CAPTCHA_JIGSAW);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public String getCategory() {
        return CaptchaCategory.JIGSAW.getConstant();
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Captcha getCapcha(String key) {
        String identity = key;
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        }
        create(identity);
        return this.jigsawCaptcha;
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public Coordinate nextStamp(String key) {
        Metadata metadata = draw();
        JigsawCaptcha jigsawCaptcha = new JigsawCaptcha();
        jigsawCaptcha.setIdentity(key);
        jigsawCaptcha.setOriginalImageBase64(metadata.getOriginalImageBase64());
        jigsawCaptcha.setSliderImageBase64(metadata.getSliderImageBase64());
        this.jigsawCaptcha = jigsawCaptcha;
        return metadata.getCoordinate();
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public boolean verify(Verification verification) {
        if (ObjectUtils.isEmpty(verification) || ObjectUtils.isEmpty(verification.getCoordinate())) {
            throw new CaptchaParameterIllegalException("Parameter Stamp value is null");
        }
        Coordinate store = get(verification.getIdentity());
        if (ObjectUtils.isEmpty(store)) {
            throw new CaptchaHasExpiredException("Stamp is invalid!");
        }
        delete(verification.getIdentity());
        Coordinate real = verification.getCoordinate();
        if (isDeflected(real.getX(), store.getX(), getCaptchaProperties().getJigsaw().getDeviation().intValue()) || real.getY() != store.getY()) {
            throw new CaptchaMismatchException();
        }
        return true;
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        BufferedImage originalImage = getResourceProvider().getRandomOriginalImage();
        Graphics backgroundGraphics = originalImage.getGraphics();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        addWatermark(backgroundGraphics, width, height);
        String sliderImageBase64 = getResourceProvider().getRandomBase64TemplateImage();
        BufferedImage templateImage = ImgUtil.toImage(sliderImageBase64);
        return draw(originalImage, templateImage, sliderImageBase64);
    }

    private Metadata draw(BufferedImage originalImage, BufferedImage templateImage, String sliderImageBase64) {
        int originalImageWidth = originalImage.getWidth();
        int originalImageHeight = originalImage.getHeight();
        int templateImageWidth = templateImage.getWidth();
        int templateImageHeight = templateImage.getHeight();
        log.trace("[Herodotus] |- Jigsaw captcha original image width is [{}], height is [{}].", Integer.valueOf(originalImageWidth), Integer.valueOf(originalImageHeight));
        log.trace("[Herodotus] |- Jigsaw captcha template image width is [{}], height is [{}].", Integer.valueOf(templateImageWidth), Integer.valueOf(templateImageHeight));
        Coordinate coordinate = createImageMattingCoordinate(originalImageWidth, originalImageHeight, templateImageWidth, templateImageHeight);
        int x = coordinate.getX();
        coordinate.getY();
        Graphics2D graphics = new BufferedImage(templateImageWidth, templateImageHeight, templateImage.getType()).createGraphics();
        BufferedImage jigsawImage = graphics.getDeviceConfiguration().createCompatibleImage(templateImageWidth, templateImageHeight, AREA_SIZE);
        mattingByTemplate(originalImage, templateImage, jigsawImage, x, 0);
        int interferencePosition = createInterferencePosition(originalImageWidth, templateImageWidth, x);
        if (interferencePosition != 0) {
            addInterference(originalImage, sliderImageBase64, interferencePosition);
        }
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(5.0f, 0, 2));
        graphics.drawImage(jigsawImage, 0, 0, (ImageObserver) null);
        graphics.dispose();
        log.trace("[Herodotus] |- Jigsaw captcha jigsaw image width is [{}], height is [{}].", Integer.valueOf(jigsawImage.getWidth()), Integer.valueOf(jigsawImage.getHeight()));
        Metadata metadata = new Metadata();
        metadata.setOriginalImageBase64(toBase64(originalImage));
        metadata.setSliderImageBase64(toBase64(jigsawImage));
        metadata.setCoordinate(coordinate);
        return metadata;
    }

    private Coordinate createImageMattingCoordinate(int originalImageWidth, int originalImageHeight, int templateImageWidth, int templateImageHeight) {
        int availableWidth = originalImageWidth - templateImageWidth;
        int availableHeight = originalImageHeight - templateImageHeight;
        int x = BOLD;
        int y = BOLD;
        if (availableWidth > 0) {
            x = RandomProvider.randomInt(availableWidth - OFFSET) + OFFSET;
        }
        if (availableHeight > 0) {
            y = RandomProvider.randomInt(availableHeight) + BOLD;
        }
        log.debug("[Herodotus] |- Jigsaw captcha image matting coordinate is x: [{}], y: [{}].", Integer.valueOf(x), Integer.valueOf(y));
        return new Coordinate(x, y);
    }

    private void mattingByTemplate(BufferedImage originalImage, BufferedImage templateImage, BufferedImage jigsawImage, int x, int y) {
        int[][] matrix = new int[AREA_SIZE][AREA_SIZE];
        int[] values = new int[AREA_ARRAY_SIZE];
        int templateImageWidth = templateImage.getWidth();
        int templateImageHeight = templateImage.getHeight();
        for (int i = 0; i < templateImageWidth; i++) {
            for (int j = 0; j < templateImageHeight; j++) {
                int pixelX = x + i;
                int pixelY = y + j;
                int templateImageRgb = getImageRgb(templateImage, i, j);
                if (templateImageRgb < 0) {
                    jigsawImage.setRGB(i, j, getImageRgb(originalImage, pixelX, pixelY));
                    GaussianBlur.execute(originalImage, pixelX, pixelY, matrix, values, AREA_SIZE);
                }
                if (!isOutOfBound(i, j, templateImageWidth, templateImageHeight) && isCritical(templateImage, i, j, templateImageRgb)) {
                    jigsawImage.setRGB(i, j, Color.white.getRGB());
                    originalImage.setRGB(pixelX, pixelY, Color.white.getRGB());
                }
            }
        }
    }

    private int getImageRgb(BufferedImage bufferedImage, int i, int j) {
        return bufferedImage.getRGB(i, j);
    }

    private int getTemplateImageRightBorderRgb(BufferedImage templateImage, int i, int j) {
        return getImageRgb(templateImage, i + 1, j);
    }

    private int getTemplateImageBottomBorderRgb(BufferedImage templateImage, int i, int j) {
        return getImageRgb(templateImage, i, j + 1);
    }

    private boolean isOutOfBound(int x, int y, int templateImageWidth, int templateImageHeight) {
        return x == templateImageWidth - 1 || y == templateImageHeight - 1;
    }

    private boolean isPixelBoundary(int main, int boarder) {
        return main < 0 && boarder >= 0;
    }

    private boolean isNoPixelBoundary(int main, int boarder) {
        return main >= 0 && boarder < 0;
    }

    private boolean isBoundary(int main, int boarder) {
        return isNoPixelBoundary(main, boarder) || isPixelBoundary(main, boarder);
    }

    private boolean isCritical(BufferedImage templateImage, int x, int y, int baseRgb) {
        int rightBorderRgb = getTemplateImageRightBorderRgb(templateImage, x, y);
        int bottomBorderRgb = getTemplateImageBottomBorderRgb(templateImage, x, y);
        return isBoundary(baseRgb, rightBorderRgb) || isBoundary(baseRgb, bottomBorderRgb);
    }

    private int createInterferencePosition(int originalImageWidth, int templateImageWidth, int x) {
        int interferenceOptions = getCaptchaProperties().getJigsaw().getInterference().intValue();
        int position = 0;
        if (interferenceOptions > 0) {
            if ((originalImageWidth - x) - BOLD > templateImageWidth * 2) {
                position = RandomProvider.randomInt(x + templateImageWidth + BOLD, originalImageWidth - templateImageWidth);
            } else {
                position = RandomProvider.randomInt(OFFSET, (x - templateImageWidth) - BOLD);
            }
        }
        if (interferenceOptions > 1) {
            position = RandomProvider.randomInt(templateImageWidth, OFFSET - templateImageWidth);
        }
        return position;
    }

    private void addInterference(BufferedImage originalImage, String sliderImageBase64, int position) {
        String data;
        do {
            data = getResourceProvider().getRandomBase64TemplateImage();
        } while (sliderImageBase64.equals(data));
        interferenceByTemplate(originalImage, (BufferedImage) Objects.requireNonNull(ImgUtil.toImage(data)), position, 0);
    }

    private void interferenceByTemplate(BufferedImage originalImage, BufferedImage templateImage, int x, int y) {
        int[][] matrix = new int[AREA_SIZE][AREA_SIZE];
        int[] values = new int[AREA_ARRAY_SIZE];
        int templateImageWidth = templateImage.getWidth();
        int templateImageHeight = templateImage.getHeight();
        for (int i = 0; i < templateImageWidth; i++) {
            for (int j = 0; j < templateImageHeight; j++) {
                int pixelX = x + i;
                int pixelY = y + j;
                int templateImageRgb = getImageRgb(templateImage, i, j);
                if (templateImageRgb < 0) {
                    GaussianBlur.execute(originalImage, pixelX, pixelY, matrix, values, AREA_SIZE);
                }
                if (!isOutOfBound(i, j, templateImageWidth, templateImageHeight) && isCritical(templateImage, i, j, templateImageRgb)) {
                    originalImage.setRGB(pixelX, pixelY, Color.white.getRGB());
                }
            }
        }
    }
}
