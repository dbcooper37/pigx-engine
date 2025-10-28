package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.domain.SecretKey;
import com.pigx.engine.oauth2.extension.dto.SignInErrorPrompt;
import com.pigx.engine.oauth2.extension.dto.SignInErrorStatus;
import com.pigx.engine.oauth2.extension.stamp.SignInFailureLimitedStampManager;
import com.pigx.engine.rest.servlet.identity.dto.Session;
import com.pigx.engine.rest.servlet.identity.dto.SessionCreate;
import com.pigx.engine.rest.servlet.identity.dto.SessionExchange;
import com.pigx.engine.rest.servlet.identity.service.InterfaceSecurityService;
import com.pigx.engine.web.core.annotation.Crypto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tags({@Tag(name = "OAuth2 认证服务器接口"), @Tag(name = "OAuth2 认证服务器开放接口"), @Tag(name = "OAuth2 身份认证辅助接口")})
@RestController
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OpenIdentityController.class */
public class OpenIdentityController {
    private final InterfaceSecurityService interfaceSecurityService;
    private final SignInFailureLimitedStampManager signInFailureLimitedStampManager;

    public OpenIdentityController(InterfaceSecurityService interfaceSecurityService, SignInFailureLimitedStampManager signInFailureLimitedStampManager) {
        this.interfaceSecurityService = interfaceSecurityService;
        this.signInFailureLimitedStampManager = signInFailureLimitedStampManager;
    }

    @PostMapping({"/open/identity/session"})
    @Operation(summary = "获取后台加密公钥", description = "根据未登录时的身份标识，在后台创建RSA/SM2公钥和私钥。身份标识为前端的唯一标识，如果为空，则在后台创建一个", requestBody = @RequestBody(content = {@Content(mediaType = "application/json")}), responses = {@ApiResponse(description = "自定义Session", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "sessionCreate", required = true, description = "Session创建请求参数", schema = @Schema(implementation = SessionCreate.class))})
    public Result<Session> create(@Validated @org.springframework.web.bind.annotation.RequestBody SessionCreate sessionCreate, HttpServletRequest request) throws OAuth2AuthenticationException {
        String sessionId = sessionCreate.getSessionId();
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = request.getSession().getId();
        }
        SecretKey secretKey = this.interfaceSecurityService.createSecretKey(sessionCreate.getClientId(), sessionCreate.getClientSecret(), sessionId);
        if (ObjectUtils.isNotEmpty(secretKey)) {
            Session session = new Session();
            session.setSessionId(secretKey.getIdentity());
            session.setPublicKey(secretKey.getPublicKey());
            session.setState(secretKey.getState());
            return Result.content(session);
        }
        return Result.failure();
    }

    @PostMapping({"/open/identity/exchange"})
    @Operation(summary = "获取AES秘钥", description = "用后台publicKey，加密前台publicKey，到后台换取AES秘钥", requestBody = @RequestBody(content = {@Content(mediaType = "application/json")}), responses = {@ApiResponse(description = "加密后的AES", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "sessionExchange", required = true, description = "秘钥交换", schema = @Schema(implementation = SessionExchange.class))})
    public Result<String> exchange(@Validated @org.springframework.web.bind.annotation.RequestBody SessionExchange sessionExchange) {
        String encryptedAesKey = this.interfaceSecurityService.exchange(sessionExchange.getSessionId(), sessionExchange.getPublicKey());
        if (StringUtils.isNotEmpty(encryptedAesKey)) {
            return Result.content(encryptedAesKey);
        }
        return Result.failure();
    }

    @PostMapping({"/open/identity/prompt"})
    @Crypto(responseEncrypt = false)
    @Operation(summary = "获取登录出错剩余次数", description = "获取登录出错剩余次数", requestBody = @RequestBody(content = {@Content(mediaType = "application/json")}), responses = {@ApiResponse(description = "加密后的AES", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "signInErrorPrompt", required = true, description = "提示信息所需参数", schema = @Schema(implementation = SignInErrorPrompt.class))})
    public Result<SignInErrorStatus> prompt(@Validated @org.springframework.web.bind.annotation.RequestBody SignInErrorPrompt signInErrorPrompt) {
        SignInErrorStatus signInErrorStatus = this.signInFailureLimitedStampManager.errorStatus(signInErrorPrompt.getUsername());
        return Result.content(signInErrorStatus);
    }
}
