package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.RandomProvider;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.constant.RegexPool;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.image.BufferedImage;


public class ArithmeticCaptchaRenderer extends AbstractBaseGraphicRenderer {

    private static final Logger log = LoggerFactory.getLogger(ArithmeticCaptchaRenderer.class);

    private final int complexity;
    /**
     * 计算结果
     */
    private String computedResult;

    public ArithmeticCaptchaRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
        this.complexity = this.getCaptchaProperties().getGraphics().getComplexity();
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.ARITHMETIC.getConstant();
    }

    @Override
    protected String[] getDrawCharacters() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < complexity; i++) {
            builder.append(RandomProvider.randomInt(10));
            if (i < complexity - 1) {
                int type = RandomProvider.randomInt(1, 4);
                if (type == 1) {
                    builder.append("+");
                } else if (type == 2) {
                    builder.append("-");
                } else if (type == 3) {
                    builder.append("x");
                }
            }
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        try {
            computedResult = String.valueOf(engine.eval(builder.toString().replaceAll("x", "*")));
        } catch (ScriptException e) {
            log.error("[PIGXD] |- Arithmetic png captcha eval expression error！", e);
        }

        builder.append("=?");

        String result = builder.toString();
        return result.split(RegexPool.ALL_CHARACTERS);
    }

    @Override
    public Metadata draw() {
        String[] drawContent = getDrawCharacters();
        BufferedImage bufferedImage = createArithmeticBufferedImage(drawContent);

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(toBase64(bufferedImage));
        metadata.setCharacters(this.computedResult);
        return metadata;
    }
}
