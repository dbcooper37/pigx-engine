package com.pigx.engine.assistant.access.stamp;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.assistant.access.properties.SmsProperties;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import cn.hutool.v7.core.util.RandomUtil;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/stamp/VerificationCodeStampManager.class */
public class VerificationCodeStampManager extends AbstractStampManager<String, String> {
    private SmsProperties smsProperties;

    public VerificationCodeStampManager(SmsProperties smsProperties) {
        super(AccessConstants.CACHE_NAME_TOKEN_VERIFICATION_CODE, smsProperties.getExpire());
    }

    public void setSmsProperties(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public String nextStamp(String key) {
        if (this.smsProperties.getSandbox().booleanValue()) {
            return this.smsProperties.getTestCode();
        }
        return RandomUtil.randomNumbers(this.smsProperties.getLength());
    }

    public Boolean getSandbox() {
        return this.smsProperties.getSandbox();
    }

    public String getVerificationCodeTemplateId() {
        return this.smsProperties.getVerificationCodeTemplateId();
    }
}
