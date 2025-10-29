package com.pigx.engine.assistant.captcha.constant;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface CaptchaConstants extends BaseConstants {

    String PROPERTY_ASSISTANT_CAPTCHA = PROPERTY_PREFIX_ASSISTANT + ".captcha";

    String CACHE_NAME_TOKEN_CAPTCHA = CACHE_TOKEN_BASE_PREFIX + "captcha:";

    String CACHE_NAME_CAPTCHA_JIGSAW = CACHE_NAME_TOKEN_CAPTCHA + "jigsaw:";
    String CACHE_NAME_CAPTCHA_WORD_CLICK = CACHE_NAME_TOKEN_CAPTCHA + "word_click:";
    String CACHE_NAME_CAPTCHA_GRAPHIC = CACHE_NAME_TOKEN_CAPTCHA + "graphic:";
}
