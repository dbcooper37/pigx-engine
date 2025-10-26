package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.definition.domain.captcha.Verification;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaHasExpiredException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaIsEmptyException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaMismatchException;
import com.pigx.engine.core.foundation.exception.captcha.CaptchaParameterIllegalException;
import com.pigx.engine.core.foundation.support.captcha.CaptchaRendererFactory;
import com.pigx.engine.oauth2.core.domain.FormLoginWebAuthenticationDetails;
import com.pigx.engine.oauth2.core.exception.OAuth2CaptchaArgumentIllegalException;
import com.pigx.engine.oauth2.core.exception.OAuth2CaptchaHasExpiredException;
import com.pigx.engine.oauth2.core.exception.OAuth2CaptchaIsEmptyException;
import com.pigx.engine.oauth2.core.exception.OAuth2CaptchaMismatchException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/OAuth2FormLoginAuthenticationProvider.class */
public class OAuth2FormLoginAuthenticationProvider extends DaoAuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(OAuth2FormLoginAuthenticationProvider.class);
    private final CaptchaRendererFactory captchaRendererFactory;

    public OAuth2FormLoginAuthenticationProvider(CaptchaRendererFactory captchaRendererFactory, UserDetailsService userDetailsService) {
        super(userDetailsService);
        this.captchaRendererFactory = captchaRendererFactory;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.AccountStatusException */
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException, AccountStatusException {
        Object details = authentication.getDetails();
        if (ObjectUtils.isNotEmpty(details) && (details instanceof FormLoginWebAuthenticationDetails)) {
            FormLoginWebAuthenticationDetails formLoginWebAuthenticationDetails = (FormLoginWebAuthenticationDetails) details;
            if (formLoginWebAuthenticationDetails.getEnabled().booleanValue()) {
                String code = formLoginWebAuthenticationDetails.getCode();
                String category = formLoginWebAuthenticationDetails.getCategory();
                String identity = formLoginWebAuthenticationDetails.getSessionId();
                if (StringUtils.isBlank(code)) {
                    throw new OAuth2CaptchaIsEmptyException("Captcha is empty.");
                }
                try {
                    Verification verification = new Verification();
                    verification.setCharacters(code);
                    verification.setCategory(category);
                    verification.setIdentity(identity);
                    this.captchaRendererFactory.verify(verification);
                } catch (CaptchaHasExpiredException e) {
                    throw new OAuth2CaptchaHasExpiredException("Captcha is expired!");
                } catch (CaptchaIsEmptyException e2) {
                    throw new OAuth2CaptchaIsEmptyException("Captcha is empty!");
                } catch (CaptchaMismatchException e3) {
                    throw new OAuth2CaptchaMismatchException("Captcha is mismatch!");
                } catch (CaptchaParameterIllegalException e4) {
                    throw new OAuth2CaptchaArgumentIllegalException("Captcha argument is illegal!");
                }
            }
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    public boolean supports(Class<?> authentication) {
        boolean supports = OAuth2FormLoginAuthenticationToken.class.isAssignableFrom(authentication);
        log.trace("[Herodotus] |- Form Login Authentication is supports! [{}]", Boolean.valueOf(supports));
        return supports;
    }
}
