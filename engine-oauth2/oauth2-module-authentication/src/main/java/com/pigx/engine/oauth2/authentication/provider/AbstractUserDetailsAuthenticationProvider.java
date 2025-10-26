package com.pigx.engine.oauth2.authentication.provider;

import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import com.pigx.engine.core.identity.service.EnhanceUserDetailsService;
import com.pigx.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import com.pigx.engine.oauth2.core.exception.AccountEndpointLimitedException;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.oauth2.persistence.sas.jpa.storage.JpaOAuth2AuthorizationService;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.Assert;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/provider/AbstractUserDetailsAuthenticationProvider.class */
public abstract class AbstractUserDetailsAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(AbstractUserDetailsAuthenticationProvider.class);
    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private final UserDetailsService userDetailsService;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2AuthenticationProperties authenticationProperties;
    private PasswordEncoder passwordEncoder;

    protected abstract void additionalAuthenticationChecks(UserDetails userDetails, Map<String, Object> additionalParameters) throws AuthenticationException;

    protected abstract UserDetails retrieveUser(Map<String, Object> additionalParameters) throws AuthenticationException;

    public AbstractUserDetailsAuthenticationProvider(OAuth2AuthorizationService authorizationService, UserDetailsService userDetailsService, OAuth2AuthenticationProperties authenticationProperties) {
        this.userDetailsService = userDetailsService;
        this.authorizationService = authorizationService;
        this.authenticationProperties = authenticationProperties;
        setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    public EnhanceUserDetailsService getUserDetailsService() {
        return (EnhanceUserDetailsService) this.userDetailsService;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.AccountExpiredException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.AccountStatusException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.CredentialsExpiredException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.DisabledException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.authentication.LockedException */
    private Authentication authenticateUserDetails(Map<String, Object> additionalParameters, String registeredClientId) throws LockedException, DisabledException, AuthenticationException, CredentialsExpiredException, AccountExpiredException, AccountStatusException {
        UserDetails user = retrieveUser(additionalParameters);
        if (!user.isAccountNonLocked()) {
            log.debug("[Herodotus] |- Failed to authenticate since user account is locked");
            throw new LockedException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
        }
        if (!user.isEnabled()) {
            log.debug("[Herodotus] |- Failed to authenticate since user account is disabled");
            throw new DisabledException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
        }
        if (!user.isAccountNonExpired()) {
            log.debug("[Herodotus] |- Failed to authenticate since user account has expired");
            throw new AccountExpiredException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
        }
        additionalAuthenticationChecks(user, additionalParameters);
        if (!user.isCredentialsNonExpired()) {
            log.debug("[Herodotus] |- Failed to authenticate since user account credentials have expired");
            throw new CredentialsExpiredException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
        }
        if (this.authenticationProperties.getSignInEndpointLimited().getEnabled().booleanValue() && !this.authenticationProperties.getSignInKickOutLimited().getEnabled().booleanValue()) {
            OAuth2AuthorizationService oAuth2AuthorizationService = this.authorizationService;
            if (oAuth2AuthorizationService instanceof JpaOAuth2AuthorizationService) {
                int count = ((JpaOAuth2AuthorizationService) oAuth2AuthorizationService).findAuthorizationCount(registeredClientId, user.getUsername());
                if (count >= this.authenticationProperties.getSignInEndpointLimited().getMaximum().intValue()) {
                    throw new AccountEndpointLimitedException("Use same endpoint signIn exceed limit");
                }
            }
        }
        if (!this.authenticationProperties.getSignInEndpointLimited().getEnabled().booleanValue() && this.authenticationProperties.getSignInKickOutLimited().getEnabled().booleanValue()) {
            OAuth2AuthorizationService oAuth2AuthorizationService2 = this.authorizationService;
            if (oAuth2AuthorizationService2 instanceof JpaOAuth2AuthorizationService) {
                JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService = (JpaOAuth2AuthorizationService) oAuth2AuthorizationService2;
                List<OAuth2Authorization> authorizations = jpaOAuth2AuthorizationService.findAvailableAuthorizations(registeredClientId, user.getUsername());
                if (CollectionUtils.isNotEmpty(authorizations)) {
                    authorizations.forEach(authorization -> {
                        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getToken(OAuth2RefreshToken.class);
                        if (ObjectUtils.isNotEmpty(refreshToken)) {
                            authorization = OAuth2Authorization.from(authorization).invalidate(refreshToken.getToken()).build();
                        }
                        log.debug("[Herodotus] |- Sign in user [{}] with token id [{}] will be kicked out.", user.getUsername(), authorization.getId());
                        jpaOAuth2AuthorizationService.save(authorization);
                    });
                }
            }
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    protected Authentication getUsernamePasswordAuthentication(Map<String, Object> additionalParameters, String registeredClientId) throws LockedException, OAuth2AuthenticationException, DisabledException, AuthenticationException, CredentialsExpiredException, AccountExpiredException {
        Authentication authentication = null;
        try {
            authentication = authenticateUserDetails(additionalParameters, registeredClientId);
        } catch (BadCredentialsException bce) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorKeys.BAD_CREDENTIALS, bce.getMessage(), OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        } catch (UsernameNotFoundException unfe) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorKeys.USERNAME_NOT_FOUND, unfe.getMessage(), OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        } catch (AccountStatusException ase) {
            String exceptionName = ase.getClass().getSimpleName();
            OAuth2EndpointUtils.throwError(exceptionName, ase.getMessage(), OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        return authentication;
    }
}
