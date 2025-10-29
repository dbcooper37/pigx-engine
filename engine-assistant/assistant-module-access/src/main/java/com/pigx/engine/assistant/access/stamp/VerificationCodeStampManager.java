package com.pigx.engine.assistant.access.stamp;

import cn.hutool.v7.core.util.RandomUtil;
import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.assistant.access.properties.SmsProperties;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;


public class VerificationCodeStampManager extends AbstractStampManager<String, String> {

    private SmsProperties smsProperties;

    public VerificationCodeStampManager(SmsProperties smsProperties) {
        super(AccessConstants.CACHE_NAME_TOKEN_VERIFICATION_CODE, smsProperties.getExpire());
    }

    public void setSmsProperties(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public String nextStamp(String key) {
        if (smsProperties.getSandbox()) {
            return smsProperties.getTestCode();
        } else {
            return RandomUtil.randomNumbers(smsProperties.getLength());
        }
    }

    public Boolean getSandbox() {
        return smsProperties.getSandbox();
    }

    public String getVerificationCodeTemplateId() {
        return smsProperties.getVerificationCodeTemplateId();
    }
}
