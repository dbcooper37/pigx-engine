package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import java.awt.image.BufferedImage;
import org.apache.commons.lang3.StringUtils;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/graphic/AbstractPngGraphicRenderer.class */
public abstract class AbstractPngGraphicRenderer extends AbstractBaseGraphicRenderer {
    protected AbstractPngGraphicRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        String[] drawCharacters = getDrawCharacters();
        BufferedImage bufferedImage = createPngBufferedImage(drawCharacters);
        String characters = StringUtils.join(drawCharacters, SymbolConstants.BLANK);
        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(toBase64(bufferedImage));
        metadata.setCharacters(characters);
        return metadata;
    }
}
