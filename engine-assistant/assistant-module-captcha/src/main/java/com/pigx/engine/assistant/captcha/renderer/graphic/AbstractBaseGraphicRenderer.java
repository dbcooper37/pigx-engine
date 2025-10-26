package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.definition.AbstractGraphicRenderer;
import com.pigx.engine.assistant.captcha.enums.CaptchaCharacter;
import com.pigx.engine.assistant.captcha.provider.RandomProvider;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.List;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/graphic/AbstractBaseGraphicRenderer.class */
public abstract class AbstractBaseGraphicRenderer extends AbstractGraphicRenderer {
    protected abstract String[] getDrawCharacters();

    protected AbstractBaseGraphicRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    protected String[] getWordCharacters() {
        int number = getCaptchaProperties().getGraphics().getLength();
        List<String> words = RandomProvider.randomWords(number);
        String[] content = new String[words.size()];
        return (String[]) words.toArray(content);
    }

    protected String[] getCharCharacters() {
        int number = getCaptchaProperties().getGraphics().getLength();
        CaptchaCharacter captchaCharacter = getCaptchaProperties().getGraphics().getLetter();
        return RandomProvider.randomCharacters(number, captchaCharacter);
    }

    private BufferedImage createPngBufferedImage(String[] characters, String benchmark, boolean isArithmetic) {
        return createBufferedImage(characters, benchmark, isArithmetic, false, 0);
    }

    protected BufferedImage createPngBufferedImage(String[] characters) {
        return createPngBufferedImage(characters, "W", false);
    }

    protected BufferedImage createArithmeticBufferedImage(String[] characters) {
        return createPngBufferedImage(characters, "8", true);
    }

    protected BufferedImage createGifBufferedImage(String[] characters, int alpha) {
        return createBufferedImage(characters, "王", false, true, alpha);
    }

    private BufferedImage createBufferedImage(String[] characters, String benchmark, boolean isArithmetic, boolean isGif, int alpha) {
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), 1);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!isArithmetic) {
            drawInterfereLine(graphics, isGif);
        }
        Color[] colors = RandomProvider.randomColors(characters.length);
        drawCharacter(graphics, characters, colors, benchmark, isGif, alpha);
        graphics.dispose();
        return bufferedImage;
    }

    private void drawColor(Graphics2D graphics) {
        graphics.setColor(RandomProvider.randomColor());
    }

    private void drawAlpha(Graphics2D graphics, float alpha) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(3, alpha);
        graphics.setComposite(alphaComposite);
    }

    private void drawAlphaForLine(Graphics2D graphics) {
        drawAlpha(graphics, 0.7f);
    }

    private float getAlpha(int length, int alpha, int index) {
        int num = alpha + index;
        float r = 1.0f / length;
        float s = (length + 1) * r;
        return num > length ? (num * r) - s : num * r;
    }

    private void drawAlphaForCharacter(Graphics2D graphics, int length, int alpha, int index) {
        drawAlpha(graphics, getAlpha(length, alpha, index));
    }

    private int randomCtrlX() {
        return RandomProvider.randomInt(getWidth() / 4, (getWidth() / 4) * 3);
    }

    private int randomCtrlY() {
        return RandomProvider.randomInt(5, getHeight() - 5);
    }

    private void drawBezierCurve(Graphics2D graphics) {
        drawColor(graphics);
        int y1 = RandomProvider.randomInt(5, getHeight() / 2);
        int x2 = getWidth() - 5;
        int y2 = RandomProvider.randomInt(getHeight() / 2, getHeight() - 5);
        int ctrlx1 = randomCtrlX();
        int ctrly1 = randomCtrlY();
        if (RandomProvider.randomInt(2) == 0) {
            y1 = y2;
            y2 = y1;
        }
        if (RandomProvider.randomInt(2) == 0) {
            QuadCurve2D.Double r0 = new QuadCurve2D.Double();
            r0.setCurve(5, y1, ctrlx1, ctrly1, x2, y2);
            graphics.draw(r0);
        } else {
            int ctrlx2 = randomCtrlX();
            int ctrly2 = randomCtrlY();
            graphics.draw(new CubicCurve2D.Double(5, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2));
        }
    }

    private void drawInterfereLine(Graphics2D graphics, boolean isGif) {
        if (isGif) {
            drawAlphaForLine(graphics);
        }
        graphics.setStroke(new BasicStroke(1.2f, 0, 2));
        drawBezierCurve(graphics);
    }

    private void drawOval(Graphics2D graphics) {
        int x = RandomProvider.randomInt(getWidth() - 5);
        int y = RandomProvider.randomInt(getHeight() - 5);
        int width = RandomProvider.randomInt(5, 30);
        int height = 5 + RandomProvider.randomInt(5, 30);
        graphics.drawOval(x, y, width, height);
    }

    private void drawCharacter(Graphics2D graphics, String[] characters, Color[] colors, String benchmark, boolean isGif, int alpha) {
        graphics.setFont(getFont());
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int fW = getWidth() / characters.length;
        int fSp = (fW - ((int) fontMetrics.getStringBounds(benchmark, graphics).getWidth())) / 2;
        for (int i = 0; i < characters.length; i++) {
            if (isGif) {
                drawAlphaForCharacter(graphics, characters.length, alpha, i);
            }
            graphics.setColor(colors[i]);
            drawOval(graphics);
            int fY = getHeight() - ((getHeight() - ((int) fontMetrics.getStringBounds(String.valueOf(characters[i]), graphics).getHeight())) >> 1);
            graphics.drawString(characters[i], ((i * fW) + fSp) - 3, fY - 3);
        }
    }
}
