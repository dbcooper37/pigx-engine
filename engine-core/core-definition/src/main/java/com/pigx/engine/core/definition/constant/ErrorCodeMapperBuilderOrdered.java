package com.pigx.engine.core.definition.constant;


public interface ErrorCodeMapperBuilderOrdered {

    int STEP = 10;

    int STANDARD = 0;
    int CACHE = STANDARD + STEP;
    int CAPTCHA = CACHE + STEP;
    int OAUTH2 = CAPTCHA + STEP;
    int REST = OAUTH2 + STEP;
    int MESSAGE = REST + STEP;
    int ACCESS = MESSAGE + STEP;
    int OSS = ACCESS + STEP;
}
