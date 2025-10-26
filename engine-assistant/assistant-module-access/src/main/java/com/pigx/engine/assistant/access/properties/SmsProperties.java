package com.pigx.engine.assistant.access.properties;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.core.definition.constant.BaseConstants;
import com.google.common.base.MoreObjects;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AccessConstants.PROPERTY_ACCESS_SMS)
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/SmsProperties.class */
public class SmsProperties {
    private Boolean enabled;
    private Boolean sandbox = false;
    private String testCode = "123456";
    private String verificationCodeTemplateId = "VERIFICATION_CODE";
    private Duration expire = Duration.ofMinutes(5);
    private int length = 6;

    public Duration getExpire() {
        return this.expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getSandbox() {
        return this.sandbox;
    }

    public void setSandbox(Boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getTestCode() {
        return this.testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getVerificationCodeTemplateId() {
        return this.verificationCodeTemplateId;
    }

    public void setVerificationCodeTemplateId(String verificationCodeTemplateId) {
        this.verificationCodeTemplateId = verificationCodeTemplateId;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).add("sandbox", this.sandbox).add("testCode", this.testCode).add("verificationCodeTemplateId", this.verificationCodeTemplateId).add("expire", this.expire).add("length", this.length).toString();
    }
}
