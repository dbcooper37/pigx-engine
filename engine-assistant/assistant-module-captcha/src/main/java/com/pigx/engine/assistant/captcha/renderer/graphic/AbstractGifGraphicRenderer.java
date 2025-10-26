package com.pigx.engine.assistant.captcha.renderer.graphic;

import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import cn.hutool.v7.core.codec.binary.Base64;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/renderer/graphic/AbstractGifGraphicRenderer.class */
public abstract class AbstractGifGraphicRenderer extends AbstractBaseGraphicRenderer {
    protected AbstractGifGraphicRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider);
    }

    @Override // com.pigx.engine.assistant.captcha.definition.AbstractRenderer
    protected String getBase64ImagePrefix() {
        return "data:image/gif;base64,";
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Metadata draw() {
        String[] drawCharacters = getDrawCharacters();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
        gifEncoder.start(out);
        gifEncoder.setQuality(180);
        gifEncoder.setDelay(100);
        gifEncoder.setRepeat(0);
        IntStream.range(0, drawCharacters.length).forEach(i -> {
            BufferedImage frame = createGifBufferedImage(drawCharacters, i);
            gifEncoder.addFrame(frame);
            frame.flush();
        });
        gifEncoder.finish();
        String characters = StringUtils.join(drawCharacters, SymbolConstants.BLANK);
        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(getBase64ImagePrefix() + Base64.encode(out.toByteArray()));
        metadata.setCharacters(characters);
        return metadata;
    }
}
