package com.pigx.engine.assistant.access.processor;

import com.pigx.engine.assistant.access.definition.AccessHandler;
import com.pigx.engine.assistant.access.definition.domain.AccessResponse;
import com.pigx.engine.assistant.access.definition.domain.AccessUserDetails;
import com.pigx.engine.assistant.access.exception.AccessIdentityVerificationFailedException;
import com.pigx.engine.core.identity.domain.AccessPrincipal;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/JustAuthAccessHandler.class */
public class JustAuthAccessHandler implements AccessHandler {
    private final JustAuthProcessor justAuthProcessor;

    public JustAuthAccessHandler(JustAuthProcessor justAuthProcessor) {
        this.justAuthProcessor = justAuthProcessor;
    }

    @Override // com.pigx.engine.assistant.access.definition.AccessHandler
    public AccessResponse preProcess(String core, String... params) {
        String url = this.justAuthProcessor.getAuthorizeUrl(core);
        AccessResponse accessResponse = new AccessResponse();
        accessResponse.setAuthorizeUrl(url);
        return accessResponse;
    }

    @Override // com.pigx.engine.assistant.access.definition.AccessHandler
    public AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal) {
        AuthRequest authRequest = this.justAuthProcessor.getAuthRequest(source);
        AuthCallback authCallback = AuthCallback.builder().code(accessPrincipal.getCode()).auth_code(accessPrincipal.getAuth_code()).state(accessPrincipal.getState()).authorization_code(accessPrincipal.getAuthorization_code()).oauth_token(accessPrincipal.getOauth_token()).oauth_verifier(accessPrincipal.getOauth_verifier()).build();
        AuthResponse<AuthUser> response = authRequest.login(authCallback);
        if (response.ok()) {
            AuthUser authUser = (AuthUser) response.getData();
            return convertAuthUserToAccessUserDetails(authUser);
        }
        throw new AccessIdentityVerificationFailedException(response.getMsg());
    }

    private AccessUserDetails convertAuthUserToAccessUserDetails(AuthUser authUser) {
        AccessUserDetails sysSocialUser = new AccessUserDetails();
        sysSocialUser.setUuid(authUser.getUuid());
        sysSocialUser.setUsername(authUser.getUsername());
        sysSocialUser.setNickname(authUser.getNickname());
        sysSocialUser.setAvatar(authUser.getAvatar());
        sysSocialUser.setBlog(authUser.getBlog());
        sysSocialUser.setCompany(authUser.getCompany());
        sysSocialUser.setLocation(authUser.getLocation());
        sysSocialUser.setEmail(authUser.getEmail());
        sysSocialUser.setRemark(authUser.getRemark());
        sysSocialUser.setGender(authUser.getGender());
        sysSocialUser.setSource(authUser.getSource());
        AuthToken authToken = authUser.getToken();
        if (ObjectUtils.isNotEmpty(authToken)) {
            setAccessUserInfo(sysSocialUser, authToken.getAccessToken(), Integer.valueOf(authToken.getExpireIn()), authToken.getRefreshToken(), Integer.valueOf(authToken.getRefreshTokenExpireIn()), authToken.getScope(), authToken.getTokenType(), authToken.getUid(), authToken.getOpenId(), authToken.getAccessCode(), authToken.getUnionId());
        }
        return sysSocialUser;
    }

    private void setAccessUserInfo(AccessUserDetails accessUserDetails, String accessToken, Integer expireIn, String refreshToken, Integer refreshTokenExpireIn, String scope, String tokenType, String uid, String openId, String accessCode, String unionId) {
        accessUserDetails.setAccessToken(accessToken);
        accessUserDetails.setExpireIn(expireIn);
        accessUserDetails.setRefreshToken(refreshToken);
        accessUserDetails.setRefreshTokenExpireIn(refreshTokenExpireIn);
        accessUserDetails.setScope(scope);
        accessUserDetails.setTokenType(tokenType);
        accessUserDetails.setUid(uid);
        accessUserDetails.setOpenId(openId);
        accessUserDetails.setAccessCode(accessCode);
        accessUserDetails.setUnionId(unionId);
    }
}
