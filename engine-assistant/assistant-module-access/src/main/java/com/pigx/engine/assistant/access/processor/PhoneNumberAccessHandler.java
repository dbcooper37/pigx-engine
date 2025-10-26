package com.pigx.engine.assistant.access.processor;

import com.pigx.engine.assistant.access.definition.AccessHandler;
import com.pigx.engine.assistant.access.definition.domain.AccessResponse;
import com.pigx.engine.assistant.access.definition.domain.AccessUserDetails;
import com.pigx.engine.assistant.access.exception.AccessIdentityVerificationFailedException;
import com.pigx.engine.assistant.access.stamp.VerificationCodeStampManager;
import com.pigx.engine.cache.core.exception.StampHasExpiredException;
import com.pigx.engine.cache.core.exception.StampMismatchException;
import com.pigx.engine.cache.core.exception.StampParameterIllegalException;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.AccessPrincipal;
import java.util.LinkedHashMap;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/PhoneNumberAccessHandler.class */
public class PhoneNumberAccessHandler implements AccessHandler {
    private final VerificationCodeStampManager verificationCodeStampManager;

    public PhoneNumberAccessHandler(VerificationCodeStampManager verificationCodeStampManager) {
        this.verificationCodeStampManager = verificationCodeStampManager;
    }

    @Override // com.pigx.engine.assistant.access.definition.AccessHandler
    public AccessResponse preProcess(String phone, String... params) {
        boolean result;
        String code = this.verificationCodeStampManager.create(phone);
        if (this.verificationCodeStampManager.getSandbox().booleanValue()) {
            result = true;
        } else {
            SmsBlend smsBlend = SmsFactory.getSmsBlend();
            LinkedHashMap<String, String> message = new LinkedHashMap<>();
            message.put(SystemConstants.CODE, code);
            SmsResponse smsResponse = smsBlend.sendMessage(phone, this.verificationCodeStampManager.getVerificationCodeTemplateId(), message);
            result = smsResponse.isSuccess();
        }
        AccessResponse accessResponse = new AccessResponse();
        accessResponse.setSuccess(Boolean.valueOf(result));
        return accessResponse;
    }

    @Override // com.pigx.engine.assistant.access.definition.AccessHandler
    public AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal) {
        try {
            this.verificationCodeStampManager.check(accessPrincipal.getMobile(), accessPrincipal.getCode());
            AccessUserDetails accessUserDetails = new AccessUserDetails();
            accessUserDetails.setUuid(accessPrincipal.getMobile());
            accessUserDetails.setPhoneNumber(accessPrincipal.getMobile());
            accessUserDetails.setUsername(accessPrincipal.getMobile());
            accessUserDetails.setSource(source);
            this.verificationCodeStampManager.delete(accessPrincipal.getMobile());
            return accessUserDetails;
        } catch (StampHasExpiredException | StampMismatchException | StampParameterIllegalException e) {
            throw new AccessIdentityVerificationFailedException("Phone Verification Code Error!");
        }
    }
}
