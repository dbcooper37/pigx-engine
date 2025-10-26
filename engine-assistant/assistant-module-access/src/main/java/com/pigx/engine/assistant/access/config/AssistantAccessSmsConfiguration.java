package com.pigx.engine.assistant.access.config;

import com.pigx.engine.assistant.access.condition.ConditionalOnSmsEnabled;
import com.pigx.engine.assistant.access.processor.PhoneNumberAccessHandler;
import com.pigx.engine.assistant.access.properties.SmsProperties;
import com.pigx.engine.assistant.access.stamp.VerificationCodeStampManager;
import com.pigx.engine.core.identity.enums.AccountCategory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({SmsProperties.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnSmsEnabled
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/config/AssistantAccessSmsConfiguration.class */
public class AssistantAccessSmsConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AssistantAccessSmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Assistant Access SMS] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public VerificationCodeStampManager verificationCodeStampManager(SmsProperties smsProperties) {
        VerificationCodeStampManager verificationCodeStampManager = new VerificationCodeStampManager(smsProperties);
        log.trace("[Herodotus] |- Bean [Verification Code Stamp Manager] Configure.");
        return verificationCodeStampManager;
    }

    @Bean({AccountCategory.PHONE_NUMBER_HANDLER})
    public PhoneNumberAccessHandler phoneNumberAccessHandler(VerificationCodeStampManager verificationCodeStampManager) {
        PhoneNumberAccessHandler phoneNumberAuthenticationHandler = new PhoneNumberAccessHandler(verificationCodeStampManager);
        log.trace("[Herodotus] |- Bean [Phone Number SignIn Handler] Configure.");
        return phoneNumberAuthenticationHandler;
    }
}
