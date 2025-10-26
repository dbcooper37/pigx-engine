package com.pigx.engine.logic.upms.definition;

import com.pigx.engine.assistant.access.exception.AccessIdentityVerificationFailedException;
import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.AuthenticationException;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/definition/AbstractSocialAuthenticationHandler.class */
public abstract class AbstractSocialAuthenticationHandler implements SocialAuthenticationHandler {
    public abstract SocialUserDetails identity(String source, AccessPrincipal accessPrincipal) throws AccessIdentityVerificationFailedException;

    public abstract SocialUserDetails isUserExist(SocialUserDetails socialUserDetails);

    /* JADX WARN: Byte code manipulation detected: skipped illegal throws declarations: [com.pigx.engine.oauth2.core.exception.UsernameAlreadyExistsException] */
    public abstract HerodotusUser register(SocialUserDetails socialUserDetails);

    /* JADX WARN: Byte code manipulation detected: skipped illegal throws declarations: [com.pigx.engine.oauth2.core.exception.SocialCredentialsUserBindingFailedException] */
    public abstract void binding(String userId, SocialUserDetails socialUserDetails);

    public abstract void additionalRegisterOperation(HerodotusUser HerodotusUser, SocialUserDetails socialUserDetails);

    public abstract HerodotusUser signIn(SocialUserDetails socialUserDetails);

    public abstract void additionalSignInOperation(HerodotusUser HerodotusUser, SocialUserDetails newSocialUserDetails, SocialUserDetails oldSocialUserDetails);

    @Override // com.pigx.engine.logic.upms.definition.SocialAuthenticationHandler
    public HerodotusUser authentication(String source, AccessPrincipal accessPrincipal) throws AccessIdentityVerificationFailedException, AuthenticationException {
        SocialUserDetails newSocialUserDetails = identity(source, accessPrincipal);
        SocialUserDetails oldSocialUserDetails = isUserExist(newSocialUserDetails);
        if (ObjectUtils.isEmpty(oldSocialUserDetails)) {
            HerodotusUser HerodotusUser = register(newSocialUserDetails);
            binding(HerodotusUser.getUserId(), newSocialUserDetails);
            additionalRegisterOperation(HerodotusUser, newSocialUserDetails);
            return HerodotusUser;
        }
        HerodotusUser HerodotusUser2 = signIn(oldSocialUserDetails);
        additionalSignInOperation(HerodotusUser2, newSocialUserDetails, oldSocialUserDetails);
        return HerodotusUser2;
    }
}
