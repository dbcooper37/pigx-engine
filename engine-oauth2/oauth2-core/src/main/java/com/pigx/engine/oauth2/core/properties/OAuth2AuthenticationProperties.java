package com.pigx.engine.oauth2.core.properties;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import com.pigx.engine.core.foundation.enums.Certificate;
import com.google.common.base.MoreObjects;
import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_OAUTH2_AUTHENTICATION)
/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/properties/OAuth2AuthenticationProperties.class */
public class OAuth2AuthenticationProperties {
    private SignInFailureLimited signInFailureLimited = new SignInFailureLimited();
    private SignInEndpointLimited signInEndpointLimited = new SignInEndpointLimited();
    private SignInKickOutLimited signInKickOutLimited = new SignInKickOutLimited();
    private Jwk jwk = new Jwk();
    private FormLogin formLogin = new FormLogin();

    public SignInEndpointLimited getSignInEndpointLimited() {
        return this.signInEndpointLimited;
    }

    public void setSignInEndpointLimited(SignInEndpointLimited signInEndpointLimited) {
        this.signInEndpointLimited = signInEndpointLimited;
    }

    public SignInFailureLimited getSignInFailureLimited() {
        return this.signInFailureLimited;
    }

    public void setSignInFailureLimited(SignInFailureLimited signInFailureLimited) {
        this.signInFailureLimited = signInFailureLimited;
    }

    public SignInKickOutLimited getSignInKickOutLimited() {
        return this.signInKickOutLimited;
    }

    public void setSignInKickOutLimited(SignInKickOutLimited signInKickOutLimited) {
        this.signInKickOutLimited = signInKickOutLimited;
    }

    public FormLogin getFormLogin() {
        return this.formLogin;
    }

    public void setFormLogin(FormLogin formLogin) {
        this.formLogin = formLogin;
    }

    public Jwk getJwk() {
        return this.jwk;
    }

    public void setJwk(Jwk jwk) {
        this.jwk = jwk;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("signInFailureLimited", this.signInFailureLimited).add("signInEndpointLimited", this.signInEndpointLimited).add("signInKickOutLimited", this.signInKickOutLimited).add("jwk", this.jwk).add("formLogin", this.formLogin).toString();
    }

    /* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/properties/OAuth2AuthenticationProperties$Jwk.class */
    public static class Jwk {
        private Certificate certificate = Certificate.CUSTOM;
        private String jksKeyStore = "classpath*:certificate/herodotus-cloud.jks";
        private String jksKeyPassword = "Herodotus-Cloud";
        private String jksStorePassword = "Herodotus-Cloud";
        private String jksKeyAlias = "herodotus-cloud";

        /* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/properties/OAuth2AuthenticationProperties$Jwk$Strategy.class */
        private enum Strategy {
            STANDARD,
            CUSTOM
        }

        public Certificate getCertificate() {
            return this.certificate;
        }

        public void setCertificate(Certificate certificate) {
            this.certificate = certificate;
        }

        public String getJksKeyStore() {
            return this.jksKeyStore;
        }

        public void setJksKeyStore(String jksKeyStore) {
            this.jksKeyStore = jksKeyStore;
        }

        public String getJksKeyPassword() {
            return this.jksKeyPassword;
        }

        public void setJksKeyPassword(String jksKeyPassword) {
            this.jksKeyPassword = jksKeyPassword;
        }

        public String getJksStorePassword() {
            return this.jksStorePassword;
        }

        public void setJksStorePassword(String jksStorePassword) {
            this.jksStorePassword = jksStorePassword;
        }

        public String getJksKeyAlias() {
            return this.jksKeyAlias;
        }

        public void setJksKeyAlias(String jksKeyAlias) {
            this.jksKeyAlias = jksKeyAlias;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("certificate", this.certificate).add("jksKeyStore", this.jksKeyStore).add("jksKeyPassword", this.jksKeyPassword).add("jksStorePassword", this.jksStorePassword).add("jksKeyAlias", this.jksKeyAlias).toString();
        }
    }

    /* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/properties/OAuth2AuthenticationProperties$SignInFailureLimited.class */
    public static class SignInFailureLimited {
        private Boolean enabled = true;
        private Integer maxTimes = 5;
        private Boolean autoUnlock = true;
        private Duration expire = Duration.ofHours(2);

        public Boolean getEnabled() {
            return this.enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public Integer getMaxTimes() {
            return this.maxTimes;
        }

        public void setMaxTimes(Integer maxTimes) {
            this.maxTimes = maxTimes;
        }

        public Duration getExpire() {
            return this.expire;
        }

        public void setExpire(Duration expire) {
            this.expire = expire;
        }

        public Boolean getAutoUnlock() {
            return this.autoUnlock;
        }

        public void setAutoUnlock(Boolean autoUnlock) {
            this.autoUnlock = autoUnlock;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).add("maxTimes", this.maxTimes).add("autoUnlock", this.autoUnlock).add("expire", this.expire).toString();
        }
    }

    /* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/properties/OAuth2AuthenticationProperties$SignInEndpointLimited.class */
    public static class SignInEndpointLimited {
        private Boolean enabled = false;
        private Integer maximum = 1;

        public Boolean getEnabled() {
            return this.enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public Integer getMaximum() {
            return this.maximum;
        }

        public void setMaximum(Integer maximum) {
            this.maximum = maximum;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).add("maximum", this.maximum).toString();
        }
    }

    /* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/properties/OAuth2AuthenticationProperties$SignInKickOutLimited.class */
    public static class SignInKickOutLimited {
        private Boolean enabled = true;

        public Boolean getEnabled() {
            return this.enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).toString();
        }
    }

    /* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/properties/OAuth2AuthenticationProperties$FormLogin.class */
    public static class FormLogin {
        private String authenticationUrl;
        private String registrationUrl;
        private String forgotPasswordUrl;
        private String usernameParameter = SystemConstants.USERNAME;
        private String passwordParameter = SystemConstants.PASSWORD;
        private String rememberMeParameter = "remember-me";
        private String captchaParameter = "captcha";
        private String loginPageUrl = "/login";
        private String failureUrl = this.loginPageUrl + "?error";
        private String logoutSuccessUrl = "/login?logout";
        private Duration cookieMaxAge = Duration.ofDays(30);
        private Boolean captchaEnabled = true;
        private String category = CaptchaCategory.HUTOOL_GIF_CAPTCHA;

        public String getAuthenticationUrl() {
            return this.authenticationUrl;
        }

        public void setAuthenticationUrl(String authenticationUrl) {
            this.authenticationUrl = authenticationUrl;
        }

        public Boolean getCaptchaEnabled() {
            return this.captchaEnabled;
        }

        public void setCaptchaEnabled(Boolean captchaEnabled) {
            this.captchaEnabled = captchaEnabled;
        }

        public String getCaptchaParameter() {
            return this.captchaParameter;
        }

        public void setCaptchaParameter(String captchaParameter) {
            this.captchaParameter = captchaParameter;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Duration getCookieMaxAge() {
            return this.cookieMaxAge;
        }

        public void setCookieMaxAge(Duration cookieMaxAge) {
            this.cookieMaxAge = cookieMaxAge;
        }

        public String getFailureUrl() {
            return this.failureUrl;
        }

        public void setFailureUrl(String failureUrl) {
            this.failureUrl = failureUrl;
        }

        public String getForgotPasswordUrl() {
            return this.forgotPasswordUrl;
        }

        public void setForgotPasswordUrl(String forgotPasswordUrl) {
            this.forgotPasswordUrl = forgotPasswordUrl;
        }

        public String getLoginPageUrl() {
            return this.loginPageUrl;
        }

        public void setLoginPageUrl(String loginPageUrl) {
            this.loginPageUrl = loginPageUrl;
        }

        public String getLogoutSuccessUrl() {
            return this.logoutSuccessUrl;
        }

        public void setLogoutSuccessUrl(String logoutSuccessUrl) {
            this.logoutSuccessUrl = logoutSuccessUrl;
        }

        public String getPasswordParameter() {
            return this.passwordParameter;
        }

        public void setPasswordParameter(String passwordParameter) {
            this.passwordParameter = passwordParameter;
        }

        public String getRegistrationUrl() {
            return this.registrationUrl;
        }

        public void setRegistrationUrl(String registrationUrl) {
            this.registrationUrl = registrationUrl;
        }

        public String getRememberMeParameter() {
            return this.rememberMeParameter;
        }

        public void setRememberMeParameter(String rememberMeParameter) {
            this.rememberMeParameter = rememberMeParameter;
        }

        public String getUsernameParameter() {
            return this.usernameParameter;
        }

        public void setUsernameParameter(String usernameParameter) {
            this.usernameParameter = usernameParameter;
        }

        public Boolean isRegistrationEnabled() {
            return Boolean.valueOf(StringUtils.isNotBlank(this.registrationUrl));
        }

        public Boolean isForgotPasswordEnabled() {
            return Boolean.valueOf(StringUtils.isNotBlank(this.forgotPasswordUrl));
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("authenticationUrl", this.authenticationUrl).add("usernameParameter", this.usernameParameter).add("passwordParameter", this.passwordParameter).add("rememberMeParameter", this.rememberMeParameter).add("captchaParameter", this.captchaParameter).add("loginPageUrl", this.loginPageUrl).add("failureUrl", this.failureUrl).add("logoutSuccessUrl", this.logoutSuccessUrl).add("registrationUrl", this.registrationUrl).add("forgotPasswordUrl", this.forgotPasswordUrl).add("cookieMaxAge", this.cookieMaxAge).add("captchaEnabled", this.captchaEnabled).add("category", this.category).toString();
        }
    }
}
