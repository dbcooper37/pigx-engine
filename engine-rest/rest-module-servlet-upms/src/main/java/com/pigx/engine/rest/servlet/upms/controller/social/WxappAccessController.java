package com.pigx.engine.rest.servlet.upms.controller.social;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.pigx.engine.assistant.access.definition.domain.AccessResponse;
import com.pigx.engine.assistant.access.factory.AccessHandlerStrategyFactory;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.rest.servlet.upms.dto.WxappProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "微信小程序平台认证接口")
public class WxappAccessController {

    @Autowired
    private AccessHandlerStrategyFactory accessHandlerStrategyFactory;

    @Operation(summary = "微信小程序登录", description = "利用wx.login获取code，进行小程序登录")
    @Parameters({
            @Parameter(name = "socialDetails", required = true, description = "社交登录自定义参数实体"),
    })
    @PostMapping("/open/identity/wxapp")
    public Result<WxMaJscode2SessionResult> login(@Validated @RequestBody WxappProfile wxappProfile) {
        AccessResponse response = accessHandlerStrategyFactory.preProcess(AccountCategory.WXAPP, wxappProfile.getCode(), wxappProfile.getAppId());
        if (ObjectUtils.isNotEmpty(response)) {
            return Result.success("微信小程序登录成功", response.getSession());
        } else {
            return Result.failure("微信小程序登录失败");
        }
    }
}
