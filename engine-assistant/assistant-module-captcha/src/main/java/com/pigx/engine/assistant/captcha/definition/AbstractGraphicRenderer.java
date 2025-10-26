package com.pigx.engine.assistant.captcha.definition;

import com.pigx.engine.assistant.captcha.constant.CaptchaConstants;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.core.definition.domain.captcha.Captcha;
import com.pigx.engine.core.definition.domain.captcha.GraphicCaptcha;
import com.pigx.engine.core.definition.domain.captcha.Metadata;
import com.pigx.engine.core.definition.domain.captcha.Verification;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaHasExpiredException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaIsEmptyException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaMismatchException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaParameterIllegalException;
import cn.hutool.v7.core.data.id.IdUtil;
import java.awt.Font;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/definition/AbstractGraphicRenderer.class */
public abstract class AbstractGraphicRenderer extends AbstractRenderer<String, String> {
    private GraphicCaptcha graphicCaptcha;

    protected AbstractGraphicRenderer(ResourceProvider resourceProvider) {
        super(resourceProvider, CaptchaConstants.CACHE_NAME_CAPTCHA_GRAPHIC);
    }

    protected Font getFont() {
        return getResourceProvider().getGraphicFont();
    }

    protected int getWidth() {
        return getCaptchaProperties().getGraphics().getWidth();
    }

    protected int getHeight() {
        return getCaptchaProperties().getGraphics().getHeight();
    }

    protected int getLength() {
        return getCaptchaProperties().getGraphics().getLength();
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public Captcha getCapcha(String key) {
        String identity = key;
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        }
        create(identity);
        return getGraphicCaptcha();
    }

    @Override // com.pigx.engine.core.definition.support.CaptchaRenderer
    public boolean verify(Verification verification) {
        if (ObjectUtils.isEmpty(verification) || StringUtils.isEmpty(verification.getIdentity())) {
            throw new CaptchaParameterIllegalException("Parameter value is illegal");
        }
        if (StringUtils.isEmpty(verification.getCharacters())) {
            throw new CaptchaIsEmptyException("Captcha is empty");
        }
        String store = get(verification.getIdentity());
        if (StringUtils.isEmpty(store)) {
            throw new CaptchaHasExpiredException("Stamp is invalid!");
        }
        delete(verification.getIdentity());
        String real = verification.getCharacters();
        if (!Strings.CI.equals(store, real)) {
            throw new CaptchaMismatchException();
        }
        return true;
    }

    private GraphicCaptcha getGraphicCaptcha() {
        return this.graphicCaptcha;
    }

    protected void setGraphicCaptcha(GraphicCaptcha graphicCaptcha) {
        this.graphicCaptcha = graphicCaptcha;
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public String nextStamp(String key) {
        Metadata metadata = draw();
        GraphicCaptcha graphicCaptcha = new GraphicCaptcha();
        graphicCaptcha.setIdentity(key);
        graphicCaptcha.setGraphicImageBase64(metadata.getGraphicImageBase64());
        graphicCaptcha.setCategory(getCategory());
        setGraphicCaptcha(graphicCaptcha);
        return metadata.getCharacters();
    }
}
