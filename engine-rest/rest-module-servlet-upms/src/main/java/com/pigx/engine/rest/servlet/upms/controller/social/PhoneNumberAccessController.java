package com.pigx.engine.rest.servlet.upms.controller.social;

import com.pigx.engine.assistant.access.definition.domain.AccessResponse;
import com.pigx.engine.assistant.access.factory.AccessHandlerStrategyFactory;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.identity.enums.AccountCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "手机验证码登录接口")
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/social/PhoneNumberAccessController.class */
public class PhoneNumberAccessController {

    @Autowired
    private AccessHandlerStrategyFactory accessHandlerStrategyFactory;

    @PostMapping({"/open/identity/verification-code"})
    @Operation(summary = "手机验证码发送地址", description = "接收手机号码，发送验证码，并缓存至Redis")
    @Parameters({@Parameter(name = "mobile", required = true, description = "手机号码")})
    public Result<String> sendCode(@RequestParam("mobile") String mobile) {
        AccessResponse response = this.accessHandlerStrategyFactory.preProcess(AccountCategory.SMS, mobile, new String[0]);
        if (ObjectUtils.isNotEmpty(response)) {
            if (response.getSuccess().booleanValue()) {
                return Result.success("短信发送成功！");
            }
            return Result.failure("短信发送失败！");
        }
        return Result.failure("手机号码接收失败！");
    }
}
