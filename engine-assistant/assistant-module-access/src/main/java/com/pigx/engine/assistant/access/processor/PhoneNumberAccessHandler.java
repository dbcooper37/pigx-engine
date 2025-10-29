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
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;

import java.util.LinkedHashMap;


public class PhoneNumberAccessHandler implements AccessHandler {

    private final VerificationCodeStampManager verificationCodeStampManager;

    public PhoneNumberAccessHandler(VerificationCodeStampManager verificationCodeStampManager) {
        this.verificationCodeStampManager = verificationCodeStampManager;
    }

    @Override
    public AccessResponse preProcess(String phone, String... params) {
        String code = verificationCodeStampManager.create(phone);
        boolean result;
        if (verificationCodeStampManager.getSandbox()) {
            result = true;
        } else {
            SmsBlend smsBlend = SmsFactory.getSmsBlend();
            LinkedHashMap<String, String> message = new LinkedHashMap<>();
            message.put(SystemConstants.CODE, code);
            SmsResponse smsResponse = smsBlend.sendMessage(phone, verificationCodeStampManager.getVerificationCodeTemplateId(), message);
            result = smsResponse.isSuccess();
        }

        AccessResponse accessResponse = new AccessResponse();
        accessResponse.setSuccess(result);
        return accessResponse;
    }

    @Override
    public AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal) {
        try {
            verificationCodeStampManager.check(accessPrincipal.getMobile(), accessPrincipal.getCode());

            AccessUserDetails accessUserDetails = new AccessUserDetails();
            accessUserDetails.setUuid(accessPrincipal.getMobile());
            accessUserDetails.setPhoneNumber(accessPrincipal.getMobile());
            accessUserDetails.setUsername(accessPrincipal.getMobile());
            accessUserDetails.setSource(source);

            verificationCodeStampManager.delete(accessPrincipal.getMobile());
            return accessUserDetails;

        } catch (StampParameterIllegalException | StampMismatchException | StampHasExpiredException e) {
            throw new AccessIdentityVerificationFailedException("Phone Verification Code Error!");
        }
    }
}
