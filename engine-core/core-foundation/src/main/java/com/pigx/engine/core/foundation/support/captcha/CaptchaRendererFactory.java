package com.pigx.engine.core.foundation.support.captcha;

import com.pigx.engine.core.definition.domain.captcha.Captcha;
import com.pigx.engine.core.definition.domain.captcha.Verification;
import com.pigx.engine.core.definition.support.CaptchaRenderer;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaCategoryIsIncorrectException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaHandlerNotExistException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/support/captcha/CaptchaRendererFactory.class */
public class CaptchaRendererFactory {

    @Autowired
    private final Map<String, CaptchaRenderer> handlers = new ConcurrentHashMap(8);

    public CaptchaRenderer getRenderer(String category) {
        CaptchaCategory captchaCategory = CaptchaCategory.getCaptchaCategory(category);
        if (ObjectUtils.isEmpty(captchaCategory)) {
            throw new CaptchaCategoryIsIncorrectException("Captcha category is incorrect.");
        }
        CaptchaRenderer captchaRenderer = this.handlers.get(captchaCategory.getConstant());
        if (ObjectUtils.isEmpty(captchaRenderer)) {
            throw new CaptchaHandlerNotExistException();
        }
        return captchaRenderer;
    }

    public Captcha getCaptcha(String identity, String category) {
        CaptchaRenderer captchaRenderer = getRenderer(category);
        return captchaRenderer.getCapcha(identity);
    }

    public boolean verify(Verification verification) {
        CaptchaRenderer captchaRenderer = getRenderer(verification.getCategory());
        return captchaRenderer.verify(verification);
    }
}
