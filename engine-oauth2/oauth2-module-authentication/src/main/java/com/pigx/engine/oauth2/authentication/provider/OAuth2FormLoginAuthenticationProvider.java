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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public class OAuth2FormLoginAuthenticationProvider extends DaoAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(OAuth2FormLoginAuthenticationProvider.class);

    private final CaptchaRendererFactory captchaRendererFactory;

    public OAuth2FormLoginAuthenticationProvider(CaptchaRendererFactory captchaRendererFactory, UserDetailsService userDetailsService) {
        super(userDetailsService);
        this.captchaRendererFactory = captchaRendererFactory;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object details = authentication.getDetails();

        if (ObjectUtils.isNotEmpty(details) && details instanceof FormLoginWebAuthenticationDetails formLoginWebAuthenticationDetails) {

            if (formLoginWebAuthenticationDetails.getEnabled()) {

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
                    captchaRendererFactory.verify(verification);
                } catch (CaptchaParameterIllegalException e) {
                    throw new OAuth2CaptchaArgumentIllegalException("Captcha argument is illegal!");
                } catch (CaptchaHasExpiredException e) {
                    throw new OAuth2CaptchaHasExpiredException("Captcha is expired!");
                } catch (CaptchaMismatchException e) {
                    throw new OAuth2CaptchaMismatchException("Captcha is mismatch!");
                } catch (CaptchaIsEmptyException e) {
                    throw new OAuth2CaptchaIsEmptyException("Captcha is empty!");
                }
            }
        }

        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        boolean supports = (OAuth2FormLoginAuthenticationToken.class.isAssignableFrom(authentication));
        log.trace("[PIGXD] |- Form Login Authentication is supports! [{}]", supports);
        return supports;
    }
}
