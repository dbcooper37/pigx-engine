package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import org.apache.commons.lang3.StringUtils;

import java.awt.image.BufferedImage;


public abstract class AbstractPngGraphicRenderer extends AbstractBaseGraphicRenderer {

    protected AbstractPngGraphicRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override
    public Metadata draw() {
        String[] drawCharacters = this.getDrawCharacters();

        BufferedImage bufferedImage = createPngBufferedImage(drawCharacters);

        String characters = StringUtils.join(drawCharacters, SymbolConstants.BLANK);

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(toBase64(bufferedImage));
        metadata.setCharacters(characters);

        return metadata;
    }
}
