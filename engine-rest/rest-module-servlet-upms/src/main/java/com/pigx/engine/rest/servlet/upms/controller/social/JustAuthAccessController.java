package com.pigx.engine.rest.servlet.upms.controller.social;

import com.pigx.engine.assistant.access.processor.JustAuthProcessor;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.rest.servlet.upms.event.AutomaticSignInEvent;
import cn.hutool.v7.core.bean.BeanUtil;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import me.zhyd.oauth.model.AuthCallback;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "社交登录Redirect Url")
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/controller/social/JustAuthAccessController.class */
public class JustAuthAccessController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JustAuthProcessor justAuthProcessor;

    @RequestMapping({"/open/identity/social/{source}"})
    @Operation(summary = "社交登录redirect url地址", description = "社交登录标准模式的redirect url地址，获取第三方登录返回的code")
    @Parameters({@Parameter(name = SystemConstants.SOURCE, required = true, description = "社交登录的类型，具体指定是哪一个第三方系统", in = ParameterIn.PATH)})
    public void callback(@PathVariable(SystemConstants.SOURCE) String source, AuthCallback callback) {
        if (StringUtils.isNotBlank(source) && BeanUtil.isNotEmpty(callback, new String[0])) {
            this.applicationContext.publishEvent(new AutomaticSignInEvent(ImmutableMap.of(SystemConstants.SOURCE, source, "callback", callback)));
        }
    }

    @GetMapping({"/open/identity/sources"})
    @Operation(summary = "获取社交登录列表", description = "根据后台已配置社交登录信息，返回可用的社交登录控制列表")
    public Result<Map<String, String>> list() {
        Map<String, String> list = this.justAuthProcessor.getAuthorizeUrls();
        if (MapUtils.isNotEmpty(list)) {
            return Result.success("获取社交登录列表成功", list);
        }
        return Result.success("社交登录没有配置", new HashMap());
    }
}
